package org.gamezeug.digitspuzzle.ui.views

import com.soywiz.korge.tests.ViewsForTesting
import org.gamezeug.digitspuzzle.domain.PuzzleSolver
import org.gamezeug.digitspuzzle.domain.PuzzleStateFactory
import org.gamezeug.digitspuzzle.ui.model.PuzzleUiState
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleSolverInfoTest : ViewsForTesting() {

	@Test
	fun puzzleSolverInfo_is_visible_to_user_if_puzzleSolver_is_running() = viewsTest {
		val puzzleState = PuzzleStateFactory.createInitialPuzzleState()
		val puzzleUiState = PuzzleUiState(
				lastPuzzleState = puzzleState,
				puzzleSolver = PuzzleSolver(puzzleState),
				version = "test"
		)
		val puzzleSolverInfo = PuzzleSolverInfo(puzzleUiState, views)
		addChild(puzzleSolverInfo)
		assertTrue(puzzleSolverInfo.isVisibleToUser())
	}

	@Test
	fun puzzleSolverInfo_is_not_visible_to_user_if_puzzleSolver_is_not_running() = viewsTest {
		val puzzleState = PuzzleStateFactory.createInitialPuzzleState()
		val puzzleUiState = PuzzleUiState(
				lastPuzzleState = puzzleState,
				puzzleSolver = null,
				version = "test"
		)
		val puzzleSolverInfo = PuzzleSolverInfo(puzzleUiState, views)
		addChild(puzzleSolverInfo)
		assertFalse(puzzleSolverInfo.isVisibleToUser())
	}

}
