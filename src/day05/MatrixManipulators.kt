package day05

class MatrixManipulator(drawing: List<String>, numberOfStacks: Int, private val mover: CrateMover) {

    private var columns: List<List<Char>>

    init {
        columns = drawing.toMatrix(numberOfStacks).sanitizeToColumns()
    }

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

    private fun List<String>.toMatrix(numberOfStacks: Int): List<List<Char>> =
        map { row ->
            (0 until numberOfStacks).map { i -> row.padEnd(numberOfStacks.getDrawingIndex())[i.getDrawingIndex()] }
        }

    private fun List<List<Char>>.sanitizeToColumns(): List<List<Char>> =
        (0 until first().size).map { i ->
            (indices).map { j -> get(j)[i] }.filter { it != ' ' }
        }
}
