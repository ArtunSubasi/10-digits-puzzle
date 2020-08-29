package org.gamezeug.digitspuzzle.domain

@ExperimentalStdlibApi
class PuzzleSolver(initialState: PuzzleState) {

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
            val availableValidMoves = nextState.getAvailableValidMoves().reversed()
            statesToCheck.addAll(availableValidMoves.map { nextState.placePiece(it) })
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

}

