package org.gamezeug.digitspuzzle.model

class PuzzleBoard(private val rows: Array<PuzzleBoardRow>) {
    val numberOfRows = rows.size
    val numberOfColumns = rows[0].size

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

class PuzzleBoardRow(val tiles: Array<Tile>) {
    val size = tiles.size
}

class PuzzleBoardFactory {
    fun buildPuzzleBoard(numberOfRows: Int, numberOfColumns: Int): PuzzleBoard {
        return PuzzleBoard(Array(numberOfRows) { _ -> buildPuzzleBoardRow(numberOfColumns)})
    }
    private fun buildPuzzleBoardRow(numberOfTiles: Int): PuzzleBoardRow {
        return PuzzleBoardRow(Array(numberOfTiles) { _ -> emptyTile() })
    }
}