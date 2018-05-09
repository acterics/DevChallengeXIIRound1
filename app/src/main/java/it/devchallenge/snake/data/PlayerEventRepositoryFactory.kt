package it.devchallenge.snake.data

import android.hardware.SensorManager
import it.devchallenge.snake.data.repository.AccelerometerPlayerEventRepository
import it.devchallenge.snake.data.repository.StubPlayerEventRepository
import it.devchallenge.snake.domain.executor.ExecutionScheduler
import it.devchallenge.snake.domain.model.ControlType
import it.devchallenge.snake.domain.repository.PlayerEventRepository
import javax.inject.Inject

class PlayerEventRepositoryFactory
@Inject constructor(private val executionScheduler: ExecutionScheduler,
                    private val sensorManager: SensorManager) {

    fun create(controlType: ControlType): PlayerEventRepository {
        return when(controlType) {
            ControlType.ACCELEROMETER -> AccelerometerPlayerEventRepository(sensorManager)
            else -> StubPlayerEventRepository(executionScheduler)
        }
    }

}