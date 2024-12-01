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
        var fold = lines.filter { it.contains("A =") }.map { it.substring(0..2) }
        var counter = 0

        while (true) {
            fold = directions.fold(fold) { acc: List<String>, s: Char ->
                val value = when (s) {
                    'L' -> acc.map { leftOf(it) }
                    'R' -> acc.map { rightOf(it) }
                    else -> throw IllegalArgumentException()
                }
                counter++
                if (value[3].endsWith('Z')) println("[3] $counter: $value")
                if (value[4].endsWith('Z')) println("[4] $counter: $value")
                if (value[5].endsWith('Z')) println("[5] $counter: $value")

                if (value.all { it.endsWith('Z') }) return counter
                value
            }
        }
    }

    println(takeWhile())

    // 0 - 17873
    // 1 - 19631
    // 2 - 17287
    // 3 - 12599
    // 4 - 21389
    // 5 - 20803

    // LCD is 15746133679061
}





