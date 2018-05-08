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

@Module
class SnakeGameModule(private val gameConfiguration: GameConfiguration) {

    @ActivityScope
    @Provides
    fun provideFieldContainer(): FieldContainer {
        return FieldContainer()
    }

    @ActivityScope
    @Provides
    fun provideFieldType(): FieldType {
        return gameConfiguration.fieldType
    }

    @ActivityScope
    @Provides
    fun provideControlType(): ControlType {
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