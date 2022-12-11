package day19

import readInput

private fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("test_input")
    check(part1(testInput) == 24000)

    val input = readInput("input")
    println(part1(input))
    println(part2(input))
}
