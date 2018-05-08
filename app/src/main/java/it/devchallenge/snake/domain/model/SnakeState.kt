package it.devchallenge.snake.domain.model

import android.graphics.Point
import java.util.*

data class SnakeState(var direction: Direction,
                      val cells: Queue<Point>) {


}