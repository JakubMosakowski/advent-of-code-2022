package day05

internal class MatrixManipulator(
    drawing: List<String>,
    private val numberOfStacks: Int,
    private val mover: CrateMover
) {

    private var columns: List<List<Char>> = drawing.toColumns()

    fun move(moves: List<Move>): List<List<Char>> =
        moves.fold(columns) { acc, currentMove ->
            acc.move(currentMove)
        }

    private fun List<List<Char>>.move(move: Move): List<List<Char>> = with(move) {
        val taken = mover.take(get(fromIndex), quantity)
        val afterSubtract = get(fromIndex).drop(quantity)
        val afterAdd = taken + get(toIndex)

        mapIndexed { index, chars ->
            when (index) {
                fromIndex -> afterSubtract
                toIndex -> afterAdd
                else -> chars
            }
        }
    }

    private fun List<String>.toColumns(): List<List<Char>> =
        (0 until numberOfStacks).map { columnIndex ->
            map { row -> row.padToMax()[columnIndex.getDrawingIndex()] }.filterNotBlank()
        }

    private fun String.padToMax(): String =
        padEnd(numberOfStacks.getDrawingIndex())

    private fun List<Char>.filterNotBlank(): List<Char> =
        filterNot { it == ' ' }

    private fun Int.getDrawingIndex() =
        this + 1 + 3 * this
}
