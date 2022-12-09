package day08

import readInput

/**
 * The expedition comes across a peculiar patch of tall trees all planted carefully in a grid.
 * The Elves explain that a previous expedition planted these trees as a reforestation effort.
 * Now, they're curious if this would be a good location for a tree house.
 *
 * First, determine whether there is enough tree cover here to keep a tree house hidden.
 * To do this, you need to count the number of trees that are visible from outside the grid
 * when looking directly along a row or column.
 *
 * The Elves have already launched a quadcopter to generate a map with the height of each tree
 * (your puzzle input). For example:
 *
 * 30373
 * 25512
 * 65332
 * 33549
 * 35390
 * Each tree is represented as a single digit whose value is its height,
 * where 0 is the shortest and 9 is the tallest.
 *
 * A tree is visible if all of the other trees between it and an edge of the grid are shorter than it.
 * Only consider trees in the same row or column; that is, only look up,
 * down, left, or right from any given tree.
 *
 * All of the trees around the edge of the grid are visible -
 * since they are already on the edge, there are no trees to block the view.
 * In this example, that only leaves the interior nine trees to consider:
 *
 * The top-left 5 is visible from the left and top.
 * (It isn't visible from the right or bottom since other trees of height 5 are in the way.)
 * The top-middle 5 is visible from the top and right.
 * The top-right 1 is not visible from any direction; for it to be visible,
 * there would need to only be trees of height 0 between it and an edge.
 * The left-middle 5 is visible, but only from the right.
 * The center 3 is not visible from any direction; for it to be visible,
 * there would need to be only trees of at most height 2 between it and an edge.
 * The right-middle 3 is visible from the right.
 * In the bottom row, the middle 5 is visible, but the 3 and 4 are not.
 * With 16 trees visible on the edge and another 5 visible in the interior,
 * a total of 21 trees are visible in this arrangement.
 *
 * Consider your map; how many trees are visible from outside the grid?
 *
 * PART 2:
 * Content with the amount of tree cover available, the Elves just need to know the best spot to build their tree house:
 * they would like to be able to see a lot of trees.
 *
 * To measure the viewing distance from a given tree,
 * look up, down, left, and right from that tree;
 * stop if you reach an edge or at the first tree that is the same height or taller than the tree under consideration.
 * (If a tree is right on the edge, at least one of its viewing distances will be zero.)
 *
 * The Elves don't care about distant trees taller than those found by the rules above;
 * the proposed tree house has large eaves to keep it dry,
 * so they wouldn't be able to see higher than the tree house anyway.
 *
 * In the example above, consider the middle 5 in the second row:
 *
 * 30373
 * 25512
 * 65332
 * 33549
 * 35390
 * Looking up, its view is not blocked; it can see 1 tree (of height 3).
 * Looking left, its view is blocked immediately; it can see only 1 tree (of height 5, right next to it).
 * Looking right, its view is not blocked; it can see 2 trees.
 * Looking down, its view is blocked eventually; it can see 2 trees (one of height 3,
 * then the tree of height 5 that blocks its view).
 * A tree's scenic score is found by multiplying together its viewing distance in each of the four directions.
 * For this tree, this is 4 (found by multiplying 1 * 1 * 2 * 2).
 *
 * However, you can do even better: consider the tree of height 5 in the middle of the fourth row:
 *
 * 30373
 * 25512
 * 65332
 * 33549
 * 35390
 * Looking up, its view is blocked at 2 trees (by another tree with a height of 5).
 * Looking left, its view is not blocked; it can see 2 trees.
 * Looking down, its view is also not blocked; it can see 1 tree.
 * Looking right, its view is blocked at 2 trees (by a massive tree of height 9).
 * This tree's scenic score is 8 (2 * 2 * 1 * 2); this is the ideal spot for the tree house.
 *
 * Consider each tree on your map. What is the highest scenic score possible for any tree?
 */
private fun main() {
    fun part1(input: List<String>): Int {
        val grid = getGrid(input)
        var counter = 0

        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, treeSize ->
                when {
                    isLeftEdge(rowIndex) -> counter++
                    isTopEdge(columnIndex) -> counter++
                    isRightEdge(rowIndex, grid.size) -> counter++
                    isBottomEdge(columnIndex, grid.size) -> counter++
                    isVisibleFromLeft(row, columnIndex, treeSize) -> counter++
                    isVisibleFromTop(grid, rowIndex, columnIndex, treeSize) -> counter++
                    isVisibleFromRight(row, columnIndex, treeSize) -> counter++
                    isVisibleFromBottom(grid, rowIndex, columnIndex, treeSize) -> counter++
                }
            }
        }

        return counter
    }

    fun part2(input: List<String>): Int {
        val grid = getGrid(input)
        var max = 0

        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, _ ->
                val score = getTreeScore(grid, row, rowIndex, columnIndex)
                if (score > max) {
                    max = score
                }
            }
        }

        return max
    }

    val testInput = readInput("test_input")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("input")
    println(part1(input))
    println(part2(input))
}

