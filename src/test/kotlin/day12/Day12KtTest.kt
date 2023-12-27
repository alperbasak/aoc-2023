package day12

import org.junit.jupiter.api.Test
import util.lines
import kotlin.test.assertContentEquals

class Day12KtTest {

    @Test
    fun one() {
        val lines = lines("/12.txt")

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
