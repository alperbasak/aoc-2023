package day1

import util.lines

fun main(args: Array<String>) {
    // parse file from resources
    val lines = lines("/1.txt")

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
