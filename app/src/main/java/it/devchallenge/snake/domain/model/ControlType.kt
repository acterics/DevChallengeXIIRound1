package it.devchallenge.snake.domain.model

import java.io.Serializable

sealed class ControlType: Serializable {
    data class Accelerometer(private val sense: Int): ControlType()
    data class Network(private val gameName: String): ControlType()
    object Camera: ControlType()
    object Microphone: ControlType()
}