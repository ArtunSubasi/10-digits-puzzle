package org.gamezeug.digitspuzzle.application

import com.soywiz.kmem.isEven
import org.gamezeug.digitspuzzle.domain.*

class SolvePuzzleUseCase(private val observer: SolvePuzzleObserver? = null) {

    private val piecePlacementUseCase = PiecePlacementUseCase()
    private var triedMoves: Long = 0

    /**
     * Places all pieces in a valid way so that all available pieces are used.
     */
    fun solvePuzzle(state: PuzzleState): PuzzleState? {
        if (state.availablePieces.isEmpty()) {
            println("==================")
            println("Solved the puzzle!")
            println("==================")
            println(state)
            return state
        }
        val availableValidMoves = getAvailableValidMoves(state)
        for (move in availableValidMoves) {
            val newState = piecePlacementUseCase.placePiece(move, state)
            triedMoves++
            observer?.newStateFound(newState)
            val potentiallySolvedPuzzle = solvePuzzle(newState)
            if (potentiallySolvedPuzzle != null) {
                return potentiallySolvedPuzzle
            }
        }
        return null
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
            if (y.isEven) {
                for (x in 0 until state.area.numberOfColumns) {
                    if (x.isEven) {
                        availableCoordinates.add(PuzzleAreaCoordinate(x, y))
                    }
                }
            }
        }
        return availableCoordinates
    }

}

interface SolvePuzzleObserver {
    fun newStateFound(state: PuzzleState)
}
