package it.devchallenge.snake.common.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity

abstract class BaseScopedActivity : MvpAppCompatActivity() {

    protected abstract fun injectComponent()
    protected open fun rejectComponent() {}


    final override fun onCreate(savedInstanceState: Bundle?) {
        onCreateWithoutInjections()

        injectComponent()

        onCreateWithoutPresenter()

        super.onCreate(savedInstanceState)

        onCreateInitialized(savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
        rejectComponent()
    }

    protected open fun onCreateWithoutPresenter() {}
    protected open fun onCreateWithoutInjections() {}
    protected open fun onCreateInitialized(savedInstanceState: Bundle?) {}





}
