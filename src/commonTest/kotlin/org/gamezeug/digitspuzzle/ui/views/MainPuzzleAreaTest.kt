package org.gamezeug.digitspuzzle.ui.views

import com.soywiz.korge.tests.ViewsForTesting
import org.gamezeug.digitspuzzle.domain.PuzzleSolver
import org.gamezeug.digitspuzzle.domain.PuzzleStateFactory
import org.gamezeug.digitspuzzle.ui.model.PuzzleUiState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MainPuzzleAreaTest : ViewsForTesting() {

	@Test
	fun mainPuzzleArea_is_visible_to_user() = viewsTest {
		val puzzleState = PuzzleStateFactory.createInitialPuzzleState()
		val puzzleUiState = PuzzleUiState(
				lastPuzzleState = puzzleState,
				puzzleSolver = PuzzleSolver(puzzleState),
				version = "test"
		)
		val mainPuzzleArea = MainPuzzleArea(puzzleUiState, views)
		addChild(mainPuzzleArea)
		assertTrue(mainPuzzleArea.isVisibleToUser())
	}

	@Test
	fun mainPuzzleArea_has_GameInfo_as_a_child_view() = viewsTest {
		val puzzleState = PuzzleStateFactory.createInitialPuzzleState()
		val puzzleUiState = PuzzleUiState(
				lastPuzzleState = puzzleState,
				puzzleSolver = PuzzleSolver(puzzleState),
				version = "test"
		)
		val mainPuzzleArea = MainPuzzleArea(puzzleUiState, views)
		addChild(mainPuzzleArea)
		assertTrue(mainPuzzleArea.firstChild is GameInfo)
		assertEquals(1, mainPuzzleArea.numChildren)
	}
}
