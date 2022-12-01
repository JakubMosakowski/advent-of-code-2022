/**
 * Instruction
 */

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day0X_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day0X")
    println(part1(input))
    println(part2(input))
}
