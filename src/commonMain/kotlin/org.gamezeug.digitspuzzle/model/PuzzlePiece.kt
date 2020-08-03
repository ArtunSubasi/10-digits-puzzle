package org.gamezeug.digitspuzzle.model

class PuzzlePiece(val name: String, private val rows: Array<PuzzleRow>): TiledPuzzleShape(rows)

class PuzzlePieceFactory {
    fun build1(): PuzzlePiece {
        val row1 = PuzzleRow(arrayOf(Tile.Builder().withBottomSegment().build()))
        val row2 = PuzzleRow(arrayOf(fullTile()))
        val row3 = PuzzleRow(arrayOf(Tile.Builder().withTopSegment().withBottomSegment().withLeftSegment().build()))
        val row4 = PuzzleRow(arrayOf(fullTile()))
        val row5 = PuzzleRow(arrayOf(Tile.Builder().withTopSegment().build()))
        return PuzzlePiece("1", arrayOf(row1, row2, row3, row4, row5))
    }
    fun build2(): PuzzlePiece {
        val row1 = PuzzleRow(arrayOf(
                Tile.Builder().withRightSegment().build(),
                fullTile(),
                Tile.Builder().withLeftSegment().withBottomSegment().build()
        ))
        val row2 = PuzzleRow(arrayOf(emptyTile(), emptyTile(), fullTile()))
        val row3 = PuzzleRow(arrayOf(
                Tile.Builder().withBottomSegment().withRightSegment().build(),
                fullTile(),
                Tile.Builder().withLeftSegment().withTopSegment().build()
        ))
        val row4 = PuzzleRow(arrayOf(fullTile(), emptyTile(), emptyTile()))
        val row5 = PuzzleRow(arrayOf(
                Tile.Builder().withTopSegment().withRightSegment().build(),
                fullTile(),
                Tile.Builder().withLeftSegment().build()
        ))
        return PuzzlePiece("2", arrayOf(row1, row2, row3, row4, row5))
    }
}
