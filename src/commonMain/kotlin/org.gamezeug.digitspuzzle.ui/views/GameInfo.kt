package org.gamezeug.digitspuzzle.ui.views

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Views
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.position
import com.soywiz.korge.view.text
import org.gamezeug.digitspuzzle.ui.model.PuzzleUiState
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GameInfo(private val uiState: PuzzleUiState, private val views: Views): Container() {

	init {
		addStateCounterText()
		addElapsedTimeText()
		addDeadEndText()
		addVersionText()
	}

	private fun addStateCounterText() {
		text("Moves:") {
			position(10, 10)
		}
		text("${uiState.stateCounter}") {
			position(140, 10)
			addUpdater { text = "${uiState.stateCounter}" }
		}
	}

	private fun addElapsedTimeText() {
		text("Duration:") {
			position(10, 40)
		}
		text("") {
			position(140, 40)
			addUpdater {
				if (!uiState.lastPuzzleState.isSolved()) {
					text = uiState.puzzleStartTime.elapsedNow().toComponents { hours, minutes, seconds, _
						->
						"${formatTimeComponent(hours)}:${formatTimeComponent(minutes)}:${formatTimeComponent(seconds)}"
					}
				}
			}
		}
	}

	private fun addDeadEndText() {
		text("") {
			position(10, 70)
			addUpdater {
				text = if (uiState.puzzleSolver == null && uiState.lastPuzzleState.isDeadEnd()) "Dead end!" else ""
			}
		}
	}

	private fun addVersionText() {
		text(uiState.version) {
			position(views.virtualWidth - 95, views.virtualHeight - 30)
		}
	}
}

private fun formatTimeComponent(timeComponent: Int) = timeComponent.toString().padStart(2, '0')
