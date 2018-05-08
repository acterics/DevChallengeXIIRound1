package it.devchallenge.snake.data

import it.devchallenge.snake.domain.model.ControlType
import it.devchallenge.snake.domain.repository.PlayerEventRepository
import javax.inject.Inject

class PlayerEventRepositoryFactory
@Inject constructor() {

    fun create(controlType: ControlType): PlayerEventRepository {
        TODO()
    }

}