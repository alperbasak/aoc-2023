package five

import org.junit.jupiter.api.Test
import util.lines
import kotlin.math.min
import kotlin.test.assertEquals

class _5_1KtTest {

    @Test
    fun getValue() {
        assertEquals(51, 99L.mapValue(arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48))))
        assertEquals(53, 51L.mapValue(arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48))))
        assertEquals(40, 40L.mapValue(arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48))))
    }

    @Test
    fun getReverseValue() {
        assertEquals(
            99,
            51L.reverseMapValue(arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48)))
        )
        assertEquals(
            51,
            53L.reverseMapValue(arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48)))
        )
        assertEquals(
            40,
            40L.reverseMapValue(arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48)))
        )
    }

    @Test
    fun getMinLocValue() {

        val lines = lines("/5_test.txt")
        val breakpoints = lines.mapIndexedNotNull { index, s -> index.takeIf { s == "" } }
        val seeds = lines[0].replace("seeds: ", "").split(" ").map { it.toLong() }
        val seedToSoilMap = lines.subList(breakpoints[0] + 2, breakpoints[1]).sortAndSplit()
        val soilToFertilizerMap = lines.subList(breakpoints[1] + 2, breakpoints[2]).sortAndSplit()
        val fertilizerToWaterMap = lines.subList(breakpoints[2] + 2, breakpoints[3]).sortAndSplit()
        val waterToLightMap = lines.subList(breakpoints[3] + 2, breakpoints[4]).sortAndSplit()
        val lightToTemperatureMap = lines.subList(breakpoints[4] + 2, breakpoints[5]).sortAndSplit()
        val temperatureToHumidityMap =
            lines.subList(breakpoints[5] + 2, breakpoints[6]).sortAndSplit()
        val humidityToLocationMap = lines.subList(breakpoints[6] + 2, lines.size).sortAndSplit()


        fun getSeedValue(long: Long): Long {
            return long.reverseMapValue(humidityToLocationMap)
                .reverseMapValue(temperatureToHumidityMap)
                .reverseMapValue(lightToTemperatureMap)
                .reverseMapValue(waterToLightMap) //49
                .reverseMapValue(fertilizerToWaterMap)
                .reverseMapValue(soilToFertilizerMap)
                .reverseMapValue(seedToSoilMap)
        }

        val maxLocationValue = humidityToLocationMap.last()[0] + humidityToLocationMap.last()[2]

        val map1 = (0..maxLocationValue).fold(Long.MAX_VALUE) { acc, l ->
            val l1 = if (seeds.chunked(2).any { seedRange ->
                    (seedRange[0]..<seedRange[0] + seedRange[1]).contains(getSeedValue(l))
                }) l else Long.MAX_VALUE
            min(acc, l1)
        }


        assertEquals(46, map1)

    }
}
