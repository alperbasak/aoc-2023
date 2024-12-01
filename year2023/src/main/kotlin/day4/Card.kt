package day4

import kotlin.math.pow

class Card(private val winningNumbers: List<Int>, private val numbers: List<Int>) {
    fun count(): Int = numbers.count { winningNumbers.contains(it) }

    fun point(): Int {
        val count = count()

        return if (count == 0) 0 else {
            1 + ((0..(count - 2)).sumOf { 2.0.pow(it.toDouble()).toInt() })
        }
    }

    companion object Card {

        private fun extractNumbers(string: String): List<Int> {
            return string.split(" ").filterNot { it == "" }.map { it.toInt() }
        }

        fun from(winningNumbers: String, numbers: String): day4.Card {
            return Card(extractNumbers(winningNumbers), extractNumbers(numbers))
        }
    }
}
