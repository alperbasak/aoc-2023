package day13

import org.junit.jupiter.api.Test
import util.lines
import kotlin.test.assertEquals

class Day13KtTest {

    @Test
    fun main() {
        val lines = lines("/13.txt")
        val separators =
            lines.mapIndexedNotNull { index: Int, s: String -> if (s.isEmpty()) index else null }
        val patterns = separators.zipWithNext { a, b -> lines.subList(a + 1, b) }

        assertEquals(405, one(patterns))
    }
}
