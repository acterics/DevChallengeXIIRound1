package it.devchallenge.snake.presentation.newgamemenu

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import it.devchallenge.snake.R
import it.devchallenge.snake.domain.model.ControlType
import it.devchallenge.snake.domain.model.FieldType
import it.devchallenge.snake.domain.model.GameConfiguration
import it.devchallenge.snake.domain.model.ObstaclesType
import it.devchallenge.snake.ui.menu.MenuActivity
import it.devchallenge.snake.ui.snakegame.SnakeGameActivity

@InjectViewState
class NewGameMenuPresenter: MvpPresenter<NewGameMenuView>() {

    companion object {
        const val DEFAULT_ACCELEROMETER_SENSE = 50
        const val DEFAULT_SNAKE_SPEED = 50
        const val DEFAULT_SNAKE_LENGTH = 3
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setAccelerometerSense(DEFAULT_ACCELEROMETER_SENSE)
        viewState.setSnakeSpeed(DEFAULT_SNAKE_SPEED)
        viewState.setSnakeLength(DEFAULT_SNAKE_LENGTH)
    }

    fun onControlTypeSelected(selectedId: Int) {
        when (selectedId) {
            R.id.rbtAccelerometer -> viewState.showAdditionalAccelerometerConfigs()
            R.id.rbtNetwork -> viewState.showAdditionalNetworkConfigs()
            else -> viewState.hideAdditionalControlConfigs()
        }
    }

    fun onObstaclesStateChanged(withObstacles: Boolean) {
        when(withObstacles) {
            true -> viewState.showAdditionalObstaclesConfig()
            false -> viewState.hideAdditionalFieldConfig()
        }
    }

    fun onAccelerometerSenseChanged(sense: Int) {
        viewState.showAccelerometerSense(sense)
    }

    fun onSnakeSpeedChanged(snakeSpeed: Int) {
        viewState.showSnakeSpeed(snakeSpeed)
    }


    fun onSnakeLengthChanged(snakeLength: Int) {
        viewState.showSnakeLength(snakeLength)
    }

    fun onStartGame(context: Context?,
                    selectedControlTypeId: Int,
                    networkGameName: String,
                    accelerometerSense: Int,
                    selectedFieldTypeId: Int,
                    selectedObstaclesTypeId: Int,
                    snakeSpeed: Int,
                    snakeLength: Int) {

        val controlType = when(selectedControlTypeId) {
            R.id.rbtNetwork -> ControlType.Network(networkGameName)
            R.id.rbtAccelerometer -> ControlType.Accelerometer(accelerometerSense)
            else -> viewState.showIllegalControlTypeError().let { return }
        }

        val obstaclesType = when(selectedObstaclesTypeId) {
            R.id.rbtObstaclesNormal -> ObstaclesType.NORMAL
            R.id.rbtObstaclesDifficult -> ObstaclesType.DIFFICULT
            R.id.rbtObstaclesCustom -> ObstaclesType.CUSTOM
            else -> ObstaclesType.NONE
        }

        val hasBorders = when(selectedFieldTypeId) {
            R.id.rbtBordered -> true
            else -> false
        }

        val fieldType = FieldType(hasBorders, obstaclesType)

        context?.startActivity(Intent(context, SnakeGameActivity::class.java)
                .putExtra(MenuActivity.EXTRA_GAME_CONFIGURATION, GameConfiguration(
                        controlType,
                        fieldType,
                        snakeSpeed,
                        snakeLength
                )))
    }


}