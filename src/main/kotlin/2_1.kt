fun main(args: Array<String>) {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("2.txt")!!.bufferedReader()
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


enum class Color {
    red, blue, green
}

// 12 red cubes, 13 green cubes, and 14 blue cubes
class Record(val count: Int, val color: Color) {
    init {
        require(
            (color == Color.red && count <= 12) || (color == Color.green && count <= 13) || (color == Color.blue && count <= 14)
        )
    }
}


//" 2 green, 8 blue, 5 red; 3 blue, 9 red; ..."
fun extractSelections(selections: String): List<String> {
    return selections.split(";")
}


//" x red, y green, ..."
fun selectionToRecords(recordString: String): List<Record> {
    return recordString.split(",").map {
        val strings = it.split(" ").drop(1)
        Record(strings[0].toInt(), Color.valueOf(strings[1]))
    }
}
