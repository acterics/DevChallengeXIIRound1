package it.devchallenge.snake.common.extension.delegate

import it.devchallenge.snake.di.ComponentsManager
import it.devchallenge.snake.di.app.AppComponent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
fun <T, P>subcomponent(componentName: String,
                       parentComponentName: String,
                       block: P.() -> T?) =
        DaggerSubcomponentDelegate<Any?, T?, P>(
                componentName,
                ComponentsManager.components[parentComponentName] as P,
                block
        )

fun <T>subcomponent(componentName: String, block: AppComponent.() -> T?) =
        DaggerSubcomponentDelegate<Any?, T?, AppComponent>(
                componentName,
                ComponentsManager.appComponent,
                block
        )

fun <T>lazySubcomponent(componentName: String, block: AppComponent.() -> T?) =
        LazyDaggerSubcomponentDelegate<Any?, T?, AppComponent>(
                { componentName },
                { ComponentsManager.appComponent },
                block
        )

@Suppress("UNCHECKED_CAST")
fun <T, P>lazySubcomponent(provideComponentName: () -> String,
                           parentComponentName: String,
                           block: P.() -> T?) =
        LazyDaggerSubcomponentDelegate<Any?, T?, P>(
                provideComponentName,
                { ComponentsManager.components[parentComponentName] as P },
                block
        )

@Suppress("UNCHECKED_CAST")
fun <T, P>lazySubcomponent(componentName: String,
                           provideParentComponentName: () -> String,
                           block: P.() -> T?) =
        LazyDaggerSubcomponentDelegate<Any?, T?, P>(
                { componentName },
                { ComponentsManager.components[provideParentComponentName()] as P },
                block
        )

@Suppress("UNCHECKED_CAST")
fun <T, P>lazySubcomponent(provideComponentName: () -> String,
                           provideParentComponentName: () -> String,
                           block: P.() -> T?) =
        LazyDaggerSubcomponentDelegate<Any?, T?, P>(
                provideComponentName,
                { ComponentsManager.components[provideParentComponentName()] as P },
                block
        )



class DaggerSubcomponentDelegate<in R, T, P>(private val componentName: String,
                                             private val parentComponent: P,
                                             private val initializer: P.() -> T?):
        ReadWriteProperty<R, T?> {

    private var initialized = false
    private var value: T? = null

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (!initialized) {
            value = parentComponent.initializer()
            ComponentsManager.components[componentName] = value
        }
        return value!!
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
        if (value == null) {
            ComponentsManager.components[componentName] = null
        }
        this.value = value
    }
}


class LazyDaggerSubcomponentDelegate<in R, T, P>(private val provideComponentName: () -> String,
                                                 private val provideParentComponent: () -> P,
                                                 private val initializer: P.() -> T?) :
        ReadWriteProperty<R, T?> {

    private var initialized = false
    private var value: T? = null
    private val componentName: String by lazy { provideComponentName() }

    override fun getValue(thisRef: R, property: KProperty<*>): T {

        if (!initialized) {
            value = provideParentComponent().initializer()
            ComponentsManager.components[componentName] = value
        }
        return value!!
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
        if (value == null) {
            ComponentsManager.components[componentName] = null
        }
        this.value = value
    }
}