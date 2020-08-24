package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*

class SolvePuzzleUseCase {

    val piecePlacementUseCase = PiecePlacementUseCase()

    /**
     * Places all pieces in a valid way so that all available pieces are used.
     */
    fun solvePuzzle(state: PuzzleState): PuzzleState {

        TODO()
    }

    /**
     * Get all available valid moves without duplicates. Warning: Incoming loop of loops!
     */
    fun getAvailableValidMoves(state: PuzzleState): List<Move> {
        val availableValidMoves = mutableListOf<Move>()
        for (coordinate in getAvailableCoordinates(state)) {
            for (piece in state.availablePieces) {
                for (rotation in Rotation.values()) {
                    for (mirroring in Mirroring.values()) {
                        val move = Move(coordinate, piece, rotation, mirroring)
                        if (piecePlacementUseCase.isValidPiecePlacement(move, state)) {
                            availableValidMoves.add(move)
                        }
                    }
                }
            }
        }
        return availableValidMoves.toList()
    }

    private fun getAvailableCoordinates(state: PuzzleState): Set<PuzzleAreaCoordinate> {
        val availableCoordinates = mutableSetOf<PuzzleAreaCoordinate>()
        for (y in 0 until state.area.numberOfRows) {
            for (x in 0 until state.area.numberOfColumns) {
                availableCoordinates.add(PuzzleAreaCoordinate(x, y))
            }
        }
        return availableCoordinates
    }

}
