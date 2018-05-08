package it.devchallenge.snake.di.field

import dagger.Module
import dagger.Provides
import it.devchallenge.snake.data.PlayerEventRepositoryFactory
import it.devchallenge.snake.di.scope.FragmentScope
import it.devchallenge.snake.domain.model.ControlType
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.FieldContainer
import it.devchallenge.snake.domain.repository.GameEventRepository
import it.devchallenge.snake.domain.repository.PlayerEventRepository
import it.devchallenge.snake.domain.repository.SnakeStateRepository

@Module
class SnakeFieldModule {

    @FragmentScope
    @Provides
    fun provideFiled(fieldContainer: FieldContainer): Field {
        return fieldContainer.field!!
    }


    @FragmentScope
    @Provides
    fun providePlayerEventRepository(controlType: ControlType,
                                     playerEventRepositoryFactory: PlayerEventRepositoryFactory):
            PlayerEventRepository {
        return playerEventRepositoryFactory.create(controlType)
    }

    @FragmentScope
    @Provides
    fun provideGameEventRepository(): GameEventRepository {
        TODO()
    }

    @FragmentScope
    @Provides
    fun provideSnakeStateRepository(field: Field): SnakeStateRepository {
        TODO()
    }
}