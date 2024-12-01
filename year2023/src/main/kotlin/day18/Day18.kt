package day18

import day11.Position
import util.lines
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


fun main() {
    val lines = lines("/18.txt")

    println(one(lines))
    println(two(lines))
}

fun one(lines: List<String>): Int {
    val digPlan = createDigPlan(lines)
    val filledDigPlan = digPlan.fill()

    return filledDigPlan.map.sumOf { it.count { it != '.' } }
}

fun createDigPlan(lines: List<String>): DigPlan {
    val digEntryList = lines.map {
        val line = it.split(" ")
        DigEntry(line[0][0], line[1].toInt(), line[2])
    }

    val mapSize = digEntryList.groupBy { it.direction }.mapValues { it.value.sumOf { it.meter } }

    val dotRow = (0..mapSize.getValue('R') * 2).map { '.' }.toTypedArray()
    val digSite = (0..mapSize.getValue('D') * 2).map { dotRow.copyOf() }.toTypedArray()

    val digPlan = digEntryList.fold(
        DigPlan(
            Position(mapSize.getValue('R') - 1, mapSize.getValue('D') - 1), digSite
        )
    ) { acc: DigPlan, digEntry: DigEntry ->
        acc.updateBy(digEntry)
    }

    // cut map into borders
    val minRow = digPlan.map.indexOfFirst { !it.all { it == '.' } }
    val maxRow = digPlan.map.indexOfLast { !it.all { it == '.' } }
    val minCol = digPlan.map.map { it.indexOf('|') }.filter { it >= 0 }.min()
    val maxCol = digPlan.map.map { it.lastIndexOf('|') }.filter { it >= 0 }.max()

    return DigPlan(digPlan.currentPosition,
        digPlan.map.copyOfRange(minRow, maxRow + 1).map { it.copyOfRange(minCol, maxCol + 1) }
            .toTypedArray())
}

data class DigEntry(val direction: Char, val meter: Int, val hex: String)

data class DigPlan(val currentPosition: Position, val map: Array<Array<Char>>) {
    fun updateBy(digEntry: DigEntry): DigPlan {
        // insert line to map it not exists in the currentPosition + direction
        val row = currentPosition.row
        val col = currentPosition.col

        val endPosition = when (digEntry.direction) {
            'R' -> Position(row, col + digEntry.meter)
            'L' -> Position(row, col - digEntry.meter)
            'U' -> Position(row - digEntry.meter, col)
            'D' -> Position(row + digEntry.meter, col)
            else -> error("Illegal direction")
        }

        val prevRoute = arrayListOf(
            Pair('N', map[row - 1][col]),
            Pair('S', map[row + 1][col]),
            Pair('E', map[row][col + 1]),
            Pair('W', map[row][col - 1])
        ).filter { it.second != '.' }

        val symbol = when (digEntry.direction) {
            'U', 'D' -> '|'
            'L', 'R' -> '-'
            else -> error("Illegal direction")
        }

        min(row, endPosition.row).rangeTo(max(row, endPosition.row))
            .forEach { map[it][col] = symbol }
        min(col, endPosition.col).rangeTo(max(col, endPosition.col))
            .forEach { map[row][it] = symbol }

        if (prevRoute.size == 1) {
            map[row][col] = when (Pair(prevRoute.first().first, digEntry.direction)) {
                Pair('N', 'R'), Pair('E', 'U') -> 'L'
                Pair('N', 'L'), Pair('W', 'U') -> 'J'
                Pair('S', 'R'), Pair('E', 'D') -> 'F'
                Pair('S', 'L'), Pair('W', 'D') -> '7'
                else -> error("Illegal direction")
            }
        }

        return DigPlan(endPosition, map)
    }

    fun fill(): DigPlan {
        val sidescanned = map.map { line ->
            var isInside = false
            var previousCorner: Char? = null

            line.map { char ->
                if (char != '.') {
                    if (char == '|') {
                        isInside = isInside.not()
                    } else if (char in arrayOf('L', '7', 'F', 'J')) {
                        if (previousCorner != null) {
                            // check for U shape
                            if (!(previousCorner == 'L' && char == 'J') && !(previousCorner == 'F' && char == '7')) {
                                isInside = isInside.not()
                            }
                            previousCorner = null
                        } else previousCorner = char
                    }
                    char
                } else if (isInside) 'I' else '.'
            }.toTypedArray()
        }.toTypedArray()
        return DigPlan(currentPosition, sidescanned)
    }
}

// :(
fun two(lines: List<String>): Long {
    val hexedLines = swapCode(lines)

    val digPlan = hexedLines.map { shiftDirection(it.first).multiply(it.second) }
    val corners = corners(digPlan)

    val area = shoelaceFormula(corners)
    val perimeter = perimeter(digPlan)

    return picksTheorem(area, perimeter)
}

fun perimeter(digPlan: List<Vector2>): Long = digPlan.sumOf { abs(it.x) + abs(it.y) }


fun corners(digPlan: List<Vector2>): List<Vector2> {
    return digPlan.scan(Vector2(0, 0)) { acc: Vector2, shiftVector: Vector2 ->
        acc.add(shiftVector)
    }
}

fun swapCode(lines: List<String>): List<Pair<Char, Long>> {
    return lines.map {
        val hex = it.split(" ").last().removeSurrounding("(#", ")")
        Pair(hex.last(), hex.substring(0..hex.length - 2).toLong(radix = 16))
    }
}

private fun shoelaceFormula(corners: List<Vector2>): Long = corners.zipWithNext { a, b ->
    a.x * b.y - a.y * b.x
}.sum() / 2

private fun picksTheorem(area: Long, perimeter: Long): Long {
    // A = i + p/2 -1
    // i + p = A + p/2 + 1
    return area + perimeter / 2 + 1
}


data class Vector2(val x: Long, val y: Long) {
    fun add(other: Vector2) = Vector2(x + other.x, y + other.y)
    fun multiply(factor: Long) = Vector2(x * factor, y * factor)
}

private fun shiftDirection(direction: Char) = when (direction) {
    '0', 'R' -> Vector2(1, 0)
    '1', 'D' -> Vector2(0, 1)
    '2', 'L' -> Vector2(-1, 0)
    '3', 'U' -> Vector2(0, -1)
    else -> throw Exception("Unknown direction")
}

