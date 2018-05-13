package it.devchallenge.snake.data.repository

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL
import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import it.devchallenge.snake.domain.model.ControlType
import it.devchallenge.snake.domain.model.Direction
import it.devchallenge.snake.domain.model.PlayerEvent
import it.devchallenge.snake.domain.repository.PlayerEventRepository
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

class AccelerometerPlayerEventRepository(private val sensorManager: SensorManager,
                                         private val config: ControlType.Accelerometer):
        PlayerEventRepository {
    private val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    override fun getPlayerEvents(): Flowable<PlayerEvent> {
        return Flowable.create(FlowableOnSubscribe<PlayerEvent> { emitter ->
            var previousAccX = 0f
            var previousAccZ = 0f
            val sensorEventListener = object: SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent) {
                    if (emitter.isCancelled) {
                        sensorManager.unregisterListener(this)
                    }
                    val dx = event.values[0] - previousAccX
                    val dz = event.values[2] - previousAccZ
                    detectDirection(
                            dx, dz
                    )?.let { direction ->
                        emitter.onNext(PlayerEvent(
                                direction,
                                System.currentTimeMillis()
                        ))
                    }
                    previousAccX = event.values[0]
                    previousAccZ = event.values[2]
                }
            }
            sensorManager.registerListener(sensorEventListener, accelerometerSensor, SENSOR_DELAY_NORMAL)
        }, BackpressureStrategy.LATEST)
                .debounce(50L, TimeUnit.MILLISECONDS)



    }


    private fun detectDirection(dx: Float,
                                dz: Float): Direction? {
        Log.e("DEBUG", "detectDirection: dx: $dx, dz: $dz")

        return if (dx.absoluteValue > dz.absoluteValue) {
            when {
                dx > 4 -> Direction.TOP
                dx < -4 -> Direction.BOTTOM
                else -> null
            }
        } else {
            when {
                dz > 4 -> Direction.LEFT
                dz < -4 -> Direction.RIGHT
                else -> null
            }
        }
    }

}