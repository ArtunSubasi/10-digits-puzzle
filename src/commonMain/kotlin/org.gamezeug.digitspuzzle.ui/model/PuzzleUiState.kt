package org.gamezeug.digitspuzzle.ui.model

import org.gamezeug.digitspuzzle.domain.PuzzleSolver
import org.gamezeug.digitspuzzle.domain.PuzzleState
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource

@OptIn(ExperimentalTime::class)
data class PuzzleUiState constructor(
        var lastPuzzleState: PuzzleState,
        var stateCounter: Long = 0,
        var numberOfStatesToTryEachFrame: Int = 100,
        var puzzleSolver: PuzzleSolver? = null,
        val puzzleStartTime: TimeMark = TimeSource.Monotonic.markNow(),
        val version: String
)
