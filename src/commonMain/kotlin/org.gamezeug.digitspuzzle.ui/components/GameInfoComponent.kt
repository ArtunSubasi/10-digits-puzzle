package org.gamezeug.digitspuzzle.ui.components

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.vector.Context2d
import com.soywiz.korma.geom.vector.line
import com.soywiz.korma.geom.vector.rect
import org.gamezeug.digitspuzzle.domain.Tile
import org.gamezeug.digitspuzzle.ui.PuzzleUiState
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GameInfoComponent(private val uiState: PuzzleUiState, private val views: Views): Container() {

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
