package it.devchallenge.snake.presentation.snakefield

import android.graphics.Point
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toSingle
import it.devchallenge.snake.domain.exception.EndOfGameException
import it.devchallenge.snake.domain.model.*
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



    override fun attachView(view: SnakeFieldView?) {
        super.attachView(view)
        playerEventsDisposable = playerEventRepository.getPlayerEvents()
                .doOnNext { playerEvent -> gameState.playerEvents.add(playerEvent) }
                .flatMapCompletable { event -> snakeStateRepository.changeSnakeDirection(event.direction) }
                .subscribe()


        gameEventsDisposable = gameEventRepository.getGameEvents()
                .doOnNext { gameEvent -> gameState.gameEvents.add(gameEvent) }
                .flatMapCompletable { snakeStateRepository.makeSnakeStep() }
                .andThen { snakeStateRepository.isEndOfGame(field) }
                .andThen {
                    snakeStateRepository.requestFoodPosition(field)
                            .flatMap { foodPosition ->
                                Maybe.fromCallable { field.foodPosition = foodPosition }
                            }
                            .toSingle(Unit)
                }
                .andThen { snakeStateRepository.getCurrentSnakeState()
                        .flatMapCompletable { snakeState -> Completable.fromAction {
                            viewState.invalidateField(field, snakeState)
                        } }

                }
                .subscribeBy(
                        onError = { error: Throwable -> onGameEventError(error) }
                )

    }



    override fun detachView(view: SnakeFieldView?) {
        super.detachView(view)
        playerEventsDisposable?.dispose()
        gameEventsDisposable?.dispose()
    }





    private fun onGameEventError(error: Throwable) {
        when (error) {
            is EndOfGameException -> onEndOfGame()
        }
    }

    private fun onEndOfGame() {

    }
}