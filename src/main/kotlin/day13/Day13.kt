package day13

import util.lines
import kotlin.math.abs
import kotlin.math.min

fun one(patterns: List<List<String>>): Int =
    patterns.sumOf { 100 * it.reflection() + transpose(it).reflection() }

fun transpose(matrix: List<String>): List<String> {
    val transpose = Array(matrix[0].length) { CharArray(matrix.size) }
    for (i in matrix.indices) {
        for (j in matrix[0].indices) {
            transpose[j][i] = matrix[i][j]
        }
    }
    return transpose.map { it.concatToString() }
}

private fun List<String>.reflection(): Int {
    fun isMirror(pair: Pair<Int, Int>): Boolean {
        val range = min(pair.first, this.size - pair.second - 1)
        return (1..range).all {
            this[pair.first - it] == this[pair.second + it]
        }
    }

    val equalRows = this.mapIndexedNotNull { index, row: String ->
        val filter = this.mapIndexedNotNull { filterIndex, it ->
            if (filterIndex > index && it == row && (abs(index - filterIndex) == 1)) {
                filterIndex
            } else null
        }
        if (filter.isNotEmpty()) {
            if (filter.size > 1) error("Should Not happen")
            Pair(index, filter[0])
        } else null
    }

    return equalRows.filter { isMirror(it) }.sumOf { it.first+1 }
}

fun main() {
    val lines = lines("/13.txt")
    val separators =
        lines.mapIndexedNotNull { index: Int, s: String -> if (s.isEmpty()) index else null }
    val patterns = separators.zipWithNext { a, b -> lines.subList(a + 1, b) }

    println(one(patterns))
}
