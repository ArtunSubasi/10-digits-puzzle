package org.gamezeug.digitspuzzle.ui.scenes

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.position
import org.gamezeug.digitspuzzle.ui.model.PuzzleUiState
import org.gamezeug.digitspuzzle.ui.views.GameInfo
import org.gamezeug.digitspuzzle.ui.views.MainPuzzleArea
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class PuzzleScene(private val uiState: PuzzleUiState): Scene() {

	override suspend fun Container.sceneInit() {
		addMainPuzzleArea()
		addGameInfo()

		addUpdater {
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
}