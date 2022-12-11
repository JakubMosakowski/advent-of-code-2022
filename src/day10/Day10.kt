package day10

import readInput

private fun main() {
    fun part1(input: List<String>): Int {
        val cycles = input.toCycles()
        val importantCycles = listOf(20, 60, 100, 140, 180, 220)

        return importantCycles.sumOf { index -> index * cycles[index - 1] }
    }

    fun part2(input: List<String>) {
        val cycles = input.toCycles()

        cycles.forEachIndexed { index, value ->
            if (index == cycles.size - 1) return
            if (index % 40 == 0) {
                print("\n")
            }

            val sprite = listOf(value - 1, value, value + 1)
            if (index % 40 in sprite) {
                print("#")
            } else {
                print(".")
            }
        }
    }

    val testInput = readInput("test_input")
    check(part1(testInput) == 13140)
    part2(testInput)

    val input = readInput("input")
    println(part1(input))
    part2(input)
}

private fun List<String>.toCycles(): List<Int> {
    var currentValue = 1
    val cycles = mutableListOf(currentValue)

    forEach { line ->
        if (line == "noop") {
            cycles.add(currentValue)
        } else {
            val (_, num) = line.split(" ")
            cycles.add(currentValue)
            currentValue += num.toInt()
            cycles.add(currentValue)
        }
    }
    return cycles
}
