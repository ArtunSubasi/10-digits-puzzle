package org.gamezeug.digitspuzzle.ui.views

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Views
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.position
import com.soywiz.korge.view.text
import org.gamezeug.digitspuzzle.ui.model.PuzzleUiState
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class PuzzleSolverInfo(private val uiState: PuzzleUiState, private val views: Views): Container() {

	init {
		addStateCounterText()
		addElapsedTimeText()
		addStatePerSecondRateText()

		addUpdater {
			visible = uiState.puzzleSolver != null
		}
	}

	private fun addStateCounterText() {
		text("Checked:") {
			position(10, 10)
		}
		text("") {
			position(140, 10)
			addUpdater {
				uiState.puzzleSolver?.let {
					text = "${it.numberOfCheckedStates}"
				}
			}
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
					uiState.puzzleSolver?.let {
						text = it.startTime.elapsedNow().toComponents { hours, minutes, seconds, _
							->
							"${formatTimeComponent(hours)}:${formatTimeComponent(minutes)}:${formatTimeComponent(seconds)}"
						}
					}
				}
			}
		}
	}

	private fun addStatePerSecondRateText() {
		text("Rate:") {
			position(10, 70)
		}
		text("") {
			position(140, 70)
			addUpdater {
				uiState.puzzleSolver?.let {
					if (!uiState.lastPuzzleState.isSolved()) {
						val numberOfCheckedStates = it.numberOfCheckedStates.toDouble()
						val elapsedTimeInSeconds = it.startTime.elapsedNow().inSeconds
						val statesPerSecondRate: Double = numberOfCheckedStates / elapsedTimeInSeconds
						text = statesPerSecondRate.toString()
					}
				}
			}
		}
	}
}

private fun formatTimeComponent(timeComponent: Int) = timeComponent.toString().padStart(2, '0')
