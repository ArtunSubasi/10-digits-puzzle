package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*

// TODO this use case should be moved to the domain of the PuzzleState
class PiecePlacementUseCase {



    fun placePiece(move: Move, state: PuzzleState): PuzzleState {
        if (!state.area.isValidPiecePlacement(move)) {
            TODO("exception handling, use isValidPiecePlacement? move it to another class for testability? do it somewhere else?")
        }
        if (!state.isPieceAvailable(move)) {
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
