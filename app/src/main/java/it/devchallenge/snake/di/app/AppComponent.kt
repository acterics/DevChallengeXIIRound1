package it.devchallenge.snake.di.app

import dagger.Component
import it.devchallenge.snake.SnakeApplication
import it.devchallenge.snake.di.game.SnakeGameComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    FirebaseDatabaseModule::class
])
interface AppComponent {

    fun snakeGameComponentBuilder(): SnakeGameComponent.Builder

    fun inject(snakeApplication: SnakeApplication)
}