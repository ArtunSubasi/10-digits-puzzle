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
class MainPuzzleAreaComponent(private val uiState: PuzzleUiState, private val views: Views): Graphics() {

	init {
		useNativeRendering = true
		addChild(GameInfoComponent(uiState, views))
		addUpdater {
			if (!uiState.lastPuzzleState.isSolved()) {
				drawPuzzleArea()
			}
		}
	}

	private fun drawPuzzleArea() {
		clear()
		for ((rowIndex, row) in uiState.lastPuzzleState.area.rows.withIndex()) {
			for ((colIndex, tile) in row.tiles.withIndex()) {
				drawGrids(colIndex, rowIndex)
				drawTile(colIndex, rowIndex, tile)
			}
		}
	}

	private fun drawGrids(x: Int, y: Int) {
		val tileWidth: Double = views.virtualWidth.toDouble() / uiState.lastPuzzleState.area.numberOfColumns
		val tileHeight: Double = views.virtualHeight.toDouble() / uiState.lastPuzzleState.area.numberOfRows
		val x0: Double = x * tileWidth
		val y0: Double = y * tileHeight
		val x1: Double = x0 + tileWidth
		val y1: Double = y0 + tileHeight
		stroke(Colors["#333333"], Context2d.StrokeInfo(thickness = 2.0)) {
			rect(x0, y0, tileWidth, tileHeight)
		}
		stroke(Colors["#333333"], Context2d.StrokeInfo(thickness = 1.0)) {
			line(x0, y0, x1, y1)
			line(x1, y0, x0, y1)
		}
	}

	private fun drawTile(x: Int, y: Int, tile: Tile) {
		val tileWidth: Double = views.virtualWidth.toDouble() / uiState.lastPuzzleState.area.numberOfColumns
		val tileHeight: Double = views.virtualHeight.toDouble() / uiState.lastPuzzleState.area.numberOfRows
		val x0: Double = x * tileWidth
		val y0: Double = y * tileHeight
		val x1: Double = x0 + tileWidth
		val y1: Double = y0 + tileHeight
		val xCenter: Double = x0 + (tileWidth / 2)
		val yCenter: Double = y0 + (tileHeight / 2)

		if (tile.leftSegment != ' ') {
			fill(chooseSegmentColor(tile.leftSegment)) {
				line(x0, y0, xCenter, yCenter)
				lineTo(x0, y1)
				lineTo(x0, y0)
			}
		}
		if (tile.topSegment != ' ') {
			fill(chooseSegmentColor(tile.topSegment)) {
				line(x0, y0, x1, y0)
				lineTo(xCenter, yCenter)
				lineTo(x0, y0)
			}
		}
		if (tile.rightSegment != ' ') {
			fill(chooseSegmentColor(tile.rightSegment)) {
				line(x1, y0, xCenter, yCenter)
				lineTo(x1, y1)
				lineTo(x1, y0)
			}
		}
		if (tile.bottomSegment != ' ') {
			fill(chooseSegmentColor(tile.bottomSegment)) {
				line(x0, y1, x1, y1)
				lineTo(xCenter, yCenter)
				lineTo(x0, y1)
			}
		}
	}
}

private fun chooseSegmentColor(segmentChar: Char): RGBA {
	return when (segmentChar) {
		'0' -> Colors["#FF0000"]
		'1' -> Colors["#FF7F00"]
		'2' -> Colors["#FFFF00"]
		'3' -> Colors["#7FFF00"]
		'4' -> Colors["#00FFFF"]
		'5' -> Colors["#007FFF"]
		'6' -> Colors["#0000FF"]
		'7' -> Colors["#7F00FF"]
		'8' -> Colors["#FF00FF"]
		'9' -> Colors["#FF7FFF"]
		'X' -> Colors["#000000"]
		else -> Colors["#FFFFFF"]
	}
}
