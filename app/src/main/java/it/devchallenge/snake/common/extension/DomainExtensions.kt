package it.devchallenge.snake.common.extension

import android.graphics.Point
import it.devchallenge.snake.domain.model.Direction

fun Point.movedPoint(direction: Direction): Point {
    val (dx, dy) = when (direction) {
        Direction.LEFT -> -1 to 0
        Direction.TOP -> 0 to -1
        Direction.RIGHT -> 1 to 0
        Direction.BOTTOM -> 0 to -1
    }
    return Point(x + dx, y + dy)
}