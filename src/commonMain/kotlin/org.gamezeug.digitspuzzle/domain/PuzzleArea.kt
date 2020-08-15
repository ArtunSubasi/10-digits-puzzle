package org.gamezeug.digitspuzzle.domain

/**
 * Represents a tiled area consisting of rows and columns.
 */
class PuzzleArea(val rows: List<PuzzleRow>) {
    val numberOfRows = rows.size
    val numberOfColumns = rows[0].size

    fun replaceTiles(vararg replacements: TileReplacement): PuzzleArea {
        val mutableArea = MutableList(numberOfRows) { y -> MutableList(numberOfColumns) { x -> rows[y].tiles[x] } }
        replacements.forEach { mutableArea[it.coordinate.y][it.coordinate.y] = it.newTile }
        return PuzzleArea(mutableArea.map { PuzzleRow(it) })
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for ((index, row) in rows.withIndex()) {
            for (tile in row.tiles) {
                stringBuilder.append(tile.toStringFirstLine())
            }
            stringBuilder.append("\n")
            for (tile in row.tiles) {
                stringBuilder.append(tile.toStringSecondLine())
            }
            stringBuilder.append("\n")
            for (tile in row.tiles) {
                stringBuilder.append(tile.toStringThirdLine())
            }
            if (index != numberOfRows - 1) {
                stringBuilder.append("\n")
            }
        }
        return stringBuilder.toString()
    }
}

// TODO check if there is a better way to define factories, such as by using companion objects
object PuzzleAreaFactory {
    fun buildPuzzleArea(numberOfRows: Int, numberOfColumns: Int): PuzzleArea {
        val puzzleRows = MutableList(numberOfRows) { _ -> PuzzleRowFactory.buildPuzzleRow(numberOfColumns) }
        return PuzzleArea(puzzleRows)
    }
}

data class PuzzleAreaCoordinate(val x: Int, val y: Int)

data class TileReplacement(val coordinate: PuzzleAreaCoordinate, val newTile: Tile)

class PuzzleRow(val tiles: List<Tile>) {
    val size = tiles.size
}

object PuzzleRowFactory {
    fun buildPuzzleRow(numberOfTiles: Int): PuzzleRow = PuzzleRow(List(numberOfTiles) { _ -> emptyTile() })
}
