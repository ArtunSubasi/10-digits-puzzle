package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*

class SolvePuzzleUseCase {

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
        println("Trying to solve a puzzle with ${state.availablePieces.size} available pieces. Move $triedMoves")
        if (triedMoves % 1000L == 0L) {
            println(state)
        }
        val availableValidMoves = getAvailableValidMoves(state)
        for (move in availableValidMoves) {
            val newState = piecePlacementUseCase.placePiece(move, state)
            triedMoves++
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
        if (availableValidMoves.size > 0) {
            println("Found ${availableValidMoves.size} valid moves!")
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
