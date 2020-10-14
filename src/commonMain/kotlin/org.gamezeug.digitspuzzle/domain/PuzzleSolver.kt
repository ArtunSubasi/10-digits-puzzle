package org.gamezeug.digitspuzzle.domain

import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource

@OptIn(ExperimentalStdlibApi::class, ExperimentalTime::class)
class PuzzleSolver(initialState: PuzzleState) {

    var numberOfCheckedStates = 0
    val startTime: TimeMark = TimeSource.Monotonic.markNow()

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
        if (!nextState.isSolved() && !nextState.isDeadEnd()) {
            val availableValidMoves = nextState.getAvailableValidMoves().reversed()
            statesToCheck.addAll(availableValidMoves.map { nextState.placePiece(it) })
        }
        numberOfCheckedStates++
        return nextState
    }

}

