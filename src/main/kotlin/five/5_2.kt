package five

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import util.lines
import kotlin.math.min

fun main() {
    val lines = lines("/5.txt")
    val breakpoints = lines.mapIndexedNotNull { index, s -> index.takeIf { s == "" } }
    val seeds = lines[0].replace("seeds: ", "").split(" ").map { it.toLong() }
    val seedToSoilMap = lines.subList(breakpoints[0] + 2, breakpoints[1]).sortAndSplit()
    val soilToFertilizerMap = lines.subList(breakpoints[1] + 2, breakpoints[2]).sortAndSplit()
    val fertilizerToWaterMap = lines.subList(breakpoints[2] + 2, breakpoints[3]).sortAndSplit()
    val waterToLightMap = lines.subList(breakpoints[3] + 2, breakpoints[4]).sortAndSplit()
    val lightToTemperatureMap = lines.subList(breakpoints[4] + 2, breakpoints[5]).sortAndSplit()
    val temperatureToHumidityMap = lines.subList(breakpoints[5] + 2, breakpoints[6]).sortAndSplit()
    val humidityToLocationMap = lines.subList(breakpoints[6] + 2, lines.size).sortAndSplit()

    fun getSeedValue(long: Long): Long {
        return long.reverseMapValue(humidityToLocationMap)
            .reverseMapValue(temperatureToHumidityMap)
            .reverseMapValue(lightToTemperatureMap)
            .reverseMapValue(waterToLightMap)
            .reverseMapValue(fertilizerToWaterMap)
            .reverseMapValue(soilToFertilizerMap)
            .reverseMapValue(seedToSoilMap)
    }

    val maxLocationValue = humidityToLocationMap.last()[0] + humidityToLocationMap.last()[2]

    val map = (0..maxLocationValue).fold(Long.MAX_VALUE) { acc, l ->
        val seedValue = getSeedValue(l)
        val l1 = if (seeds.chunked(2).any { seedRange ->
                (seedRange[0]..<seedRange[0] + seedRange[1]).contains(seedValue)
            }) l else Long.MAX_VALUE
        min(acc, l1)
    }


    println(map)
}

fun <A, B> List<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async(Dispatchers.Default) { f(it) } }.map { it.await() }
}


fun Long.reverseMapValue(values: List<List<Long>>): Long {
    val validRange =
        values.mapNotNull { longs -> longs.takeIf { this in it[0]..<it[0] + longs[2] } }

    if (validRange.isEmpty()) {
        return this
    }
    val first = validRange.first()

    return this - first[0] + first[1]
}
