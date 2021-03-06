package it.devchallenge.snake.domain.repository

import io.reactivex.Single
import it.devchallenge.snake.domain.model.Field

interface FieldRepository {
    fun getField(): Single<Field>
}