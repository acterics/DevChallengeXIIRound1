package it.devchallenge.snake.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class GameConfiguration(

        val controlType: @RawValue ControlType,
        val fieldType: FieldType,
        val speed: Int,
        val initialSnakeLength: Int
): Parcelable {

}