package it.devchallenge.snake.ui.snakegame

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import it.devchallenge.snake.R
import it.devchallenge.snake.common.extension.delegate.subcomponent
import it.devchallenge.snake.common.ui.BaseScopedFragment
import it.devchallenge.snake.di.field.SnakeFieldComponent
import it.devchallenge.snake.di.field.SnakeFieldModule
import it.devchallenge.snake.di.game.SnakeGameComponent
import it.devchallenge.snake.domain.executor.ExecutionScheduler
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.SnakeState
import it.devchallenge.snake.domain.repository.GameEventRepository
import it.devchallenge.snake.domain.repository.PlayerEventRepository
import it.devchallenge.snake.domain.repository.SnakeStateRepository
import it.devchallenge.snake.presentation.snakefield.SnakeFieldPresenter
import it.devchallenge.snake.presentation.snakefield.SnakeFieldView
import kotlinx.android.synthetic.main.fragment_snake_field.*
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
    @Inject lateinit var executionScheduler: ExecutionScheduler


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_snake_field, container, false)
    }

    override fun invalidateField(field: Field, snakeState: SnakeState) {
        vSnakeField.invalidateField(field, snakeState)
    }

    override fun invalidateScore(score: Int) {
        tvScore.text = "$score"
    }
}