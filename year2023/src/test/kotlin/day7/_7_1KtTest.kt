package day7

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class _7_1KtTest {

    @Test
    fun type() {
        val hands = arrayListOf("A833A", "Q33J3", "55KK5", "K4457")
        assertContentEquals(arrayListOf(3, 4, 5, 2), hands.map { day7.type(it) })
    }
}
