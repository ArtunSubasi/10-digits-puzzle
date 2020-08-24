package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*

class PiecePlacementUseCase {

    fun isValidPiecePlacement(move: Move, state: PuzzleState): Boolean {
        val effectiveArea = move.getEffectiveArea()
        if (!state.area.hasRoomFor(effectiveArea, move.coordinate)) return false
        for ((rowIndex, row) in effectiveArea.rows.withIndex()) {
            for ((colIndex, newTile) in row.tiles.withIndex()) {
                val tileInThePuzzle = state.area.getTile(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                if (!newTile.isDisjoint(tileInThePuzzle)) return false
            }
        }
        return true
    }

    fun isPieceAvailable(move: Move, state: PuzzleState): Boolean {
        return state.availablePieces.contains(move.piece)
    }

    fun placePiece(move: Move, state: PuzzleState) {
        if (!isValidPiecePlacement(move, state)) {
            TODO("exception handling, use isValidPiecePlacement? move it to another class for testability? do it somewhere else?")
        }
        if (!isPieceAvailable(move, state)) {
            TODO("nope nope nope")
        }
        val effectiveArea = move.getEffectiveArea()
        for ((rowIndex, row) in effectiveArea.rows.withIndex()) {
            for ((colIndex, newTile) in row.tiles.withIndex()) {
                val tileInThePuzzle = state.area.getTile(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                val mergedTile = tileInThePuzzle.merge(newTile)
                val coordinate = PuzzleAreaCoordinate(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                val tileReplacement = TileReplacement(coordinate, mergedTile)
                state.area = state.area.replaceTiles(tileReplacement)
            }
        }
        state.moves.add(move)
        state.availablePieces.remove(move.piece)
        state.usedPieces.add(move.piece)
    }

}
