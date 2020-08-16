package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*

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

    fun placePiece(move: Move, state: PuzzleState) {
        if (!isValidPiecePlacement(move, state)) {
            TODO("exception handling, use isValidPiecePlacement? move it to another class for testability? do it somewhere else?")
        }
        for ((rowIndex, row) in move.piece.area.rows.withIndex()) {
            for ((colIndex, newTile) in row.tiles.withIndex()) {
                val tileInThePuzzle = state.area.getTile(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                val mergedTile = tileInThePuzzle.merge(newTile)
                val coordinate = PuzzleAreaCoordinate(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                state.area = state.area.replaceTiles(TileReplacement(coordinate, mergedTile))
            }
        }
    }

}