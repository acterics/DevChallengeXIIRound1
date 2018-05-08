package it.devchallenge.snake.domain.repository

import io.reactivex.Flowable
import it.devchallenge.snake.domain.model.PlayerEvent

interface PlayerEventRepository {

    fun getPlayerEvents(): Flowable<PlayerEvent>
}