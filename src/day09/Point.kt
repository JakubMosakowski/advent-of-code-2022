package day09

import day09.Move.DOWN
import day09.Move.LEFT
import day09.Move.RIGHT
import day09.Move.UP
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.sign

internal data class Point(val x: Int, val y: Int) {

    fun move(move: Move): Point = when (move) {
        LEFT -> Point(x = x - 1, y = y)
        UP -> Point(x = x, y = y + 1)
        RIGHT -> Point(x = x + 1, y = y)
        DOWN -> Point(x = x, y = y - 1)
    }

    fun follow(point: Point): Point {
        val differenceX = (point.x - x).toDouble()
        val differenceY = (point.y - y).toDouble()

        return when {
            isNotTouchingDiagonally(differenceX, differenceY) ->
                Point(x.bumpValue(differenceX), y.bumpValue(differenceY))
            isNotTouchingPerpendicularly(differenceX) ->
                Point(x.bumpValue(differenceX), y)
            isNotTouchingPerpendicularly(differenceY) ->
                Point(x, y.bumpValue(differenceY))
            else -> this
        }
    }

    override fun toString(): String = "x=$x y=$y"

    private fun isNotTouchingDiagonally(differenceX: Double, differenceY: Double) =
        hypot(differenceX, differenceY) > 2

    private fun isNotTouchingPerpendicularly(difference: Double) =
        abs(difference) == 2.0

    // Change value by 1 (add or subtract, based on sign)
    private fun Int.bumpValue(by: Double): Int =
        this + sign(by).toInt()
}
