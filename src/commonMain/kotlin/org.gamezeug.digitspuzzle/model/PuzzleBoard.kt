package org.gamezeug.digitspuzzle.model

class PuzzleBoard(private val rows: Array<PuzzleBoardRow>) {
    val numberOfRows = rows.size
    val numberOfColumns = rows[0].size

    fun putTile(x: Int, y: Int, newTile: Tile) {
        rows[y].tiles[x] = newTile
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

class PuzzleBoardRow(val tiles: Array<Tile>) {
    val size = tiles.size
}

class PuzzleBoardFactory {
    fun buildPuzzleBoard(numberOfRows: Int, numberOfColumns: Int): PuzzleBoard {
        return PuzzleBoard(Array(numberOfRows) { _ -> buildPuzzleBoardRow(numberOfColumns)})
    }
    fun buildPuzzleBoardWithEdges(numberOfRows: Int, numberOfColumns: Int): PuzzleBoard {
        val puzzleBoard = buildPuzzleBoard(numberOfRows, numberOfColumns)
        puzzleBoard.putTile(0, 0, buildTopLeftTile())
        puzzleBoard.putTile(numberOfColumns - 1, 0, buildTopRightTile())
        puzzleBoard.putTile(0, numberOfRows - 1, buildBottomLeftTile())
        puzzleBoard.putTile(numberOfColumns - 1, numberOfRows - 1, buildBottomRightTile())
        return puzzleBoard
    }
    private fun buildTopLeftTile() = Tile.Builder().withTopSegment().withLeftSegment().build()
    private fun buildTopRightTile() = Tile.Builder().withTopSegment().withRightSegment().build()
    private fun buildBottomLeftTile() = Tile.Builder().withBottomSegment().withLeftSegment().build()
    private fun buildBottomRightTile() = Tile.Builder().withBottomSegment().withRightSegment().build()
    private fun buildPuzzleBoardRow(numberOfTiles: Int): PuzzleBoardRow {
        return PuzzleBoardRow(Array(numberOfTiles) { _ -> emptyTile() })
    }
}