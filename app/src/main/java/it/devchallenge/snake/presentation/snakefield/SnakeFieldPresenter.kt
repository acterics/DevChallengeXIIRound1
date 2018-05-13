package it.devchallenge.snake.presentation.snakefield

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import it.devchallenge.snake.domain.exception.EndOfGameException
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.GameState
import it.devchallenge.snake.domain.repository.GameEventRepository
import it.devchallenge.snake.domain.repository.PlayerEventRepository
import it.devchallenge.snake.domain.repository.SnakeStateRepository

@InjectViewState
class SnakeFieldPresenter(private val field: Field,
                          private val playerEventRepository: PlayerEventRepository,
                          private val gameEventRepository: GameEventRepository,
                          private val snakeStateRepository: SnakeStateRepository):
        MvpPresenter<SnakeFieldView>() {

    private val gameState = GameState(field = field)

    private var playerEventsDisposable: Disposable? = null
    private var gameEventsDisposable: Disposable? = null
    private var scoreDisposable: Disposable? = null


    override fun attachView(view: SnakeFieldView?) {
        super.attachView(view)
        playerEventsDisposable = playerEventRepository.getPlayerEvents()
                .doOnNext { playerEvent -> gameState.playerEvents.add(playerEvent) }
                .flatMapCompletable { event -> snakeStateRepository.changeSnakeDirection(event.direction) }
                .subscribe()

        scoreDisposable = snakeStateRepository
                .getSnakeScore()
                .subscribe { score -> viewState.invalidateScore(score) }


        gameEventsDisposable = gameEventRepository.getGameEvents()
                .doOnNext { gameEvent -> gameState.gameEvents.add(gameEvent) }
                .flatMapCompletable {
                    snakeStateRepository.makeSnakeStep(field)
                            .andThen(snakeStateRepository.isEndOfGame(field))
                            .andThen(snakeStateRepository.requestFoodPosition(field))
                            .flatMapCompletable { newFoodPosition ->
                                Completable.fromAction {
                                    field.foodPosition = newFoodPosition
                                }
                            }
                            .andThen(snakeStateRepository.getCurrentSnakeState())
                            .flatMapCompletable { snakeState ->
                                Completable.fromAction {
                                    viewState.invalidateField(field, snakeState)
                                }
                            }
                }
                .subscribeBy(
                        onError = { error: Throwable -> onGameEventError(error) }
                )


    }


    override fun detachView(view: SnakeFieldView?) {
        super.detachView(view)
        playerEventsDisposable?.dispose()
        gameEventsDisposable?.dispose()
        scoreDisposable?.dispose()
    }


    private fun onGameEventError(error: Throwable) {
        error.printStackTrace()
        when (error) {
            is EndOfGameException -> onEndOfGame()
        }
    }

    private fun onEndOfGame() {
        playerEventsDisposable?.dispose()
        gameEventsDisposable?.dispose()
        scoreDisposable?.dispose()
    }
}