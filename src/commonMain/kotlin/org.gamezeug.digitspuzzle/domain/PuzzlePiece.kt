package org.gamezeug.digitspuzzle.domain

import com.soywiz.korio.async.async
import com.soywiz.korio.async.runBlockingNoSuspensions
import com.soywiz.korio.file.std.resourcesVfs
import kotlinx.coroutines.GlobalScope

class PuzzlePiece(val name: String, val area: PuzzleArea) {

    fun rotate90DegreesClockwise(): PuzzlePiece {
        val newNumberOfColumns = area.numberOfRows
        val newNumberOfRows = area.numberOfColumns
        val newRows = List(newNumberOfRows) {
            y -> List(newNumberOfColumns) {
                x -> area.getTile(y, area.numberOfRows - 1 - x).rotate90DegreesClockwise()
            }
        }
        return PuzzlePiece(name, PuzzleArea(newRows.map { PuzzleRow(it) }))
    }

    fun rotate180Degrees(): PuzzlePiece {
        return rotate90DegreesClockwise().rotate90DegreesClockwise()
    }

    fun rotate270DegreesClockwise(): PuzzlePiece {
        return rotate180Degrees().rotate90DegreesClockwise()
    }

    override fun toString() = area.toString()
}

/**
 * Reads and constructs PuzzlePieces from CSV in format such as:
 * XXXX,XXXX,XXXX
 * X := "F" | "T" | "B" | "L" | "R" | " "
 */
object PuzzlePieceFactory {

    fun build1(): PuzzlePiece = buildDigit('1')
    fun build2(): PuzzlePiece = buildDigit('2')
    private fun buildDigit(digitChar: Char) = buildFromFile(digitChar, "puzzlePieces/$digitChar.csv")

    fun buildFromFile(charToPrint: Char, filePath: String): PuzzlePiece {
        val file = resourcesVfs[filePath]
        val deferred = GlobalScope.async { file.readLines() }
        val lines = runBlockingNoSuspensions {
            deferred.await()
        }
        val rows = mutableListOf<PuzzleRow>()
        for (line in lines) {
            val tiles = line.split(",")
                    .map { TileFactory.createFromTileDescription(it, charToPrint) }
                    .toList()
            rows.add(PuzzleRow(tiles))
        }
        return PuzzlePiece(charToPrint.toString(), PuzzleArea(rows))
    }

}
