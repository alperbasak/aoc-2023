package six


//Time:        46     82     84     79
//Distance:   347   1522   1406   1471
fun main() {
    val races = arrayListOf(
        Pair(46, 347), Pair(82, 1522), Pair(84, 1406), Pair(79, 1471)
    )

    println(races.fold(1) { acc, pair -> acc * getNumberOfWays(pair) })

}

fun getNumberOfWays(pair: Pair<Int, Int>): Int {
    val maxTime = pair.first
    return (0..maxTime).count {
        val totalDistance = it * (maxTime - it)
        pair.second < totalDistance
    }
}
