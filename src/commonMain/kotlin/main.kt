import com.soywiz.korge.Korge
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.vector.Context2d
import com.soywiz.korma.geom.vector.line
import com.soywiz.korma.geom.vector.rect
import org.gamezeug.digitspuzzle.domain.PuzzlePieceFactory
import org.gamezeug.digitspuzzle.domain.PuzzleSolver
import org.gamezeug.digitspuzzle.domain.PuzzleStateFactory
import org.gamezeug.digitspuzzle.domain.Tile
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/*
TODO this main method was used by the Korge example project.
Refactor this class after figuring out how Korge works and can be structured.
 */
@ExperimentalTime
@ExperimentalStdlibApi
suspend fun main() = Korge(width = 1100, height = 700, bgcolor = Colors["#444444"]) {

	val pieces = PuzzlePieceFactory.buildAll()
	val puzzleState = PuzzleStateFactory.createInitialPuzzleState(pieces)
	val tileWidth: Double = width / puzzleState.area.numberOfColumns
	val tileHeight: Double = height / puzzleState.area.numberOfRows

	val puzzleSolver = PuzzleSolver(puzzleState)
	var stateCounter: Long = 0
	val puzzleStartTime = TimeSource.Monotonic.markNow()
	val numberOfStatesToTryEachFrame = 100 // TODO make this modifable in the UI for fun!
	var lastState = puzzleState

	addUpdater {
		for (i in 1..numberOfStatesToTryEachFrame) {
			if (puzzleSolver.hasNextState() && !lastState.isSolved()) {
				lastState = puzzleSolver.nextState()
				stateCounter++
			}
		}
	}

	graphics {
		useNativeRendering = false
		val tilePrinter = TilePrinter(this, tileWidth, tileHeight)
		addUpdater {
			clear()
			if (!lastState.isSolved()) {
				for ((rowIndex, row) in lastState.area.rows.withIndex()) {
					for ((colIndex, tile) in row.tiles.withIndex()) {
						tilePrinter.printGrids(colIndex, rowIndex)
						tilePrinter.printTile(colIndex, rowIndex, tile)
					}
				}
			}
		}
		text(stateCounter.toString()) {
			position(10, 10)
			addUpdater { text = stateCounter.toString() }
		}
		text(stateCounter.toString()) {
			position(parent?.width!! - 120, 10)
			addUpdater {
				if (!lastState.isSolved()) {
					text = puzzleStartTime.elapsedNow().toComponents { hours, minutes, seconds, _
						->
						"${formatTimeComponent(hours)}:${formatTimeComponent(minutes)}:${formatTimeComponent(seconds)}"
					}
				}
			}
		}
		text(stateCounter.toString()) {
			position(10, parent?.height!! - 30)
			addUpdater {
				if (!lastState.isSolved()) {
					val statesPerSecondRate: Double = stateCounter.toDouble() / puzzleStartTime.elapsedNow().inSeconds
					text = statesPerSecondRate.toString()
				}
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

private fun formatTimeComponent(timeComponent: Int) = timeComponent.toString().padStart(2, '0')

class TilePrinter(private val graphics: Graphics, private val tileWidth: Double, private val tileHeight: Double) {

	fun printGrids(x: Int, y: Int) {
		val x0: Double = x * tileWidth
		val y0: Double = y * tileHeight
		val x1: Double = x0 + tileWidth
		val y1: Double = y0 + tileHeight
		graphics.stroke(Colors["#333333"], Context2d.StrokeInfo(thickness = 2.0)) {
			rect(x0, y0, tileWidth, tileHeight)
		}
		graphics.stroke(Colors["#333333"], Context2d.StrokeInfo(thickness = 1.0)) {
			line(x0, y0, x1, y1)
			line(x1, y0, x0, y1)
		}
	}

	fun printTile(x: Int, y: Int, tile: Tile) {
		val x0: Double = x * tileWidth
		val y0: Double = y * tileHeight
		val x1: Double = x0 + tileWidth
		val y1: Double = y0 + tileHeight
		val xCenter: Double = x0 + (tileWidth / 2)
		val yCenter: Double = y0 + (tileHeight / 2)

		if (tile.leftSegment != ' ') {
			graphics.fill(chooseSegmentColor(tile.leftSegment)) {
				line(x0, y0, xCenter, yCenter)
				lineTo(x0, y1)
				lineTo(x0, y0)
			}
		}
		if (tile.topSegment != ' ') {
			graphics.fill(chooseSegmentColor(tile.topSegment)) {
				line(x0, y0, x1, y0)
				lineTo(xCenter, yCenter)
				lineTo(x0, y0)
			}
		}
		if (tile.rightSegment != ' ') {
			graphics.fill(chooseSegmentColor(tile.rightSegment)) {
				line(x1, y0, xCenter, yCenter)
				lineTo(x1, y1)
				lineTo(x1, y0)
			}
		}
		if (tile.bottomSegment != ' ') {
			graphics.fill(chooseSegmentColor(tile.bottomSegment)) {
				line(x0, y1, x1, y1)
				lineTo(xCenter, yCenter)
				lineTo(x0, y1)
			}
		}
	}
}