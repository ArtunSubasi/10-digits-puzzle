package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.Move
import org.gamezeug.digitspuzzle.domain.PuzzleState

class PiecePlacementUseCase {

    fun isValidPiecePlacement(move: Move, state: PuzzleState): Boolean {
        if (!state.area.hasRoomFor(move.piece.area, move.coordinate)) return false
        for ((rowIndex, row) in move.piece.area.rows.withIndex()) {
            for ((colIndex, newTile) in row.tiles.withIndex()) {
                val tileInThePuzzle = state.area.getTile(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                if (!newTile.isDisjoint(tileInThePuzzle)) return false
            }
        }
        return true
    }

}