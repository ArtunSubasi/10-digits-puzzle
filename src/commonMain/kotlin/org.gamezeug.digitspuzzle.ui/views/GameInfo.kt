package org.gamezeug.digitspuzzle.ui.views

import com.soywiz.korge.view.*
import org.gamezeug.digitspuzzle.ui.model.PuzzleUiState
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GameInfo(private val uiState: PuzzleUiState, private val views: Views): Container() {

	init {
		addStateCounterText()
		addElapsedTimeText()
		addStatePerSecondRateText()
		addVersionText()
	}

	private fun addStateCounterText() {
		text(uiState.stateCounter.toString()) {
			position(10, 10)
			addUpdater { text = uiState.stateCounter.toString() }
		}
	}

	private fun addElapsedTimeText() {
		text("") {
			position(views.virtualWidth - 120, 10)
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

	private fun addStatePerSecondRateText() {
		text("") {
			position(10, views.virtualHeight - 30)
			addUpdater {
				if (!uiState.lastPuzzleState.isSolved()) {
					val statesPerSecondRate: Double = uiState.stateCounter.toDouble() / uiState.puzzleStartTime.elapsedNow().inSeconds
					text = statesPerSecondRate.toString()
				}
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
