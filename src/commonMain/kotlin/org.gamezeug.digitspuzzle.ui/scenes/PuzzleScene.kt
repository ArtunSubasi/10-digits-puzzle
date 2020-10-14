package org.gamezeug.digitspuzzle.ui.scenes

import com.soywiz.korev.Key
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.position
import org.gamezeug.digitspuzzle.domain.PuzzleSolver
import org.gamezeug.digitspuzzle.ui.model.PuzzleUiState
import org.gamezeug.digitspuzzle.ui.views.GameInfo
import org.gamezeug.digitspuzzle.ui.views.MainPuzzleArea
import org.gamezeug.digitspuzzle.ui.views.PuzzleSolverInfo
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class PuzzleScene(private val uiState: PuzzleUiState): Scene() {

	override suspend fun Container.sceneInit() {
		addMainPuzzleArea()
		addGameInfo()
		addPuzzleSolverInfo()

		addUpdater {
			if (views.input.keys.justReleased(Key.ENTER)) {
				uiState.puzzleSolver = when {
					uiState.puzzleSolver != null -> null
					else -> PuzzleSolver(uiState.lastPuzzleState)
				}
			}
			uiState.puzzleSolver?.let {
				for (i in 1..uiState.numberOfStatesToTryEachFrame) {
					if (it.hasNextState() && !uiState.lastPuzzleState.isSolved()) {
						uiState.lastPuzzleState = it.nextState()
						uiState.stateCounter++
					}
				}
			}
		}
	}

	private fun Container.addMainPuzzleArea() {
		val mainPuzzleArea = MainPuzzleArea(uiState, views)
		mainPuzzleArea.scale = 0.6
		mainPuzzleArea.position(0.0, views.virtualHeight - mainPuzzleArea.scaledHeight)
		addChild(mainPuzzleArea)
	}

	private fun Container.addGameInfo() {
		val gameInfo = GameInfo(uiState, views)
		addChild(gameInfo)
	}

	private fun Container.addPuzzleSolverInfo() {
		val component = PuzzleSolverInfo(uiState, views)
		component.position(300.0, 0.0)
		addChild(component)
	}
}