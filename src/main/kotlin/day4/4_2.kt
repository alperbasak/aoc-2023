package day4

fun main() {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/4.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val matchCounts = lines.map {
        val split = it.split("[:|]".toRegex())
        Card.from(split[1], split[2]).count()
    }

    println(increment(matchCounts).sum())

}

fun increment(matchCounts: List<Int>): List<Int> {
    val cardCounts = matchCounts.map { 0 }.toMutableList()

    matchCounts.forEachIndexed { index, i ->
        cardCounts[index] += 1
        (index + 1..index + i).forEach {
            cardCounts[it] += cardCounts[index]
        }
    }
    return cardCounts
}
