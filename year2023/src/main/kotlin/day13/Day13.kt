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

    val equalRows = zipWithNext().mapIndexedNotNull { index, pair ->
        if (pair.first == pair.second) {
            Pair(index, index + 1)
        } else null
    }

    return equalRows.filter { isMirror(it) }.sumOf { it.first + 1 }
}

fun offByOne(a: String, b: String): Boolean {
    return a.filterIndexed { index, c -> b[index] != c }.count() == 1
}

fun fixSmudge(pattern: List<String>): List<String> {
    // find possible consecutive offByOne pairs
    val nearOffByOnes = pattern.zipWithNext().mapIndexedNotNull { index, pair ->
        if (offByOne(pair.first, pair.second))
            Pair(index, index + 1)
        else null
    }

    // find possible non-consecutive offByOne pairs
    val farOffByOnes = pattern.mapIndexedNotNull { index, row: String ->
        val filter = pattern.mapIndexedNotNull { filterIndex, it ->
            if (filterIndex > index && offByOne(it, row) && (abs(index - filterIndex) > 1)) {
                filterIndex
            } else null
        }
        if (filter.isNotEmpty()) {
            if (filter.size > 1) error("Should Not happen")
            Pair(index, filter[0])
        } else null
    }.filter { pair ->
        (pair.second - pair.first).mod(2) == 1 &&
                (1..(pair.second - pair.first) / 2).all { pattern[pair.first + it] == pattern[pair.second - it] }
    }

    val offByOnes = nearOffByOnes.plus(farOffByOnes)

    if (offByOnes.size != 1) error("NOOO")

    val list = pattern.toMutableList()
    list[offByOnes[0].second] = pattern[offByOnes[0].first]

    return list
}

fun main() {
    val lines = lines("/13.txt")
    val separators =
        lines.mapIndexedNotNull { index: Int, s: String -> if (s.isEmpty()) index else null }
    val patterns = separators.zipWithNext { a, b -> lines.subList(a + 1, b) }

    println(one(patterns))
}
