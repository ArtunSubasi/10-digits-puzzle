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

    fun placePiece(move: Move, state: PuzzleState): PuzzleState {
        if (!isValidPiecePlacement(move, state)) {
            TODO("exception handling, use isValidPiecePlacement? move it to another class for testability? do it somewhere else?")
        }
        if (!isPieceAvailable(move, state)) {
            TODO("nope nope nope")
        }
        val tileReplacements = mutableListOf<TileReplacement>()

        for ((rowIndex, row) in move.getEffectiveArea().rows.withIndex()) {
            for ((colIndex, newTile) in row.tiles.withIndex()) {
                val tileInThePuzzle = state.area.getTile(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                val mergedTile = tileInThePuzzle.merge(newTile)
                val coordinate = PuzzleAreaCoordinate(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                tileReplacements.add(TileReplacement(coordinate, mergedTile))
            }
        }

        return PuzzleState(
                area = state.area.replaceTiles(*tileReplacements.toTypedArray()),
                availablePieces = state.availablePieces.minus(move.piece),
                moves = state.moves.plus(move),
                usedPieces = state.usedPieces.plus(move.piece)
        )
    }

}
