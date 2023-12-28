package day12

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Day12KtTest {

    @Test
    fun isApplicable() {
        assert("#.#.###".arranged(arrayListOf(1, 1, 3)))
        assert(".#...#....###.".arranged(arrayListOf(1, 1, 3)))
        assert(".#.###.#.######".arranged(arrayListOf(1, 3, 1, 6)))
        assert("####.#...#...".arranged(arrayListOf(4, 1, 1)))
        assert("#....######..#####.".arranged(arrayListOf(1, 6, 5)))
        assert(".###.##....#".arranged(arrayListOf(3, 2, 1)))
    }

    @Test
    fun permute() {
        assertContentEquals(
            arrayListOf(
                "....###",
                "..#.###",
                ".#..###",
                ".##.###",
                "#...###",
                "#.#.###",
                "##..###",
                "###.###",
            ), "???.###".permute()
        )
    }

    @Test
    fun arrangements() {
        assertEquals(1, "???.###".permute().count { it.arranged(arrayListOf(1, 1, 3)) })
        assertEquals(4, ".??..??...?##.".permute().count { it.arranged(arrayListOf(1, 1, 3)) })
        assertEquals(1, "?#?#?#?#?#?#?#?".permute().count { it.arranged(arrayListOf(1, 3, 1, 6)) })
        assertEquals(1, "????.#...#...".permute().count { it.arranged(arrayListOf(4, 1, 1)) })
        assertEquals(4, "????.######..#####.".permute().count { it.arranged(arrayListOf(1, 6, 5)) })
        assertEquals(10, "?###????????".permute().count { it.arranged(arrayListOf(3, 2, 1)) })
    }


}
