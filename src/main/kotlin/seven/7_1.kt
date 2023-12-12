package seven

fun main() {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/7.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val hands = lines.map {
        val line = it.split(" ")
        line.first() to line.last().toInt()
    }

    val sortedHands = hands.sortedWith(rankComparator)
    val sum = sortedHands.mapIndexed { index, pair -> pair.second * (index + 1) }.sum()

    println(sum)
}

val rankComparator = Comparator { pair1: Pair<String, Int>, pair2: Pair<String, Int> ->
    val typeDiff = type(pair1.first) - type(pair2.first)
    if (typeDiff == 0) labelCompare(pair1.first, pair2.first) else typeDiff
}

val cardLabels = arrayOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

fun labelIndex(c: Char) = cardLabels.indexOf(c)

fun labelCompare(first: String, second: String): Int {
    first.forEachIndexed { index, c ->
        val labelDiff = labelIndex(second[index]) - labelIndex(c)
        if (labelDiff != 0) return labelDiff
    }
    return 0
}

val IllegalArgumentException = IllegalArgumentException("Not Possible")

fun type(hand: String): Int {
    val charCounts = hand.associateWith { c -> hand.count { it == c } }

    return when (charCounts.values.max()) {
        5 -> 7
        4 -> 6
        3 -> when (charCounts.values.size) {
            2 -> 5
            3 -> 4
            else -> throw IllegalArgumentException
        }

        2 -> when (charCounts.values.size) {
            3 -> 3
            4 -> 2
            else -> throw IllegalArgumentException
        }

        1 -> 1
        else -> throw IllegalArgumentException
    }

}

