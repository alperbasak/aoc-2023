package day18

import org.junit.jupiter.api.Test
import util.lines
import kotlin.test.assertContentEquals

class Day18KtTest {

    @Test
    fun one() {
        val lines = lines("/18.txt")

        val expected = arrayOf(
            "#######",
            "#.....#",
            "###...#",
            "..#...#",
            "..#...#",
            "###.###",
            "#...#..",
            "##..###",
            ".#....#",
            ".######"
        )

        val digPlan = createDigPlan(lines)

        assertContentEquals(
            expected,
            digPlan.map.map { it.toString() }.toTypedArray()
        )
    }
}
