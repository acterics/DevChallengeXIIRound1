package it.devchallenge.snake.data.repository

import io.reactivex.Flowable
import it.devchallenge.snake.domain.executor.ExecutionScheduler
import it.devchallenge.snake.domain.model.GameEvent
import it.devchallenge.snake.domain.repository.GameEventRepository
import java.util.concurrent.TimeUnit

//TODO add speed dependency
class GameEventRepositoryImpl(private val executionScheduler: ExecutionScheduler):
        GameEventRepository {
    override fun getGameEvents(): Flowable<GameEvent> {
        return Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .map { GameEvent(System.currentTimeMillis()) }
                .compose(executionScheduler.highPriorityFlowable())
    }
}