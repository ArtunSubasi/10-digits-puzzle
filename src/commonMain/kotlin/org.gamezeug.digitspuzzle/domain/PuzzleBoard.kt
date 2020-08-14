package org.gamezeug.digitspuzzle.domain

class PuzzleBoard(private val area: PuzzleArea) {
    override fun toString() = area.toString()
}

object PuzzleBoardFactory {
    fun buildPuzzleBoard(numberOfRows: Int, numberOfColumns: Int): PuzzleBoard {
        val puzzleRows = MutableList(numberOfRows) { _ -> buildMutablePuzzleRow(numberOfColumns) }
        return PuzzleBoard(PuzzleArea(puzzleRows.map { PuzzleRow(it) }))
    }
    fun buildPuzzleBoardWithEdges(numberOfRows: Int, numberOfColumns: Int): PuzzleBoard {
        val puzzleRows = MutableList(numberOfRows) { _ -> buildMutablePuzzleRow(numberOfColumns) }
        puzzleRows[0][0] = buildTopLeftTile()
        puzzleRows[0][numberOfColumns - 1] = buildTopRightTile()
        puzzleRows[numberOfRows - 1][0] = buildBottomLeftTile()
        puzzleRows[numberOfRows - 1][numberOfColumns - 1] = buildBottomRightTile()
        return PuzzleBoard(PuzzleArea(puzzleRows.map { PuzzleRow(it) }))
    }
    private fun buildTopLeftTile() = Tile.Builder().withTopSegment().withLeftSegment().build()
    private fun buildTopRightTile() = Tile.Builder().withTopSegment().withRightSegment().build()
    private fun buildBottomLeftTile() = Tile.Builder().withBottomSegment().withLeftSegment().build()
    private fun buildBottomRightTile() = Tile.Builder().withBottomSegment().withRightSegment().build()

    private fun buildMutablePuzzleRow(numberOfTiles: Int): MutableList<Tile> {
        return MutableList(numberOfTiles) { _ -> emptyTile() }
    }
}