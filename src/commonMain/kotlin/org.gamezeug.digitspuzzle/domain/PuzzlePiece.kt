package org.gamezeug.digitspuzzle.domain

import com.soywiz.korio.async.async
import com.soywiz.korio.async.runBlockingNoSuspensions
import com.soywiz.korio.file.std.resourcesVfs
import kotlinx.coroutines.GlobalScope

class PuzzlePiece(val name: String, private val rows: Array<PuzzleRow>): TiledPuzzleShape(rows)

/**
 * Reads and constructs PuzzlePieces from CSV in format such as:
 * XXXX,XXXX,XXXX
 * X := "F" | "T" | "B" | "L" | "R" | " "
 */
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

    fun buildFromFile(charToPrint: Char, filePath: String): PuzzlePiece {
        val file = resourcesVfs[filePath]
        val deferred = GlobalScope.async { file.readLines() }
        val lines = runBlockingNoSuspensions {
            deferred.await()
        }
        val rows = mutableListOf<PuzzleRow>()
        for (line in lines) {
            val tiles = line.split(",").map { mapToTile(it, charToPrint) }.toTypedArray()
            rows.add(PuzzleRow(tiles))
        }
        return PuzzlePiece(charToPrint.toString(), rows.toTypedArray())
    }

    private fun mapToTile(tileDescription: String, charToPrint: Char): Tile {
        if (tileDescription.contains("F")) return fullTile(charToPrint)
        var builder = Tile.Builder()
        builder = builder.withCharToPrint(charToPrint)
        if (tileDescription.contains("L")) builder = builder.withLeftSegment()
        if (tileDescription.contains("T")) builder = builder.withTopSegment()
        if (tileDescription.contains("R")) builder = builder.withRightSegment()
        if (tileDescription.contains("B")) builder = builder.withBottomSegment()
        return builder.build()
    }

}
