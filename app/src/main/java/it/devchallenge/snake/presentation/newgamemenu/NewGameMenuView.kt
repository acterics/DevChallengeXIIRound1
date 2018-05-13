package it.devchallenge.snake.presentation.newgamemenu

import com.arellomobile.mvp.MvpView

interface NewGameMenuView: MvpView {
    fun showAdditionalAccelerometerConfigs()
    fun showAdditionalNetworkConfigs()
    fun hideAdditionalControlConfigs()
    fun showAccelerometerSense(sense: Int)
    fun showSnakeSpeed(snakeSpeed: Int)
    fun showAdditionalObstaclesConfig()
    fun hideAdditionalFieldConfig()
    fun showSnakeLength(snakeLength: Int)
    fun setAccelerometerSense(sense: Int)
    fun setSnakeSpeed(snakeSpeed: Int)
    fun setSnakeLength(snakeLength: Int)
    fun showIllegalControlTypeError()

}