package three


fun main(args: Array<String>) {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/3.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val numbersWithIndexes = lines.mapIndexed { lineIndex, s ->

        val neighbourLines =
            arrayListOf(
                lines.getOrNull(lineIndex - 1),
                lines.getOrNull(lineIndex + 1)
            ).filterNotNull()

        numbersFrom(s).filter { pair ->
            isAdjacentToGear(
                pair.first,
                pair.second,
                s,
                neighbourLines
            )
        }
    }

    val gearsIndexes = lines.mapIndexed { lineIndex, it ->
        it.mapIndexed { index, c ->
            if (c == '*') {
                index
            } else null
        }.filterNotNull()
    }


    val gearRatios = gearsIndexes.mapIndexed { lineIndex, gearList ->
        val mapNotNull = gearList.mapNotNull {

            // get the surrounding numbers
            // top
            val top = getNumbersBetween(numbersWithIndexes[lineIndex - 1], it)

            // same line
            val same = getNumbersBetween(numbersWithIndexes[lineIndex], it)

            // bottom
            val bottom = getNumbersBetween(numbersWithIndexes[lineIndex + 1], it)

            val ints = top + same + bottom
            if (ints.size == 2) {
                ints[0] * ints[1]
            } else null
        }
        mapNotNull.sum()
    }

    val sum = gearRatios.reduce { acc, i ->
        acc + i
    }

    println(sum)
}

fun getNumbersBetween(pairs: List<Pair<Int, Int>>, index: Int): List<Int> {
    return pairs.filter {
        // ending digit, should look for previous numbers
        if (it.second < index) {
            if ((index - it.first.toString().length) <= (it.second)) {
                true
            } else {
                false
            }
        } else if (it.second in index..index + 1) {
            true
        } else {
            false
        }
    }.map { it.first }
}


fun isAdjacentToGear(
    number: Int,
    indexStart: Int,
    line: String,
    neighbourLines: List<String>
): Boolean {
    // find the index of number in line
    val indexRange = IntRange(indexStart, indexStart + number.toString().length)

    // check vertical with line
    val previousChar = line.getOrNull(indexRange.first - 1)
    val nextChar = line.getOrNull(indexRange.last)

    if ((previousChar != null && previousChar == '*') || (nextChar != null && nextChar == '*')) {
        return true
    }

    // or check with neighbour lines vertical or diagonal
    val neighbourStart = if (previousChar != null) indexRange.first - 1 else indexRange.first
    val neighbourEnd = if (nextChar != null) indexRange.last + 1 else indexRange.last

    val neighbourBlock =
        neighbourLines.map {
            it.substring(neighbourStart, neighbourEnd).filter { it == '*' }
        }.filterNot { it.isEmpty() }

    return neighbourBlock.isNotEmpty()
}
