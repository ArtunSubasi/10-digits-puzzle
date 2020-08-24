package org.gamezeug.digitspuzzle.domain

/**
 * An immutable tiled area consisting of rows and columns.
 */
data class PuzzleArea(val rows: List<PuzzleRow>) {
    val numberOfRows = rows.size
    val numberOfColumns = rows[0].size

    fun replaceTiles(vararg replacements: TileReplacement): PuzzleArea {
        val mutableArea = MutableList(numberOfRows) { y -> MutableList(numberOfColumns) { x -> rows[y].tiles[x] } }
        replacements.forEach { mutableArea[it.coordinate.y][it.coordinate.x] = it.newTile }
        return PuzzleArea(mutableArea.map { PuzzleRow(it) })
    }

    fun getTile(x: Int, y: Int): Tile {
        return rows[y].tiles[x]
    }

    fun hasRoomFor(newArea: PuzzleArea, newAreaCoordinate: PuzzleAreaCoordinate): Boolean {
        val wideEnough = this.numberOfColumns >= (newArea.numberOfColumns + newAreaCoordinate.x)
        val tallEnough = this.numberOfRows >= (newArea.numberOfRows + newAreaCoordinate.y)
        return wideEnough && tallEnough
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

// TODO check if there is a better way to define factories, such as by using companion objects, or plain constuctors
object PuzzleAreaFactory {
    fun buildPuzzleArea(numberOfRows: Int, numberOfColumns: Int): PuzzleArea {
        val puzzleRows = MutableList(numberOfRows) { _ -> PuzzleRowFactory.buildPuzzleRow(numberOfColumns) }
        return PuzzleArea(puzzleRows)
    }
    fun buildPuzzleAreaWithEdges(numberOfRows: Int, numberOfColumns: Int): PuzzleArea {
        val emptyArea = buildPuzzleArea(numberOfRows, numberOfColumns)
        return emptyArea.replaceTiles(
                TileReplacement(PuzzleAreaCoordinate(0, 0), buildTopLeftTile()),
                TileReplacement(PuzzleAreaCoordinate(numberOfColumns - 1, 0), buildTopRightTile()),
                TileReplacement(PuzzleAreaCoordinate(0, numberOfRows - 1), buildBottomLeftTile()),
                TileReplacement(PuzzleAreaCoordinate(numberOfColumns - 1, numberOfRows - 1), buildBottomRightTile())
        )
    }
    private fun buildTopLeftTile() = Tile(topSegment = 'X', leftSegment = 'X')
    private fun buildTopRightTile() = Tile(topSegment = 'X', rightSegment = 'X')
    private fun buildBottomLeftTile() = Tile(bottomSegment = 'X', leftSegment = 'X')
    private fun buildBottomRightTile() = Tile(bottomSegment = 'X', rightSegment = 'X')
}

data class PuzzleAreaCoordinate(val x: Int, val y: Int)

data class TileReplacement(val coordinate: PuzzleAreaCoordinate, val newTile: Tile)

data class PuzzleRow(val tiles: List<Tile>) {
    val size = tiles.size
}

object PuzzleRowFactory {
    fun buildPuzzleRow(numberOfTiles: Int): PuzzleRow = PuzzleRow(List(numberOfTiles) { _ -> emptyTile() })
}
