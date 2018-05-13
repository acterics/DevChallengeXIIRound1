package it.devchallenge.snake.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FieldType(val hasBorders: Boolean,
                     val obstaclesType: ObstaclesType): Parcelable