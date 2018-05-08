package it.devchallenge.snake.presentation.snakefield

import com.arellomobile.mvp.MvpView
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.SnakeState

interface SnakeFieldView: MvpView {
    fun invalidateField(field: Field, snakeState: SnakeState)
}