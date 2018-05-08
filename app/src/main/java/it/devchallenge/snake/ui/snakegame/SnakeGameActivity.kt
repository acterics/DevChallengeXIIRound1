package it.devchallenge.snake.ui.snakegame

import it.devchallenge.snake.common.extension.delegate.subcomponent
import it.devchallenge.snake.common.ui.BaseScopedActivity
import it.devchallenge.snake.di.game.SnakeGameComponent
import it.devchallenge.snake.di.game.SnakeGameModule
import it.devchallenge.snake.domain.model.GameConfiguration

class SnakeGameActivity: BaseScopedActivity() {

    companion object {
        const val EXTRA_GAME_CONFIGURATION = "it.devchallenge.snake.ui.snakegame.EXTRA_GAME_CONFIGURATION"
    }

    private val gameConfiguration by lazy<GameConfiguration> {
        intent.getParcelableExtra(EXTRA_GAME_CONFIGURATION)
    }

    private var component by subcomponent(SnakeGameComponent.NAME) {
        snakeGameComponentBuilder()
                .requestSnakeGameModule(SnakeGameModule(gameConfiguration))
                .build()
    }

    override fun injectComponent() {
        component?.inject(this)
    }

    override fun rejectComponent() {
        component = null
    }



}