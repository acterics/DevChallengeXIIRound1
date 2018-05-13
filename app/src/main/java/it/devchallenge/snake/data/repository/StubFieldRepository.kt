package it.devchallenge.snake.data.repository

import android.content.Context
import android.graphics.Point
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.FieldType
import it.devchallenge.snake.domain.repository.FieldRepository
import java.util.*

class StubFieldRepository(private val context: Context,
                          private val fieldType: FieldType): FieldRepository {
    override fun getField(): Single<Field> {
        val random = Random()
        val width = context.resources.displayMetrics.widthPixels / 50
        val height = context.resources.displayMetrics.heightPixels / 50
        return Field(
                width = width,
                height = height,
                fieldType = fieldType,
                barriers = null,
                foodPosition = Point(random.nextInt(width), random.nextInt(height))
        ).toSingle()
    }
}