package it.devchallenge.snake.data.repository

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import it.devchallenge.snake.domain.model.Direction
import it.devchallenge.snake.domain.model.PlayerEvent
import it.devchallenge.snake.domain.repository.PlayerEventRepository

class AccelerometerPlayerEventRepository(private val sensorManager: SensorManager):
        PlayerEventRepository {
    private val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    override fun getPlayerEvents(): Flowable<PlayerEvent> {

        return Flowable.create({ emitter ->
            val sensorEventListener = object: SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent) {
                    if (emitter.isCancelled) {
                        sensorManager.unregisterListener(this)
                    }
                    detectDirection(
                            event.values[0],
                            event.values[1],
                            event.values[2]
                    )?.let { direction ->
                        emitter.onNext(PlayerEvent(
                                direction,
                                System.currentTimeMillis()
                        ))
                    }
                }
            }
            sensorManager.registerListener(sensorEventListener, accelerometerSensor, SENSOR_DELAY_NORMAL)
        }, BackpressureStrategy.LATEST)
    }


    private fun detectDirection(xAcceleration: Float,
                                yAcceleration: Float,
                                zAcceleration: Float): Direction? {
        return when {
            xAcceleration < -7 -> Direction.TOP
            xAcceleration > 7 -> Direction.BOTTOM
            yAcceleration > 7 -> Direction.RIGHT
            yAcceleration < -7 -> Direction.LEFT
            else -> null
        }

    }

}