package it.devchallenge.snake.di.game

import dagger.Subcomponent
import it.devchallenge.snake.di.field.SnakeFieldComponent
import it.devchallenge.snake.di.scope.ActivityScope
import it.devchallenge.snake.ui.snakegame.SnakeGameActivity

@ActivityScope
@Subcomponent(modules = [
    SnakeGameModule::class
])
interface SnakeGameComponent {

    companion object {
        const val NAME = "it.devchallenge.snake.di.game.SnakeGameComponent"
    }

    @Subcomponent.Builder
    interface Builder {
        fun requestSnakeGameModule(snakeGameModule: SnakeGameModule): Builder
        fun build(): SnakeGameComponent
    }

    fun snakeFieldComponentBuilder(): SnakeFieldComponent.Builder

    fun inject(snakeGameActivity: SnakeGameActivity)
}