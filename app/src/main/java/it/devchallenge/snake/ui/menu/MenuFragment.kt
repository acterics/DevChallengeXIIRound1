package it.devchallenge.snake.ui.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.devchallenge.snake.R
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MenuFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btStart.setOnClickListener {
            fragmentManager!!
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.holderContent, NewGameMenuFragment())
                    .commit()
        }
    }
}