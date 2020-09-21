package org.gamezeug.digitspuzzle.ui

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import org.gamezeug.digitspuzzle.ui.components.MainPuzzleAreaComponent
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class PuzzleScene(private val uiState: PuzzleUiState): Scene() {

	override suspend fun Container.sceneInit() {
		addChild(MainPuzzleAreaComponent(uiState, views))
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
}