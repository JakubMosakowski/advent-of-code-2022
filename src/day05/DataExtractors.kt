package day05

fun List<String>.getSeparatorIndex() = indexOf("")

fun List<String>.getNumberOfStacks(separatorIndex: Int): Int =
    get(separatorIndex - 1).trim().last().digitToInt()

fun List<String>.getDrawing(separatorIndex: Int): List<String> =
    subList(fromIndex = 0, toIndex = separatorIndex - 1)

fun List<String>.getMoves(separatorIndex: Int): List<String> =
    subList(fromIndex = separatorIndex + 1, toIndex = size)
