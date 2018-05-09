package it.devchallenge.snake.data

import android.content.Context
import it.devchallenge.snake.data.repository.StubFieldRepository
import it.devchallenge.snake.domain.model.FieldType
import it.devchallenge.snake.domain.repository.FieldRepository
import javax.inject.Inject

class FieldRepositoryFactory
@Inject constructor(private val context: Context) {

    fun create(fieldType: FieldType): FieldRepository {
        return StubFieldRepository(context, fieldType)
    }
}