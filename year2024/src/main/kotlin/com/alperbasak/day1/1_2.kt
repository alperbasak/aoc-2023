package com.alperbasak.day1

import com.alperbasak.util.lines

fun main() {
    val lines = lines("/1_1.txt")
    val groupOne = mutableListOf<Int>()
    val groupTwo = mutableListOf<Int>()

    for (line in lines) {
        val values = line.split("   ")
        groupOne.add(values[0].toInt())
        groupTwo.add(values[1].toInt())
    }

    val score = groupOne.sumOf { first -> first * groupTwo.count { it == first } }
    println(score)

}
