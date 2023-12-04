package two

import extractSelections
import selectionToRecords

fun main(args: Array<String>) {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/2.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val sumList = lines.mapNotNull { line ->
        val game = line.split(":")
        try {
            val colorMap = extractSelections(game.last())
                .flatMap { selectionToRecords(it) }
                .groupBy { it.color }

            val power = colorMap.map { it.value.maxBy { it.count }.count }.reduce { acc, i ->
                acc * i
            }

            println(power)
            power
        } catch (e: Exception) {
            null
        }
    }

    val sum = sumList.reduce { acc, i ->
        acc + i
    }

    println(sum)
}
