package it.devchallenge.snake.ui.snakegame

import android.os.Bundle
import it.devchallenge.snake.R
import it.devchallenge.snake.common.extension.delegate.subcomponent
import it.devchallenge.snake.common.ui.BaseScopedActivity
import it.devchallenge.snake.di.game.SnakeGameComponent
import it.devchallenge.snake.di.game.SnakeGameModule
import it.devchallenge.snake.domain.model.FieldContainer
import it.devchallenge.snake.domain.model.GameConfiguration
import it.devchallenge.snake.domain.repository.FieldRepository
import it.devchallenge.snake.ui.menu.MenuActivity.Companion.EXTRA_GAME_CONFIGURATION
import javax.inject.Inject

class SnakeGameActivity: BaseScopedActivity() {



    private val gameConfiguration by lazy<GameConfiguration> {
        intent.getParcelableExtra(EXTRA_GAME_CONFIGURATION)
    }

    private var component by subcomponent(SnakeGameComponent.NAME) {
        snakeGameComponentBuilder()
                .requestSnakeGameModule(SnakeGameModule(gameConfiguration))
                .build()
    }

    @Inject lateinit var fieldRepository: FieldRepository
    @Inject lateinit var fieldContainer: FieldContainer


    override fun injectComponent() {
        component?.inject(this)
    }

    override fun rejectComponent() {
        component = null
    }

    override fun onCreateInitialized(savedInstanceState: Bundle?) {
        super.onCreateInitialized(savedInstanceState)
        setContentView(R.layout.activity_snake_game)
        fieldRepository.getField()
                .subscribe { field ->
                    fieldContainer.field = field
                    supportFragmentManager.beginTransaction()
                            .add( R.id.holderContent, SnakeFieldFragment())
                            .commitNow()
                }
    }



}