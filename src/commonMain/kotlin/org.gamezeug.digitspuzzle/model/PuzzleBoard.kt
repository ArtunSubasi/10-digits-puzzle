package org.gamezeug.digitspuzzle.model

class PuzzleBoard(private val rows: Array<PuzzleRow>): TiledPuzzleShape(rows) {
    fun putTile(x: Int, y: Int, newTile: Tile) {
        rows[y].tiles[x] = newTile
    }
}

class PuzzleBoardFactory {
    fun buildPuzzleBoard(numberOfRows: Int, numberOfColumns: Int): PuzzleBoard {
        return PuzzleBoard(Array(numberOfRows) { _ -> buildPuzzleRow(numberOfColumns)})
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
    private fun buildPuzzleRow(numberOfTiles: Int): PuzzleRow {
        return PuzzleRow(Array(numberOfTiles) { _ -> emptyTile() })
    }
}