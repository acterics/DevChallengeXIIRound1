package it.devchallenge.snake.domain.model

enum class Direction(val axis: DirectionAxis) {
    LEFT(DirectionAxis.HORIZONTAL),
    TOP(DirectionAxis.VERTICAL),
    RIGHT(DirectionAxis.HORIZONTAL),
    BOTTOM(DirectionAxis.VERTICAL)
}