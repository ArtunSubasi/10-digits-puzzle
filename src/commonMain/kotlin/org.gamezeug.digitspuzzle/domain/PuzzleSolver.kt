package org.gamezeug.digitspuzzle.domain

@OptIn(ExperimentalStdlibApi::class)
class PuzzleSolver(initialState: PuzzleState) {

    private val statesToCheck = mutableListOf(initialState)

    init {
        nextState()
    }

    fun hasNextState() = statesToCheck.isNotEmpty()

    /**
     * Returns the next puzzle state. If this state is not a dead end, calculates the next states based on that one.
     */
    fun nextState(): PuzzleState {
        val nextState = statesToCheck.removeLast()
        if (!nextState.isDeadEnd()) {
            val availableValidMoves = nextState.getAvailableValidMoves().reversed()
            statesToCheck.addAll(availableValidMoves.map { nextState.placePiece(it) })
        }
        return nextState
    }

}

