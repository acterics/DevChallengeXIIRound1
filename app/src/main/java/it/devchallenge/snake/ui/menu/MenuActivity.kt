package it.devchallenge.snake.ui.menu

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import it.devchallenge.snake.R
import it.devchallenge.snake.domain.model.ControlType
import it.devchallenge.snake.domain.model.FieldType
import it.devchallenge.snake.domain.model.GameConfiguration
import it.devchallenge.snake.ui.snakegame.SnakeGameActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_GAME_CONFIGURATION = "it.devchallenge.snake.ui.menu.EXTRA_GAME_CONFIGURATION"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.holderContent, MenuFragment())
                    .commitNow()
        }

    }
}