package it.devchallenge.snake.common.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
import it.devchallenge.snake.di.ComponentsManager

abstract class BaseScopedFragment: MvpAppCompatFragment() {

    protected abstract fun injectComponent()
    protected open fun rejectComponent() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateWithoutInjections()
        injectComponent()
        onCreateWithoutPresenter()
        super.onCreate(savedInstanceState)
        onCreateInitialized(savedInstanceState)
    }

    override fun onDestroy() {
        if (isRemoving || activity!!.isFinishing && !isStateSaved) {
            rejectComponent()
        }
        super.onDestroy()
    }

    protected open fun onCreateWithoutPresenter() {}
    protected open fun onCreateWithoutInjections() {}
    protected open fun onCreateInitialized(savedInstanceState: Bundle?) {}

    @Suppress("UNCHECKED_CAST")
    protected fun <T>getComponent(componentName: String): T? {
        return ComponentsManager.components[componentName] as T?
    }


}