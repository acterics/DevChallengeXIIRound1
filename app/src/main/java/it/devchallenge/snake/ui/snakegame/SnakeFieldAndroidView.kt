package it.devchallenge.snake.ui.snakegame

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import it.devchallenge.snake.domain.model.Field
import it.devchallenge.snake.domain.model.FieldType
import it.devchallenge.snake.domain.model.SnakeState


class SnakeFieldAndroidView : View {

    private val snakePaint = Paint()
    private val foodPaint = Paint()
    private val barrierPaint = Paint()

    private var foodRect: RectF = RectF()
    private var snakePath: Path = Path()
    private var barrierPath: Path = Path()


    private var cellWidth = 0f
    private var cellHeight = 0f

    private var fieldWidth = 0
    private var fieldHeight = 0
    private var foodPosition = Point()
    private var fieldType: FieldType? = null

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
        barrierPaint.color = Color.BLACK
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!isFirstInvalidated) return


        canvas.drawPath(snakePath, snakePaint)
        canvas.drawRect(foodRect, foodPaint)
        canvas.drawPath(barrierPath, barrierPaint)
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
        if (isFieldTypeChanged(field.fieldType)) {
            fieldType = field.fieldType
            barrierPath.reset()
            if (field.fieldType.hasBorders) {
                barrierPath.addRect(0f, 0f, cellWidth, field.height * cellHeight, Path.Direction.CW)
                barrierPath.addRect(0f, 0f, field.width * cellWidth, cellHeight, Path.Direction.CW)
                barrierPath.addRect((field.width - 1) * cellWidth, 0f, field.width * cellWidth, field.height * cellHeight, Path.Direction.CW)
                barrierPath.addRect(0f  , (field.height -1) * cellHeight, field.width * cellWidth, field.height * cellHeight, Path.Direction.CW)
            }
            field.barriers?.forEach { barrierPoint ->
                barrierPath.addRect(getCellRect(barrierPoint), Path.Direction.CW)
            }
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
    private fun isFieldTypeChanged(newFieldType: FieldType) = fieldType != newFieldType

    private fun computeCellWidth(): Float = width / fieldWidth.toFloat()
    private fun computeCellHeight(): Float = height / fieldHeight.toFloat()

    private fun getCellRect(position: Point): RectF {
        return RectF(
                position.x * cellWidth,
                position.y * cellHeight,
                position.x * cellWidth + cellWidth,
                position.y * cellHeight + cellHeight
        )
    }


}
