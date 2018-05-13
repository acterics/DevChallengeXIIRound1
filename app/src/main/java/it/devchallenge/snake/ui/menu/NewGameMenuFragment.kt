package it.devchallenge.snake.ui.menu

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import it.devchallenge.snake.R
import it.devchallenge.snake.presentation.newgamemenu.NewGameMenuPresenter
import it.devchallenge.snake.presentation.newgamemenu.NewGameMenuView
import kotlinx.android.synthetic.main.fragment_new_game_menu.*

class NewGameMenuFragment: MvpAppCompatFragment(), NewGameMenuView {


    @InjectPresenter lateinit var presenter: NewGameMenuPresenter
    @ProvidePresenter fun providePresenter(): NewGameMenuPresenter {
        return NewGameMenuPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_game_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        holderControlTypes.setOnCheckedChangeListener { _, checkedId ->
            presenter.onControlTypeSelected(checkedId)
        }

        chbtObstacles.setOnCheckedChangeListener { _, isChecked ->
            presenter.onObstaclesStateChanged(isChecked)
        }

        sbSense.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                presenter.onAccelerometerSenseChanged(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        sbSnakeSpeed.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                presenter.onSnakeSpeedChanged(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        sbSnakeLength.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                presenter.onSnakeLengthChanged(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        btStart.setOnClickListener {
            presenter.onStartGame(
                    context,
                    holderControlTypes.checkedRadioButtonId,
                    etNetworkGameName.text.toString(),
                    sbSense.progress,
                    holderFieldTypes.checkedRadioButtonId,
                    holderObstaclesTypes.checkedRadioButtonId,
                    sbSnakeSpeed.progress,
                    sbSnakeLength.progress
            )
        }
    }


    override fun showAdditionalAccelerometerConfigs() {
        TransitionManager.beginDelayedTransition(holderRoot)
        tvAdditionalControl.visibility = View.VISIBLE
        groupNetworkControlConfig.visibility = View.GONE
        groupAccelerometerConfig.visibility = View.VISIBLE
        TransitionManager.endTransitions(holderRoot)
    }

    override fun showAdditionalNetworkConfigs() {
        TransitionManager.beginDelayedTransition(holderRoot)
        tvAdditionalControl.visibility = View.VISIBLE
        groupAccelerometerConfig.visibility = View.GONE
        groupNetworkControlConfig.visibility = View.VISIBLE
        TransitionManager.endTransitions(holderRoot)
    }

    override fun hideAdditionalControlConfigs() {
        TransitionManager.beginDelayedTransition(holderRoot)
        tvAdditionalControl.visibility = View.GONE
        groupNetworkControlConfig.visibility = View.GONE
        groupAccelerometerConfig.visibility = View.GONE
        TransitionManager.endTransitions(holderRoot)
    }

    override fun showAccelerometerSense(sense: Int) {
        tvSensePointer.text = "$sense"
    }

    override fun showSnakeSpeed(snakeSpeed: Int) {
        tvSnakeSpeedPointer.text = "$snakeSpeed"
    }

    override fun showAdditionalObstaclesConfig() {
        TransitionManager.beginDelayedTransition(holderRoot)
        tvAdditionalFieldConfig.visibility = View.VISIBLE
        groupObstaclesConfig.visibility = View.VISIBLE
        holderObstaclesTypes.check(R.id.rbtObstaclesNormal)
        TransitionManager.endTransitions(holderRoot)
    }

    override fun hideAdditionalFieldConfig() {
        TransitionManager.beginDelayedTransition(holderRoot)
        tvAdditionalFieldConfig.visibility = View.GONE
        groupObstaclesConfig.visibility = View.GONE
        TransitionManager.endTransitions(holderRoot)
    }

    override fun showSnakeLength(snakeLength: Int) {
        tvSnakeLengthPointer.text = "$snakeLength"
    }

    override fun setAccelerometerSense(sense: Int) {
        sbSense.progress = sense
    }

    override fun setSnakeSpeed(snakeSpeed: Int) {
        sbSnakeSpeed.progress = snakeSpeed
    }

    override fun setSnakeLength(snakeLength: Int) {
        sbSnakeLength.progress = snakeLength
    }

    override fun showIllegalControlTypeError() {
        Toast.makeText(context, "Control type error", Toast.LENGTH_SHORT).show()
    }
}