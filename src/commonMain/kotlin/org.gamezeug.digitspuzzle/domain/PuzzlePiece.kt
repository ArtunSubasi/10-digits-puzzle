package org.gamezeug.digitspuzzle.domain

import com.soywiz.korio.async.async
import com.soywiz.korio.async.runBlockingNoSuspensions
import com.soywiz.korio.file.std.resourcesVfs
import kotlinx.coroutines.GlobalScope

data class PuzzlePiece(val name: String, val area: PuzzleArea) {

    fun rotateBy(rotation: Rotation): PuzzlePiece {
        return when (rotation) {
            Rotation.NO_ROTATION -> this
            Rotation.ROTATE_90_DEGREES_CLOCKWISE -> rotate90DegreesClockwise()
            Rotation.ROTATE_180_DEGREES -> rotate180Degrees()
            Rotation.ROTATE_270_DEGREES_CLOCKWISE -> rotate270DegreesClockwise()
        }
    }

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

    fun mirrorBy(mirroring: Mirroring): PuzzlePiece {
        return when (mirroring) {
            Mirroring.NO_MIRRORING -> this
            Mirroring.MIRROR_HORIZONTALLY -> mirrorHorizontally()
        }
    }

    fun mirrorHorizontally(): PuzzlePiece {
        val newRows = List(area.numberOfRows) {
            y -> List(area.numberOfColumns) {
            x -> area.getTile(area.numberOfColumns - 1 - x, y).mirrorHorizontally()
        }
        }
        return PuzzlePiece(name, PuzzleArea(newRows.map { PuzzleRow(it) }))
    }

    override fun toString() = area.toString()
}

/**
 * Reads and constructs PuzzlePieces from CSV in format such as:
 * XXXX,XXXX,XXXX
 * X := "F" | "T" | "B" | "L" | "R" | " "
 */
object PuzzlePieceFactory {

    fun build0(): PuzzlePiece = buildDigit('0')
    fun build1(): PuzzlePiece = buildDigit('1')
    fun build2(): PuzzlePiece = buildDigit('2')
    fun build3(): PuzzlePiece = buildDigit('3')
    fun build4(): PuzzlePiece = buildDigit('4')
    fun build5(): PuzzlePiece = buildFromFile('5', "puzzlePieces/2.csv").mirrorHorizontally()
    fun build6(): PuzzlePiece = buildDigit('6')
    fun build7(): PuzzlePiece = buildDigit('7')
    fun build8(): PuzzlePiece = buildDigit('8')
    fun build9(): PuzzlePiece = buildFromFile('9', "puzzlePieces/6.csv").rotate180Degrees()

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
        val puzzlePiece = PuzzlePiece(charToPrint.toString(), PuzzleArea(rows))
        println("Puzzle piece created:\n $puzzlePiece")
        return puzzlePiece
    }

}
