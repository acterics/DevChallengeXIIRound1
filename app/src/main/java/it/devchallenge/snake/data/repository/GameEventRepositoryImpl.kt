package it.devchallenge.snake.data.repository

import io.reactivex.Flowable
import it.devchallenge.snake.domain.executor.ExecutionScheduler
import it.devchallenge.snake.domain.model.GameConfiguration
import it.devchallenge.snake.domain.model.GameEvent
import it.devchallenge.snake.domain.repository.GameEventRepository
import java.util.concurrent.TimeUnit

//TODO add speed dependency
class GameEventRepositoryImpl(private val gameConfiguration: GameConfiguration,
                              private val executionScheduler: ExecutionScheduler):
        GameEventRepository {
    override fun getGameEvents(): Flowable<GameEvent> {
        return Flowable.interval((10000 / gameConfiguration.speed).toLong(), TimeUnit.MILLISECONDS)
                .map { GameEvent(System.currentTimeMillis()) }
                .compose(executionScheduler.highPriorityFlowable())
    }
}