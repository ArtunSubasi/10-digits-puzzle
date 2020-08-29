package org.gamezeug.digitspuzzle.application

import com.soywiz.kmem.isEven
import com.soywiz.korio.async.async
import com.soywiz.korio.async.runBlockingNoSuspensions
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.gamezeug.digitspuzzle.domain.*

class SolvePuzzleUseCase(private val observer: SolvePuzzleObserver? = null) {

    private val piecePlacementUseCase = PiecePlacementUseCase()
    private var triedMoves: Long = 0

    /**
     * Places all pieces in a valid way so that all available pieces are used.
     */
    @ExperimentalStdlibApi
    fun solvePuzzle(state: PuzzleState): PuzzleState? {
        val statesToCheck = mutableListOf(state)
        while (statesToCheck.isNotEmpty()) {
            triedMoves++
            val lastState = statesToCheck.removeLast()
            observer?.newStateFound(lastState)
            if (triedMoves % 10_000L == 0L) {
                println("Tried $triedMoves puzzle states. Number of states to check: ${statesToCheck.size}")
            }
            if (lastState.isSolved()) {
                println("==================")
                println("Solved the puzzle!")
                println("by trying $triedMoves puzzle states")
                println("==================")
                println(lastState)
                return lastState
            }

            if (shouldContinueSolvingPuzzle(lastState)) {
                val availableValidMoves = getAvailableValidMoves(lastState).reversed()
                runBlockingNoSuspensions {
                    statesToCheck.addAll(availableValidMoves.pmap { piecePlacementUseCase.placePiece(it, lastState) })
                }
            }
        }

        // We tried all possible states and could not find a solution
        return null
    }

    /**
     * Check if it makes sense to continue solving this puzzle state.
     */
    private fun shouldContinueSolvingPuzzle(lastState: PuzzleState): Boolean {
        // Break if the puzzle contains a blank area which is too small for the smallest piece
        val areaOfSmallestPiece = lastState.availablePieces.map { it.area.getNumberOfFilledTiles() }.min()!!
        val tooSmallBlankArea = lastState.area.getBlankAreaMap().values.find { it in 2..areaOfSmallestPiece }
        return tooSmallBlankArea == null
    }

    private suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
        map { async { f(it) } }.awaitAll()
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
