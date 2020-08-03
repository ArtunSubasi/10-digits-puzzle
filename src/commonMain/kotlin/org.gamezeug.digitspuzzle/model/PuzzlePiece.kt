package org.gamezeug.digitspuzzle.model

class PuzzlePiece(val name: String, private val rows: Array<PuzzleRow>): TiledPuzzleShape(rows)

class PuzzlePieceFactory {
    // TODO modify whose function implementations so that they use the PieceReader
    fun build1(): PuzzlePiece {
        val row1 = PuzzleRow(arrayOf(Tile.Builder().withBottomSegment().build()))
        val row2 = PuzzleRow(arrayOf(fullTile()))
        val row3 = PuzzleRow(arrayOf(Tile.Builder().withTopSegment().withBottomSegment().withLeftSegment().build()))
        val row4 = PuzzleRow(arrayOf(fullTile()))
        val row5 = PuzzleRow(arrayOf(Tile.Builder().withTopSegment().build()))
        return PuzzlePiece("1", arrayOf(row1, row2, row3, row4, row5))
    }
    fun build2(): PuzzlePiece {
        val char = '2'
        val row1 = PuzzleRow(arrayOf(
                Tile.Builder().withRightSegment().withCharToPrint(char).build(),
                fullTile(char),
                Tile.Builder().withLeftSegment().withBottomSegment().withCharToPrint(char).build()
        ))
        val row2 = PuzzleRow(arrayOf(emptyTile(char), emptyTile(char), fullTile(char)))
        val row3 = PuzzleRow(arrayOf(
                Tile.Builder().withBottomSegment().withRightSegment().withCharToPrint(char).build(),
                fullTile(char),
                Tile.Builder().withLeftSegment().withTopSegment().withCharToPrint(char).build()
        ))
        val row4 = PuzzleRow(arrayOf(fullTile(char), emptyTile(char), emptyTile(char)))
        val row5 = PuzzleRow(arrayOf(
                Tile.Builder().withTopSegment().withRightSegment().withCharToPrint(char).build(),
                fullTile(char),
                Tile.Builder().withLeftSegment().withCharToPrint(char).build()
        ))
        return PuzzlePiece(char.toString(), arrayOf(row1, row2, row3, row4, row5))
    }
}
