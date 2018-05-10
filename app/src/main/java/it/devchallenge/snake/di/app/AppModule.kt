package it.devchallenge.snake.di.app

import android.app.Application
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import dagger.Module
import dagger.Provides
import it.devchallenge.snake.domain.executor.ExecutionScheduler
import it.devchallenge.snake.domain.executor.ThreadScheduler
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideExecutionScheduler(threadScheduler: ThreadScheduler): ExecutionScheduler {
        return threadScheduler
    }

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideSensorManager(): SensorManager {
        return application.getSystemService(SENSOR_SERVICE) as SensorManager
    }

}