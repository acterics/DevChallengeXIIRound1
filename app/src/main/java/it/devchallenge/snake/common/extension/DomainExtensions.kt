package it.devchallenge.snake.common.extension

import android.graphics.Point
import it.devchallenge.snake.domain.model.Direction
import it.devchallenge.snake.domain.model.Field
import kotlin.math.absoluteValue

fun Point.movedPoint(direction: Direction): Point {
    val (dx, dy) = when (direction) {
        Direction.LEFT -> -1 to 0
        Direction.TOP -> 0 to -1
        Direction.RIGHT -> 1 to 0
        Direction.BOTTOM -> 0 to 1
    }
    return Point(x + dx, y + dy)
}

fun Point.borderlessMovedPoint(direction: Direction, field: Field): Point {
    val (dx, dy) = when (direction) {
        Direction.LEFT -> -1 to 0
        Direction.TOP -> 0 to -1
        Direction.RIGHT -> 1 to 0
        Direction.BOTTOM -> 0 to 1
    }
    return Point(((x + dx + field.width) % (field.width)), ((y + dy + field.height) % (field.height)))
}