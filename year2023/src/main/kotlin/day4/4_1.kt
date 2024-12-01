package day4

fun main() {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/4.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val sumList = lines.map { s ->

        val split = s.split("[:|]".toRegex())
        val card = Card.from(split[1], split[2])

        card.point()
    }

    val sum = sumList.reduce { acc, i ->
        acc + i
    }

    println(sum)
}
