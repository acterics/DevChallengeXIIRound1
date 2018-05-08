package it.devchallenge.snake.ui.snakegame

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import it.devchallenge.snake.common.extension.delegate.subcomponent
import it.devchallenge.snake.common.ui.BaseScopedFragment
import it.devchallenge.snake.di.field.SnakeFieldComponent
import it.devchallenge.snake.di.field.SnakeFieldModule
import it.devchallenge.snake.di.game.SnakeGameComponent
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.SnakeState
import it.devchallenge.snake.domain.repository.GameEventRepository
import it.devchallenge.snake.domain.repository.PlayerEventRepository
import it.devchallenge.snake.domain.repository.SnakeStateRepository
import it.devchallenge.snake.presentation.snakefield.SnakeFieldPresenter
import it.devchallenge.snake.presentation.snakefield.SnakeFieldView
import javax.inject.Inject

class SnakeFieldFragment: BaseScopedFragment(), SnakeFieldView {

    private var component by subcomponent<SnakeFieldComponent, SnakeGameComponent>(
            SnakeFieldComponent.NAME, SnakeGameComponent.NAME
    ) {
        snakeFieldComponentBuilder()
                .requestSnakeFieldModule(SnakeFieldModule())
                .build()
    }


    @Inject lateinit var field: Field
    @Inject lateinit var snakeStateRepository: SnakeStateRepository
    @Inject lateinit var playerEventRepository: PlayerEventRepository
    @Inject lateinit var gameEventRepository: GameEventRepository


    @InjectPresenter lateinit var presenter: SnakeFieldPresenter
    @ProvidePresenter fun providePresenter(): SnakeFieldPresenter {
        return SnakeFieldPresenter(
                field,
                playerEventRepository,
                gameEventRepository,
                snakeStateRepository
        )
    }

    override fun injectComponent() {
        component?.inject(this)
    }

    override fun rejectComponent() {
        component = null
    }

    override fun invalidateField(field: Field, snakeState: SnakeState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}