package org.gamezeug.digitspuzzle.application

import com.soywiz.kmem.isEven
import com.soywiz.korio.async.async
import com.soywiz.korio.async.runBlockingNoSuspensions
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.gamezeug.digitspuzzle.domain.*

// TODO after the changes in this class, the naming is not suitable anymore. Come up with a new name. Make iterable?
@ExperimentalStdlibApi
class SolvePuzzleUseCase(initialState: PuzzleState) {

    private val piecePlacementUseCase = PiecePlacementUseCase()
    private val statesToCheck = mutableListOf(initialState)

    init {
        nextState()
    }

    fun hasNextState() = statesToCheck.isNotEmpty()

    /**
     * Returns the next puzzle state. If this state is solvable, calculates the next states based on that one.
     */
    fun nextState(): PuzzleState {
        val nextState = statesToCheck.removeLast()
        if (shouldContinueSolvingPuzzle(nextState)) {
            val availableValidMoves = getAvailableValidMoves(nextState).reversed()
            runBlockingNoSuspensions {
                statesToCheck.addAll(availableValidMoves.pmap { piecePlacementUseCase.placePiece(it, nextState) })
            }
        }
        return nextState
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

    // TODO move to PuzzleState
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

    // TODO move to PuzzleArea
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

