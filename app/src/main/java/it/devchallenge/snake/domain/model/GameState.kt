package it.devchallenge.snake.domain.model

data class GameState(
        val playerEvents: MutableList<PlayerEvent> = mutableListOf(),
        val gameEvents: MutableList<GameEvent> = mutableListOf(),
        val field: Field
)