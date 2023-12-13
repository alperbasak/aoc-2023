package day8

fun main() {
    // parse file from resources
    val file = object {}.javaClass.getResourceAsStream("/8.txt")!!.bufferedReader()
    val lines = file.useLines { it.toList() }

    val directions = lines[0].toList()

    fun leftOf(acc: String): String {
        return lines.first { it.contains("$acc =") }.substring(7..9)
    }

    fun rightOf(acc: String): String {
        return lines.first { it.contains("$acc =") }.substring(12..14)
    }

    fun takeWhile(): Int {
        var fold = "AAA"
        var counter = 0

        while (true) {
            fold = directions.fold(fold) { acc: String, s: Char ->
                val value = when (s) {
                    'L' -> leftOf(acc)
                    'R' -> rightOf(acc)
                    else -> throw IllegalArgumentException()
                }
                counter++
                if (value == "ZZZ") return counter
                value
            }
        }
    }

    println(takeWhile())
}





