package day12

import util.lines

private fun one(lines: List<String>): Int = lines.sumOf {
    val input = it.split(" ")
    probabilities(input[0], input[1].split(",").map { it.toInt() })
}

fun probabilities(string: String, arrangement: List<Int>): Int =
    string.permute().count { it.arranged(arrangement) }


fun String.permute(): List<String> {
    val permuted = arrayListOf(
        this.replaceFirst('?', '.'),
        this.replaceFirst('?', '#')
    )

    if (this.contains('?')) {
        return permuted.flatMap { it.permute() }
    }

    return permuted.distinct()
}

fun String.arranged(arrangement: List<Int>): Boolean {
    val grouped = this.split(".").filter { it.isNotEmpty() }
    return grouped.size == arrangement.size &&
            grouped.mapIndexed { index, s -> s.length == arrangement[index] }.all { it }
}


fun main() {
    val lines = lines("/12.txt")
    println(one(lines))
}

