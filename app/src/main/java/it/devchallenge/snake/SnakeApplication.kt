package it.devchallenge.snake

import android.app.Application
import it.devchallenge.snake.di.ComponentsManager

class SnakeApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        ComponentsManager.initAppComponent(this)

    }
}