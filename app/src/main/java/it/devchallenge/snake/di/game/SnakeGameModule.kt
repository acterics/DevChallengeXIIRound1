package it.devchallenge.snake.di.game

import dagger.Module
import dagger.Provides
import it.devchallenge.snake.data.FieldRepositoryFactory
import it.devchallenge.snake.di.scope.ActivityScope
import it.devchallenge.snake.domain.model.ControlType
import it.devchallenge.snake.domain.model.FieldContainer
import it.devchallenge.snake.domain.model.FieldType
import it.devchallenge.snake.domain.model.GameConfiguration
import it.devchallenge.snake.domain.repository.FieldRepository
import it.devchallenge.snake.ui.snakegame.SnakeGameActivity

@Module
class SnakeGameModule(private val snakeGameActivity: SnakeGameActivity) {

    @ActivityScope
    @Provides
    fun provideFieldContainer(): FieldContainer {
        return FieldContainer()
    }

    @ActivityScope
    @Provides
    fun provideGameConfiguration(): GameConfiguration {
        return snakeGameActivity.gameConfiguration
    }

    @ActivityScope
    @Provides
    fun provideFieldType(gameConfiguration: GameConfiguration): FieldType {
        return gameConfiguration.fieldType
    }

    @ActivityScope
    @Provides
    fun provideControlType(gameConfiguration: GameConfiguration): ControlType {
        return gameConfiguration.controlType
    }

    @ActivityScope
    @Provides
    fun provideFieldRepository(fieldType: FieldType,
                               fieldRepositoryFactory: FieldRepositoryFactory):
            FieldRepository {
        return fieldRepositoryFactory.create(fieldType)
    }





}