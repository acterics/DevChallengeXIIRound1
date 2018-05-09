package it.devchallenge.snake.data.repository

import io.reactivex.Flowable
import io.reactivex.rxkotlin.zipWith
import it.devchallenge.snake.common.extension.scanUnmodified
import it.devchallenge.snake.domain.executor.ExecutionScheduler
import it.devchallenge.snake.domain.model.Direction
import it.devchallenge.snake.domain.model.PlayerEvent
import it.devchallenge.snake.domain.repository.PlayerEventRepository
import java.util.concurrent.TimeUnit

class StubPlayerEventRepository(private val executionScheduler: ExecutionScheduler):
        PlayerEventRepository {

    override fun getPlayerEvents(): Flowable<PlayerEvent> {
        return Flowable.fromIterable(generatePlayerEvents())
                .zipWith(Flowable.interval(100L, TimeUnit.MILLISECONDS)) {
                    event: PlayerEvent, _: Long -> event
                }
                .compose(executionScheduler.highPriorityFlowable())
    }

    private fun generatePlayerEvents(): List<PlayerEvent> {
//        return listOf()
        return (0..100).map {
            PlayerEvent(
                    Direction.values()[it % 4],
                    (it * 1000).toLong()
            )
        }
    }
}