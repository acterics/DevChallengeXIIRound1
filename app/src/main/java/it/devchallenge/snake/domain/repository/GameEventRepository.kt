package it.devchallenge.snake.domain.repository

import io.reactivex.Flowable
import it.devchallenge.snake.domain.model.GameEvent

interface GameEventRepository {
    fun getGameEvents(): Flowable<GameEvent>
}