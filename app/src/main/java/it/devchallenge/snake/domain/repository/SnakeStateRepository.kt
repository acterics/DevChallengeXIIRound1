package it.devchallenge.snake.domain.repository

import android.graphics.Point
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import it.devchallenge.snake.domain.model.Direction
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.SnakeState

interface SnakeStateRepository {
    fun makeSnakeStep(): Completable
    fun changeSnakeDirection(direction: Direction): Completable


    /**
     * if game is over return completable with error #EndOfGameException
     */
    fun isEndOfGame(field: Field): Completable

    /**
     * emit new food position if snake consumed previous food
     */
    fun requestFoodPosition(field: Field): Maybe<Point>

    fun getCurrentSnakeState(): Single<SnakeState>

}