package day05

import readInput

/**
 * The expedition can depart as soon as the final supplies have been unloaded from the ships.
 * Supplies are stored in stacks of marked crates, but because the needed supplies are buried under many other crates, the crates need to be rearranged.
 *
 * The ship has a giant cargo crane capable of moving crates between stacks.
 * To ensure none of the crates get crushed or fall over, the crane operator will rearrange them in a series of carefully-planned steps.
 * After the crates are rearranged, the desired crates will be at the top of each stack.
 *
 * The Elves don't want to interrupt the crane operator during this delicate procedure, but they forgot to ask her which crate will end up where, and they want to be ready to unload them as soon as possible so they can embark.
 *
 * They do, however, have a drawing of the starting stacks of crates and the rearrangement procedure (your puzzle input). For example:
 *
 * [D]
 * [N] [C]
 * [Z] [M] [P]
 * 1   2   3
 *
 * move 1 from 2 to 1
 * move 3 from 1 to 3
 * move 2 from 2 to 1
 * move 1 from 1 to 2
 * In this example, there are three stacks of crates. Stack 1 contains two crates: crate Z is on the bottom, and crate N is on top.
 * Stack 2 contains three crates; from bottom to top, they are crates M, C, and D. Finally, stack 3 contains a single crate, P.
 *
 * Then, the rearrangement procedure is given. In each step of the procedure, a quantity of crates is moved from one stack to a different stack.
 * In the first step of the above rearrangement procedure, one crate is moved from stack 2 to stack 1, resulting in this configuration:
 *
 * [D]
 * [N] [C]
 * [Z] [M] [P]
 * 1   2   3
 * In the second step, three crates are moved from stack 1 to stack 3.
 * Crates are moved one at a time, so the first crate to be moved (D) ends up below the second and third crates:
 *
 * [Z]
 * [N]
 * [C] [D]
 * [M] [P]
 * 1   2   3
 * Then, both crates are moved from stack 2 to stack 1. Again, because crates are moved one at a time, crate C ends up below crate M:
 *
 * [Z]
 * [N]
 * [M]     [D]
 * [C]     [P]
 * 1   2   3
 * Finally, one crate is moved from stack 1 to stack 2:
 *
 * [Z]
 * [N]
 * [D]
 * [C] [M] [P]
 * 1   2   3
 * The Elves just need to know which crate will end up on top of each stack; in this example,
 * the top crates are C in stack 1, M in stack 2, and Z in stack 3, so you should combine these together and give the Elves the message CMZ.
 *
 * After the rearrangement procedure completes, what crate ends up on top of each stack?
 *
 * PART 2:
 *
 */

fun List<String>.getSeparatorIndex() = indexOf("")

fun List<String>.getLabels(separatorIndex: Int): String =
    get(separatorIndex - 1).trim()

fun String.getNumberOfStacks(): Int =
    last().digitToInt()

fun List<String>.getDrawing(separatorIndex: Int): List<String> =
    subList(fromIndex = 0, toIndex = separatorIndex - 1)

fun Int.getDrawingIndex() =
    this + 1 + 3 * this

fun List<String>.drawingToMatrix(numberOfStacks: Int): List<List<Char>> =
    map { row ->
        (0 until numberOfStacks).map { i -> row.padEnd(numberOfStacks.getDrawingIndex())[i.getDrawingIndex()] }
    }

fun List<List<Char>>.drawingToColumns(): List<List<Char>> =
    (0 until first().size).map { i ->
        (indices).map { j -> get(j)[i] }.filter { it != ' ' }
    }

data class Move(
    val quantity: Int,
    val fromIndex: Int,
    val toIndex: Int
)

fun List<String>.getMoves(separatorIndex: Int): List<String> =
    subList(fromIndex = separatorIndex + 1, toIndex = size)

fun String.toMove(): Move {
    val (quantity, from, to) = filterNot { it.isLetter() }
        .split(' ')
        .filter { it.isNotBlank() }
        .map { it.toInt() }

    return Move(quantity, from - 1, to - 1)
}

fun List<List<Char>>.move(move: Move): List<List<Char>> = with(move) {
    val taken = get(fromIndex).take(quantity).reversed()
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

fun main() {
    fun part1(input: List<String>): String {
        val separatorIndex = input.getSeparatorIndex()
        val numberOfStacks = input.getLabels(separatorIndex).getNumberOfStacks()
        val columns = input.getDrawing(separatorIndex).drawingToMatrix(numberOfStacks).drawingToColumns()
        val moves = input.getMoves(separatorIndex).map { it.toMove() }

        val result = moves.scan(columns) { acc, move ->
            acc.move(move)
        }.last()

        return result.map { it.first() }.joinToString("")
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
