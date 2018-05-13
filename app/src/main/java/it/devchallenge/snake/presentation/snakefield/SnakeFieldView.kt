package it.devchallenge.snake.presentation.snakefield

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.SnakeState

@StateStrategyType(SkipStrategy::class)
interface SnakeFieldView: MvpView {
    fun invalidateField(field: Field, snakeState: SnakeState)
    fun invalidateScore(score: Int)

}