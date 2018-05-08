package it.devchallenge.snake.domain.repository

import android.graphics.Point
import io.reactivex.Flowable
import io.reactivex.Single
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.GameEvent
import it.devchallenge.snake.domain.model.SnakeState

interface GameEventRepository {
    fun getGameEvents(): Flowable<GameEvent>
}