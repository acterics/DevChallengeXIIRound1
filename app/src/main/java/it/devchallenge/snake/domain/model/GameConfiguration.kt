package it.devchallenge.snake.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameConfiguration(
        val controlType: ControlType,
        val fieldType: FieldType,
        val speed: Int,
        val initialSnakeLength: Int
): Parcelable