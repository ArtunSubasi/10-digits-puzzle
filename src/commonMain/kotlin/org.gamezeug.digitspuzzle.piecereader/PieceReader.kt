package org.gamezeug.digitspuzzle.piecereader

import com.soywiz.korio.async.async
import com.soywiz.korio.async.runBlockingNoSuspensions
import com.soywiz.korio.file.std.resourcesVfs
import kotlinx.coroutines.GlobalScope
import org.gamezeug.digitspuzzle.model.*

/**
 * Reads and constructs PuzzlePieces from CSV in format such as:
 * XXXX,XXXX,XXXX
 * X := "F" | "T" | "B" | "L" | "R" | " "
 */
class PieceReader {

    fun readFromFile(charToPrint: Char, filePath: String): PuzzlePiece {
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

    // TODO pass charToPrint as well
    private fun mapToTile(tileDescription: String, charToPrint: Char): Tile {
        if (tileDescription.contains("F")) return fullTile()
        var builder = Tile.Builder()
        if (tileDescription.contains("L")) builder = builder.withLeftSegment()
        if (tileDescription.contains("T")) builder = builder.withTopSegment()
        if (tileDescription.contains("R")) builder = builder.withRightSegment()
        if (tileDescription.contains("B")) builder = builder.withBottomSegment()
        return builder.build()
    }

}