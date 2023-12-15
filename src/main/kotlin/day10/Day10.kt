package day10

fun main() {

    val file = object {}.javaClass.getResourceAsStream("/10.txt")!!.bufferedReader()
    val lines = file.readLines()

    println(one(lines))
}

fun one(lines: List<String>): Int {
    val row = lines.indexOfFirst { it.contains('S') }
    val col = lines[row].indexOfFirst { it == 'S' }

    val positions = nextPosition(Position(row, col, 'S'), null, lines, emptyList())
    return positions.size / 2
}

tailrec fun nextPosition(
    position: Position, prevPosition: Position?, lines: List<String>, positions: List<Position>
): List<Position> {
    val currentSymbol = position.symbol!!

    val adjacentPipes = getAdjacentPipes(
        position, lines
    ).filter { it.value != prevPosition }

    if (prevPosition != null && currentSymbol == 'S') {
        return positions
    }

    val direction = when {
        adjacentPipes['N']?.symbol in arrayOf('|', 'F', '7')
                && currentSymbol in arrayOf('|', 'L', 'J', 'S') -> 'N'

        adjacentPipes['E']?.symbol in arrayOf('-', '7', 'J')
                && currentSymbol in arrayOf('-', 'F', 'L', 'S') -> 'E'

        adjacentPipes['S']?.symbol in arrayOf('|', 'L', 'J')
                && currentSymbol in arrayOf('|', '7', 'F', 'S') -> 'S'

        adjacentPipes['W']?.symbol in arrayOf('-', 'F', 'L')
                && currentSymbol in arrayOf('-', 'J', '7', 'S') -> 'W'

        else -> '-'
    }

    if (direction != '-') {
        return nextPosition(
            adjacentPipes.getValue(direction),
            position,
            lines,
            positions + position
        )
    }

    val start = adjacentPipes.filterValues { it.symbol == 'S' }.values.first()
    return nextPosition(start, position, lines, positions + position)

}


fun getAdjacentPipes(position: Position, lines: List<String>): Map<Char, Position> {
    return mapOf(
        'N' to Position(
            position.row - 1,
            position.col,
            lines.getOrNull(position.row - 1)?.getOrNull(position.col)
        ), 'E' to Position(
            position.row,
            position.col + 1,
            lines.getOrNull(position.row)?.getOrNull(position.col + 1)
        ), 'S' to Position(
            position.row + 1,
            position.col,
            lines.getOrNull(position.row + 1)?.getOrNull(position.col)
        ), 'W' to Position(
            position.row,
            position.col - 1,
            lines.getOrNull(position.row)?.getOrNull(position.col - 1)
        )
    )
}

data class Position(val row: Int, val col: Int, val symbol: Char?)
