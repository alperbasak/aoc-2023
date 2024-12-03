package com.alperbasak.day1

import com.alperbasak.util.lines
import kotlin.math.abs

fun main() {
    val lines = lines("/1_1.txt")

    val groupOne = mutableListOf<Int>()
    val groupTwo = mutableListOf<Int>()

    for (line in lines) {
        val values = line.split("   ")
        groupOne.add(values[0].toInt())
        groupTwo.add(values[1].toInt())
    }

    val sortedGroupOne = groupOne.sorted()
    val sortedGroupTwo = groupTwo.sorted()

    val result = sortedGroupOne.mapIndexed { index, value ->
        abs(value - sortedGroupTwo[index])
    }.sum()

    println(result)

}
