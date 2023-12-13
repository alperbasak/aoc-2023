package day3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class _3_1KtTest {

    @Test
    fun numbersFrom() {
        val string = "617*......123"
        assertEquals(arrayListOf(Pair(617, 0), Pair(123, 10)), numbersFrom(string))
    }

    @Test
    fun shouldReturnNumbersWithIndex() {
        val line =
            ".........842......................442*766.......284.........842...........856...*......................647......422.94..............@......."
        assertEquals(
            arrayListOf(
                Pair(842, 9),
                Pair(442, 34),
                Pair(766, 38),
                Pair(284, 48),
                Pair(842, 60),
                Pair(856, 74),
                Pair(647, 103),
                Pair(422, 112),
                Pair(94, 116),
            ),
            numbersFrom(line)
        )
    }

    @Test
    fun shouldReturnNumbersWithIndex2() {
        val line =
            "...........465.....#.............554.....#.......#..#.......................401.......714...../.192.999..................466...28.161....351"
        assert(numbersFrom(line).contains(Pair(351, 137)))
    }

    @Test
    fun isAdjacentHorizontal() {
        val number = 617
        val line = "617*......"
        val neighbourLine = arrayListOf("......#...", ".....+.58.")
        assertTrue(isAdjacent(number, line.indexOf(number.toString()), line, neighbourLine))
    }

    @Test
    fun isAdjacentVertical() {
        val number = 633
        val line = "..35..633."
        val neighbourLine = arrayListOf("...*......", "......#...")
        assertTrue(isAdjacent(number, line.indexOf(number.toString()), line, neighbourLine))
    }

    @Test
    fun isAdjacentDiagonal() {
        val number = 592
        val line = "..592....."
        val neighbourLine = arrayListOf(".....+.58.", "......755.")
        assertTrue(isAdjacent(number, line.indexOf(number.toString()), line, neighbourLine))
    }

    @Test
    fun isAdjacentInTheBeginning() {
        val number = 592
        val line = "592......."
        val neighbourLine = arrayListOf("...+...58.", "......755.")
        assertTrue(isAdjacent(number, line.indexOf(number.toString()), line, neighbourLine))
    }

    @Test
    fun isAdjacentInTheEnd() {
        val number = 592
        val line = ".......592"
        val neighbourLine = arrayListOf("......58.+", "......755.")
        assertTrue(isAdjacent(number, line.indexOf(number.toString()), line, neighbourLine))
    }

    @Test
    fun isNotAdjacent() {
        val number = 58
        val line = ".....+.58."
        val neighbourLine = arrayListOf("617*......", "..592.....")
        assertFalse(isAdjacent(number, line.indexOf(number.toString()), line, neighbourLine))
    }

    @Test
    fun duplicateNumbersSameLine() {
        val number = 842
        val falseIndex = 9
        val trueIndex = 60
        val line =
            ".........842......................442*766.......284.........842...........856...*......................647......422.94..............@......."
        val neighbourLine = arrayListOf(
            ".......................407@...873...............+..................*542....../.41....$./758....176.................*........................",
            "....$........799............................&.......433.318*.....576..........111....................................................788...."
        )
        assertFalse(isAdjacent(number, falseIndex, line, neighbourLine))
        assertTrue(isAdjacent(number, trueIndex, line, neighbourLine))
    }


}
