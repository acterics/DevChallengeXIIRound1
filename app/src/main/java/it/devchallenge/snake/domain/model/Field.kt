package it.devchallenge.snake.domain.model

import android.graphics.Point

data class Field(val height: Int,
                 val width: Int,
                 var foodPosition: Point)