package org.gamezeug.digitspuzzle.domain

class PuzzleBoard(private val area: PuzzleArea) {
    fun putTile(x: Int, y: Int, newTile: Tile) {
        area.rows[y].tiles[x] = newTile
    }
    override fun toString() = area.toString()
}

class PuzzleBoardFactory {
    fun buildPuzzleBoard(numberOfRows: Int, numberOfColumns: Int): PuzzleBoard {
        val puzzleArea = PuzzleArea(Array(numberOfRows) { _ -> buildPuzzleRow(numberOfColumns) })
        return PuzzleBoard(puzzleArea)
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