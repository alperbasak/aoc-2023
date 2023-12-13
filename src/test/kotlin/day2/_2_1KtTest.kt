package day2

import org.junit.jupiter.api.assertThrows
import extractSelections
import selectionToRecords
import kotlin.test.Test

class _2_1KtTest {
    @Test
    fun toSuccessfulRecord() {
        // given a string
        val string = " 2 blue, 10 green"

        // should result in 2 Records with correct counts
        val toRecordList = selectionToRecords(string)
        assert(toRecordList[0].count == 2)
        assert(toRecordList[0].color == Color.blue)

        assert(toRecordList[1].count == 10)
        assert(toRecordList[1].color == Color.green)

    }

    @Test
    fun toErrorRecord() {
        // given a string
        val string = " 20 blue, 10 green"

        // should throw
        assertThrows<IllegalArgumentException> {
            selectionToRecords(string)
        }
    }

    @Test
    fun extractSelections() {
        val string =
            " 2 green, 8 blue, 5 red; 3 blue, 9 red; 9 blue, 2 green; 9 red, 2 green, 5 blue; 5 red, 2 green"

        assert(extractSelections(string).size == 5)
    }
}
