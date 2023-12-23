package day11

import kotlin.math.abs

fun main() {

    val file = object {}.javaClass.getResourceAsStream("/11.txt")!!.bufferedReader()
    val lines = file.readLines()

    val positions: List<Position> = lines.galaxyPositions()

    val galaxyRows = positions.map { it.row }
    val rows = lines.indices.filterNot { galaxyRows.contains(it) }

    val galaxyCols = positions.map { it.col }
    val cols = lines[0].indices.filterNot { galaxyCols.contains(it) }

    val pairedPositions: List<Pair<Position, Position>> = positions.pair()

    println(pairedPositions.sumOf { it.distance(rows, cols, 2) })
    println(pairedPositions.sumOf { it.distance(rows, cols, 1000000) })
}

fun List<Position>.pair(): List<Pair<Position, Position>> {
    return this.flatMapIndexed { index, position ->
        this.mapIndexedNotNull { pairIndex, pairPosition ->
            if (pairIndex > index) {
                Pair(position, pairPosition)
            } else null
        }
    }
}

fun List<String>.galaxyPositions(): List<Position> {
    return this.flatMapIndexed { row, s ->
        s.mapIndexedNotNull { col, c ->
            if (c == '#') Position(row, col) else null
        }
    }
}

fun Pair<Position, Position>.distance(rows: List<Int>, cols: List<Int>, times: Int): Long {

    fun getCount(list: List<Int>, it: Int, that: Int): Int {
        val range = if (that > it) it..that else that..it
        return list.count { it in range }
    }

    val emptyRowCount = getCount(rows, this.first.row, this.second.row)
    val emptyColCount = getCount(cols, this.first.col, this.second.col)

    val deltaRow =
        (abs(this.first.row - this.second.row) + emptyRowCount * times - emptyRowCount).toLong()
    val deltaCol = abs(this.first.col - this.second.col) + emptyColCount * times - emptyColCount

    return deltaRow + deltaCol
}

data class Position(val row: Int, val col: Int)


