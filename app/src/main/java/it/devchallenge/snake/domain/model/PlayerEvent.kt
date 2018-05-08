package it.devchallenge.snake.domain.model

data class PlayerEvent(val direction: Direction,
                       val timestamp: Long)