package day05

data class Move(
    val quantity: Int,
    val fromIndex: Int,
    val toIndex: Int
) {
    companion object {
        fun from(string: String): Move {
            val (quantity, from, to) = string.filterNot { it.isLetter() }
                .split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }

            return Move(quantity, from - 1, to - 1)
        }
    }
}
