package day5

import util.lines

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


    val minLocation = seeds.map {
        it.mapValue(seedToSoilMap).mapValue(soilToFertilizerMap).mapValue(fertilizerToWaterMap)
            .mapValue(waterToLightMap).mapValue(lightToTemperatureMap)
            .mapValue(temperatureToHumidityMap).mapValue(humidityToLocationMap)
    }.min()

    println(minLocation)
}

fun List<String>.sortAndSplit(): List<List<Long>> {
    return this.sorted().map { it.split(" ").map { it.toLong() } }
}

fun Long.mapValue(values: List<List<Long>>): Long {
    val validRange =
        values.mapNotNull { longs -> longs.takeIf { this in it[1]..it[1] + longs[2] } }

    if (validRange.isEmpty()) {
        return this
    }
    val first = validRange.first()

    return this - first[1] + first[0]
}
