package it.devchallenge.snake.di

import android.app.Application
import it.devchallenge.snake.di.app.AppComponent
import it.devchallenge.snake.di.app.AppModule
import it.devchallenge.snake.di.app.DaggerAppComponent

object ComponentsManager {

    val components: MutableMap<String, Any?> = mutableMapOf()

    lateinit var appComponent: AppComponent


    fun initAppComponent(app: Application) {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(app))
                .build()
    }

}