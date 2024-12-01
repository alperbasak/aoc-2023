package day9

fun main() {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/9.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val lineDiffs = lines.map { diffs(arrayListOf(it.split(" ").map { it.toInt() })) }

    val nextHistory = lineDiffs.sumOf {
        it.map { it.last() }.reversed().reduce { acc, i -> acc + i }
    }

    println(nextHistory)
}

tailrec fun diffs(numbers: List<List<Int>>): List<List<Int>> {
    val row = numbers.last()
    if (row.all { it == 0 }) return numbers

    // get the diff of last row
    val rowDiffs = (1..<row.size).map { row[it] - row[it - 1] }

    return diffs(numbers + arrayListOf(rowDiffs))
}



