package it.devchallenge.snake.ui.snakegame

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.SnakeState


class SnakeFieldAndroidView : View {

    private val snakePaint = Paint()
    private val foodPaint = Paint()

    private var foodRect: RectF = RectF()
    private var snakePath: Path = Path()


    private var cellWidth = 0f
    private var cellHeight = 0f

    private var fieldWidth = 0
    private var fieldHeight = 0
    private var foodPosition = Point()

    private var isFirstInvalidated = false


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        snakePaint.color = Color.GREEN
        foodPaint.color = Color.RED
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!isFirstInvalidated) return


        canvas.drawPath(snakePath, snakePaint)
        canvas.drawRect(foodRect, foodPaint)
        snakePath.close()

    }


    fun invalidateField(field: Field, snakeState: SnakeState) {
        if (!isFirstInvalidated) isFirstInvalidated = true
        if (isFieldWidthChanged(field.width)) {
            fieldWidth = field.width
            cellWidth = computeCellWidth()
        }
        if (isFieldHeightChanged(field.height)) {
            fieldHeight = field.height
            cellHeight = computeCellHeight()
        }
        if (isFoodPositionChanged(field.foodPosition)) {
            foodPosition = field.foodPosition
            foodRect = getCellRect(foodPosition)
        }

        snakePath.reset()
        snakeState.cells.map { position -> getCellRect(position) }
                .forEach { rect -> snakePath.addRect(rect, Path.Direction.CW) }


        invalidate()
    }


    private fun isFieldWidthChanged(newFieldWidth: Int) = fieldWidth != newFieldWidth
    private fun isFieldHeightChanged(newFieldHeight: Int) = fieldHeight != newFieldHeight
    private fun isFoodPositionChanged(newFoodPosition: Point) = foodPosition != newFoodPosition

    private fun computeCellWidth(): Float = width / fieldWidth.toFloat()
    private fun computeCellHeight(): Float = height / fieldHeight.toFloat()

    private fun getCellRect(position: Point): RectF {
        return RectF(
                position.x * cellWidth - cellWidth / 2,
                position.y * cellHeight - cellHeight / 2,
                position.x * cellWidth + cellWidth / 2,
                position.y * cellHeight + cellHeight / 2
        )
    }


}
