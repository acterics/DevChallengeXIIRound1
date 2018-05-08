package it.devchallenge.snake.di.field

import dagger.Subcomponent
import it.devchallenge.snake.di.scope.FragmentScope
import it.devchallenge.snake.ui.snakegame.SnakeFieldFragment

@FragmentScope
@Subcomponent(modules = [
    SnakeFieldModule::class
])
interface SnakeFieldComponent {

    companion object {
        const val NAME = "it.devchallenge.snake.di.field.SnakeFieldComponent"
    }

    @Subcomponent.Builder
    interface Builder {
        fun requestSnakeFieldModule(snakeFieldModule: SnakeFieldModule): Builder
        fun build(): SnakeFieldComponent
    }

    fun inject(snakeFieldFragment: SnakeFieldFragment)
}