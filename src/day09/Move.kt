package day09


enum class Move {
    LEFT,
    UP,
    RIGHT,
    DOWN;

    companion object {
        fun fromList(input: List<String>): List<Move> =
            input.flatMap {
                val (type, steps) = it.split(" ")
                List(steps.toInt()) {
                    when (type) {
                        "L" -> LEFT
                        "U" -> UP
                        "D" -> DOWN
                        else -> RIGHT
                    }
                }
            }
    }
}
