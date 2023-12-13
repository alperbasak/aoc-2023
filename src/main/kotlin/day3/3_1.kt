package day3


fun main(args: Array<String>) {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/3.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val sumList = lines.mapIndexed { index, s ->

        val neighbourLines =
            arrayListOf(lines.getOrNull(index - 1), lines.getOrNull(index + 1)).filterNotNull()

        numbersFrom(s).filterIndexed { index, pair ->
            isAdjacent(
                pair.first,
                pair.second,
                s,
                neighbourLines
            )
        }.sumOf { it.first }
    }

    val sum = sumList.reduce { acc, i ->
        acc + i
    }

    println(sum)
}

fun numbersFrom(string: String): List<Pair<Int, Int>> {
    val numberWithIndexes = arrayListOf<Pair<Int, Int>>()
    var numberString = ""

    string.forEachIndexed { index, c ->
        if (c.isDigit()) {
            numberString += c
            if (string.length - 1 == index) {
                numberWithIndexes.add(Pair(numberString.toInt(), index - numberString.length + 1))
            }
        } else if (numberString.isNotEmpty()) {
            numberWithIndexes.add(Pair(numberString.toInt(), index - numberString.length))
            numberString = ""
        }
    }
    return numberWithIndexes

}

fun isAdjacent(number: Int, indexStart: Int, line: String, neighbourLines: List<String>): Boolean {
    // find the index of number in line
    val indexRange = IntRange(indexStart, indexStart + number.toString().length)

    // check vertical with line
    val previousChar = line.getOrNull(indexRange.first - 1)
    val nextChar = line.getOrNull(indexRange.last)

    if ((previousChar != null && previousChar != '.') || (nextChar != null && nextChar != '.')) {
        return true
    }

    // or check with neighbour lines vertical or diagonal
    val neighbourStart = if (previousChar != null) indexRange.first - 1 else indexRange.first
    val neighbourEnd = if (nextChar != null) indexRange.last + 1 else indexRange.last

    val neighbourBlock =
        neighbourLines.map {
            it.substring(neighbourStart, neighbourEnd).filterNot { it.isDigit() || it == '.' }
        }.filterNot { it.isEmpty() }

    return neighbourBlock.isNotEmpty()
}

