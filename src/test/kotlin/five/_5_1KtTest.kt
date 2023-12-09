package five

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class _5_1KtTest {

    @Test
    fun getValue() {
        assertEquals(51, mapValue(99, arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48))))
        assertEquals(53, mapValue(51, arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48))))
        assertEquals(40, mapValue(40, arrayListOf(arrayListOf(50, 98, 2), arrayListOf(52, 50, 48))))
    }
}
