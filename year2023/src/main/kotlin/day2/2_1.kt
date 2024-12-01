package day2

import extractSelections
import selectionToRecords

fun main(args: Array<String>) {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/2.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val sumList = lines.mapNotNull { line ->
        val game = line.split(":")
        val gameId = game.first().removePrefix("Game ").toInt()
        try {
            extractSelections(game.last()).map { selectionToRecords(it) }
            gameId
        } catch (e: Exception) {
            null
        }
    }

    val sum = sumList.reduce { acc, i ->
        acc + i
    }

    println(sum)
}
