package day6


fun main() {
    val race = Pair(46828479L, 347152214061471)

    println(getNumberOfWays(race))

}

fun getNumberOfWays(pair: Pair<Long, Long>): Int {
    val maxTime = pair.first

    return (0..maxTime).count {
        val totalDistance = it * (maxTime - it)
        pair.second < totalDistance
    }
}
