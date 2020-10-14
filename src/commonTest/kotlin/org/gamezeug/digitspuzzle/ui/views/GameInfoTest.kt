package org.gamezeug.digitspuzzle.ui.views

import com.soywiz.korge.tests.ViewsForTesting
import org.gamezeug.digitspuzzle.domain.PuzzleSolver
import org.gamezeug.digitspuzzle.domain.PuzzleStateFactory
import org.gamezeug.digitspuzzle.ui.model.PuzzleUiState
import org.gamezeug.digitspuzzle.ui.scenes.PuzzleScene
import kotlin.test.Test
import kotlin.test.assertTrue

class GameInfoTest : ViewsForTesting() {

	@Test
	fun gameInfo_is_visible_to_user() = viewsTest {
		val puzzleState = PuzzleStateFactory.createInitialPuzzleState()
		val puzzleUiState = PuzzleUiState(
				lastPuzzleState = puzzleState,
				puzzleSolver = PuzzleSolver(puzzleState),
				version = "test"
		)
		val gameInfo = GameInfo(puzzleUiState, views)
		addChild(gameInfo)
		assertTrue(gameInfo.isVisibleToUser())
	}
}
