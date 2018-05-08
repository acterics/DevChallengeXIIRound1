package it.devchallenge.snake.data

import it.devchallenge.snake.domain.model.FieldType
import it.devchallenge.snake.domain.repository.FieldRepository
import javax.inject.Inject

class FieldRepositoryFactory
@Inject constructor() {

    fun create(fieldType: FieldType): FieldRepository {
        TODO()
    }
}