private fun getGrid(input: List<String>): List<List<Int>> {
    val grid = mutableListOf<List<Int>>()

    input.forEach { line ->
        val ints = line.toCharArray().map { char -> char.digitToInt() }
        grid.add(ints)
    }
    return grid
}

private fun isLeftEdge(rowIndex: Int) =
    rowIndex == 0

private fun isTopEdge(columnIndex: Int) =
    columnIndex == 0

private fun isRightEdge(rowIndex: Int, gridSize: Int) =
    rowIndex == gridSize - 1

private fun isBottomEdge(columnIndex: Int, gridSize: Int) =
    columnIndex == gridSize - 1

private fun isVisibleFromLeft(row: List<Int>, columnIndex: Int, treeSize: Int) =
    row.subList(0, columnIndex).all { it < treeSize }

private fun isVisibleFromTop(grid: List<List<Int>>, rowIndex: Int, columnIndex: Int, treeSize: Int) =
    grid.subList(0, rowIndex).map { it[columnIndex] }.all { it < treeSize }

private fun isVisibleFromRight(row: List<Int>, columnIndex: Int, treeSize: Int) =
    row.subList(columnIndex + 1, row.size).all { it < treeSize }

private fun isVisibleFromBottom(grid: List<List<Int>>, rowIndex: Int, columnIndex: Int, treeSize: Int) =
    grid.subList(rowIndex + 1, grid.size).map { it[columnIndex] }.all { it < treeSize }

private fun getLeftScore(row: List<Int>, columnIndex: Int): Int {
    if (columnIndex == 0) {
        return 0
    }

    val currentTreeHeight = row[columnIndex]
    val leftTrees = row.subList(0, columnIndex)
    var lastVisibleIndex = leftTrees.indexOfLast { it >= currentTreeHeight }
    if (lastVisibleIndex == -1) {
        lastVisibleIndex = 0
    }

    return columnIndex - lastVisibleIndex
}

private fun getTopScore(grid: List<List<Int>>, rowIndex: Int, columnIndex: Int): Int {
    if (rowIndex == 0) {
        return 0
    }

    val currentTreeHeight = grid[rowIndex][columnIndex]
    val column = grid.map { it[columnIndex] }
    val topTrees = column.subList(0, rowIndex)
    var lastVisibleIndex = topTrees.indexOfLast { it >= currentTreeHeight }
    if (lastVisibleIndex == -1) {
        lastVisibleIndex = 0
    }

    return rowIndex - lastVisibleIndex
}

private fun getRightScore(row: List<Int>, columnIndex: Int): Int {
    if (columnIndex == row.size - 1) {
        return 0
    }

    val currentTreeHeight = row[columnIndex]
    val rightTrees = row.subList(columnIndex + 1, row.size)
    var lastVisibleIndex = rightTrees.indexOfFirst { it >= currentTreeHeight }
    if (lastVisibleIndex == -1) {
        lastVisibleIndex = row.size - columnIndex - 1
    } else {
        lastVisibleIndex++
    }

    return lastVisibleIndex
}

private fun getBottomScore(grid: List<List<Int>>, rowIndex: Int, columnIndex: Int): Int {
    if (rowIndex == grid.size - 1) {
        return 0
    }

    val currentTreeHeight = grid[rowIndex][columnIndex]
    val column = grid.map { it[columnIndex] }
    val bottomTrees = column.subList(rowIndex + 1, grid.size)

    var lastVisibleIndex = bottomTrees.indexOfFirst { it >= currentTreeHeight }
    if (lastVisibleIndex == -1) {
        lastVisibleIndex = grid.size - rowIndex - 1
    } else {
        lastVisibleIndex++
    }

    return lastVisibleIndex
}


private fun getTreeScore(grid: List<List<Int>>, row: List<Int>, rowIndex: Int, columnIndex: Int): Int =
    getLeftScore(row, columnIndex) *
            getTopScore(grid, rowIndex, columnIndex) *
            getRightScore(row, columnIndex) *
            getBottomScore(grid, rowIndex, columnIndex)
