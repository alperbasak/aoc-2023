fun main(args: Array<String>) {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("1.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val sumList = lines.map() { line ->
        val digits = line.filter { char ->
            char.isDigit()
        }
        digits[0].plus(digits.last().toString()).toInt()
    }
    println(sumList)
    val sum = sumList.reduce { acc, i ->
        acc + i
    }

    println(sum)
}
