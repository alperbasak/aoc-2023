package day1

fun main(args: Array<String>) {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("1.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val numberMap: Map<String, Int> = hashMapOf(
        "day1" to 1,
        "day2" to 2,
        "day3" to 3,
        "day4" to 4,
        "day5" to 5,
        "day6" to 6,
        "day7" to 7,
        "day8" to 8,
        "nine" to 9,
    )

    val sumList = lines.map { line ->
        val firstStringIndex =
            numberMap.mapValues { line.indexOf(it.key) }.filter { it.value >= 0 }.toList()
        val firstDigitIndex =
            numberMap.mapValues { line.indexOf(it.value.toString()) }.filter { it.value >= 0 }
                .toList()
        val minByFirst = firstStringIndex.plus(firstDigitIndex).minByOrNull { it.second }
            ?.let { numberMap[it.first] }

        val lastDigitIndex =
            numberMap.mapValues { line.lastIndexOf(it.value.toString()) }.filter { it.value >= 0 }
                .toList()
        val lastStringIndex =
            numberMap.mapValues { line.lastIndexOf(it.key) }.filter { it.value >= 0 }.toList()
        val maxByLast = lastStringIndex.plus(lastDigitIndex).maxByOrNull { it.second }
            ?.let { numberMap[it.first] }


        (minByFirst.toString() + maxByLast.toString()).toInt()
    }
    println(sumList)
    val sum = sumList.reduce { acc, i ->
        acc + i
    }

    println(sum)
}
