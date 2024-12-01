package day12

import util.lines

private fun one(lines: List<String>): Long = lines.sumOf {
    val input = it.split(" ")
    probabilities(input[0], input[1].split(",").map { it.toInt() })
}

private fun two(lines: List<String>): Long = lines.sumOf {
    val input = it.split(" ")
    val probabilities =
        countArrangements(input[0].unfold('?'), input[1].unfold(',').split(",").map { it.toInt() })
    probabilities
}

fun String.permute(): List<String> {
    val permuted = arrayListOf(
        this.replaceFirst('?', '.'), this.replaceFirst('?', '#')
    )

    if (this.contains('?')) {
        return permuted.flatMap { it.permute() }
    }

    return permuted.distinct()
}

fun String.arranged(arrangement: List<Int>): Boolean {
    val grouped = this.split(".").filter { it.isNotEmpty() }
    return grouped.size == arrangement.size && grouped.mapIndexed { index, s -> s.length == arrangement[index] }
        .all { it }
}

fun String.unfold(delimiter: Char): String {
    return (0..3).map { it.toString() }.fold(this) { acc, _ -> "$acc$delimiter$this" }
}

fun probabilities(string: String, arrangement: List<Int>): Long =
    string.permute(arrangement).count().toLong()

fun String.permute(arrangement: List<Int>): List<String> {
    // early kill disarranged ones
    val grouped = this.split('.').filter { it.isNotEmpty() }
    run check@{
        grouped.forEachIndexed { index: Int, s: String ->
            if (s.contains('?')) return@check
            else if (s.length != arrangement.getOrElse(index) { -1 }) {
                return emptyList()
            }
        }
    }

    if (!this.contains('?')) {
        return if (this.arranged(arrangement)) {
            arrayListOf(this)
        } else emptyList()
    }

    val permuted = arrayListOf(
        this.replaceFirst('?', '.'), this.replaceFirst('?', '#')
    )

    return permuted.flatMap { it.permute(arrangement) }
}

// shamelessly stolen
private fun countArrangements(conditions: String, groups: List<Int>): Long {
    val cache = mutableMapOf<Pair<Int, Int>, Long>()
    fun recurse(charPos: Int, groupPos: Int): Long {
        cache[charPos to groupPos]?.let { return it }

        var count = 0L
        if (groupPos == groups.size) {
            count = if ((charPos..conditions.lastIndex).none { conditions[it] == '#' }) 1L else 0L

        } else {
            val minRequiredLen =
                (groupPos..groups.lastIndex).sumOf { groups[it] } + groups.lastIndex - groupPos
            for (i in charPos..conditions.length - minRequiredLen) {
                val groupEnd = i + groups[groupPos]
                val isGroupFit = (i..<groupEnd).all { conditions[it] != '.' }
                val isDelimited = conditions.length == groupEnd || conditions[groupEnd] != '#'

                if (isGroupFit && isDelimited) {
                    count += recurse(groupEnd + 1, groupPos + 1)
                }
                if (conditions[i] == '#') {
                    break
                }
            }
        }
        return count.also { cache[charPos to groupPos] = it }
    }

    return recurse(0, 0)
}

fun main() {
    val lines = lines("/12.txt")
    println(one(lines))
    println(two(lines))
}

