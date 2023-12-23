package day11

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11KtTest {

    @Test
    fun distance() {
        val lines =
            object {}.javaClass.getResourceAsStream("/11.txt")!!.bufferedReader().readLines()

        val positions: List<Position> = lines.galaxyPositions()

        val galaxyRows = positions.map { it.row }
        val rows = lines.indices.filterNot { galaxyRows.contains(it) }

        val galaxyCols = positions.map { it.col }
        val cols = lines[0].indices.filterNot { galaxyCols.contains(it) }

        val pairedPositions: List<Pair<Position, Position>> = positions.pair()

        assertEquals(374, pairedPositions.sumOf { it.distance(rows, cols, 2) })
        assertEquals(1030, pairedPositions.sumOf { it.distance(rows, cols, 10) })
        assertEquals(8410, pairedPositions.sumOf { it.distance(rows, cols, 100) })
    }

}
