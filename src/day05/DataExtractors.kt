package day05

internal fun List<String>.getSeparatorIndex() = indexOf("")

internal fun List<String>.getNumberOfStacks(separatorIndex: Int): Int =
    get(separatorIndex - 1).trim().last().digitToInt()

internal fun List<String>.getDrawing(separatorIndex: Int): List<String> =
    subList(fromIndex = 0, toIndex = separatorIndex - 1)

internal fun List<String>.getMoves(separatorIndex: Int): List<String> =
    subList(fromIndex = separatorIndex + 1, toIndex = size)
