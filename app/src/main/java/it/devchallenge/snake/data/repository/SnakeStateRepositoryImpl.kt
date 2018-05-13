package it.devchallenge.snake.data.repository

import android.graphics.Point
import android.util.Log
import io.reactivex.*
import it.devchallenge.snake.common.extension.borderlessMovedPoint
import it.devchallenge.snake.common.extension.movedPoint
import it.devchallenge.snake.common.extension.select
import it.devchallenge.snake.domain.exception.EndOfGameException
import it.devchallenge.snake.domain.executor.ExecutionScheduler
import it.devchallenge.snake.domain.model.Direction
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.FieldType
import it.devchallenge.snake.domain.model.SnakeState
import it.devchallenge.snake.domain.repository.SnakeStateRepository
import java.util.*
import java.util.concurrent.LinkedBlockingDeque

class SnakeStateRepositoryImpl(private val field: Field,
                               private val initialLength: Int,
                               private val executionScheduler: ExecutionScheduler):
        SnakeStateRepository {

    private val snakeState: SnakeState
    private var currentScore: Int = 0
    private var currentDirection: Direction


    init {
        var currentHead = Point(field.width / 2 - initialLength, field.height / 2)
        val snakeQueue = LinkedBlockingDeque<Point>()
        (0..initialLength).forEach {
            snakeQueue.addLast(currentHead)
            currentHead = currentHead.movedPoint(Direction.RIGHT)
        }

        snakeState = SnakeState(Direction.RIGHT, snakeQueue)
        currentDirection = snakeState.direction
    }

    override fun makeSnakeStep(field: Field): Completable {
        return Completable.fromAction {
            snakeState.cells.let { queue ->
                snakeState.direction = currentDirection
                if (field.fieldType.hasBorders) {
                    queue.addLast(queue.last.movedPoint(snakeState.direction))
                } else {
                    queue.addLast(queue.last.borderlessMovedPoint(snakeState.direction, field))
                }
                if (queue.last != field.foodPosition) {
                    queue.removeFirst()
                }
            }
        }
    }

    override fun changeSnakeDirection(direction: Direction): Completable {
        return Completable.fromAction {
            if (direction.axis != snakeState.direction.axis) {
                currentDirection = direction
            }
        }.compose(executionScheduler.highPriorityCompletable())
    }

    override fun isEndOfGame(field: Field): Completable {
        return Completable.fromAction {
            snakeState.cells.last.let { head ->
                snakeState.cells.forEachIndexed { index, point ->
                    if (index != snakeState.cells.size - 1 && point == head) {
                        throw EndOfGameException()
                    }
                }
                if (field.barriers?.any { it == head } == true) {
                    throw EndOfGameException()
                }
                if (field.fieldType.hasBorders) {
                    if (head.x >= field.width - 1|| head.x <= 0 ||
                            head.y >= field.height -1 || head.y <= 0) {
                        Log.e("DEBUG", "isEndOfGame: head: $head")

                        throw EndOfGameException()
                    }
                }
            }
        }.compose(executionScheduler.highPriorityCompletable())

    }

    override fun requestFoodPosition(field: Field): Maybe<Point> {
        return Maybe.fromCallable {
            if (snakeState.cells.last == field.foodPosition) {
                generateFoodPosition(field)
            } else {
                null
            }
        }
    }

    override fun getCurrentSnakeState(): Single<SnakeState> {
        return Single.just(snakeState)
    }

//    override fun getSnakeScore(): Flowable<Int> {
//        Flowable.create<Int>({ emitter ->
//            emitter.
//        }, BackpressureStrategy.ERROR)
//    }

    private fun generateFoodPosition(field: Field): Point {
        val random = Random()
        val excluded = snakeState.cells + (field.barriers ?: listOf())
        val startPosition = if (field.fieldType.hasBorders) {
            1
        } else {
            0
        }
        return Point(
                random.select((startPosition until field.width) - excluded.map { it.x }),
                random.select((startPosition until field.height) - excluded.map { it.y })
        )
    }


}