package four

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CardTest {

    @Test
    fun point() {

        assertEquals(
            64,
            Card.from(
                " 30 51 48 31 36 33 49 83 86 17 ",
                " 17 33 31 70 90 37 86 45 58 21 83 52 59 68 55 32 20 43 48 75 30 42 80 60 71"
            ).point()
        )

    }
}
