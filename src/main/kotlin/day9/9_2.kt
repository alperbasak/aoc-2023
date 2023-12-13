package day9

fun main() {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/9.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val lineDiffs = lines.map { diffs(arrayListOf(it.split(" ").map { it.toInt() })) }

    val nextHistory = lineDiffs.sumOf {
        it.map { it.first() }.reversed().reduce { acc, i -> i - acc }
    }

    println(nextHistory)
}



