package it.devchallenge.snake.common.extension

import io.reactivex.Flowable
import it.devchallenge.snake.common.extension.rx.FlowableUnmodifiedScan

fun <T> Flowable<T>.scanUnmodified(accumulator: (previous: T, next: T) -> T): Flowable<T> {
    return lift(FlowableUnmodifiedScan(accumulator))
}