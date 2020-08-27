import com.soywiz.korge.Korge
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.vector.Context2d
import com.soywiz.korma.geom.vector.line
import com.soywiz.korma.geom.vector.rect
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.gamezeug.digitspuzzle.application.SolvePuzzleObserver
import org.gamezeug.digitspuzzle.application.SolvePuzzleUseCase
import org.gamezeug.digitspuzzle.domain.PuzzlePieceFactory
import org.gamezeug.digitspuzzle.domain.PuzzleState
import org.gamezeug.digitspuzzle.domain.PuzzleStateFactory
import org.gamezeug.digitspuzzle.domain.Tile

/*
TODO this main method was used by the Korge example project.
Refactor this class after figuring out how Korge works and can be structured.
 */
@ExperimentalStdlibApi
suspend fun main() = Korge(width = 1100, height = 700, bgcolor = Colors["#444444"]) {

	val pieces = PuzzlePieceFactory.buildAll()
	val puzzleState = PuzzleStateFactory.createInitialPuzzleState(pieces)
	val tileWidth: Double = width / puzzleState.area.numberOfColumns
	val tileHeight: Double = height / puzzleState.area.numberOfRows
	println("tileWidth: $tileWidth, tileHeight: $tileHeight")
	val tilePrinter = TilePrinter(this, tileWidth, tileHeight)

	val observer = object : SolvePuzzleObserver {
		var stateCounter: Long = 0
		override fun newStateFound(state: PuzzleState) {
				tilePrinter.clear()
				for ((rowIndex, row) in state.area.rows.withIndex()) {
					for ((colIndex, tile) in row.tiles.withIndex()) {
						tilePrinter.printGrids(colIndex, rowIndex)
						tilePrinter.printTile(colIndex, rowIndex, tile)
						tilePrinter.printStates(stateCounter)
					}
				}
			stateCounter++
		}
	}

	GlobalScope.launch { // launch a new coroutine in background and continue
		SolvePuzzleUseCase(observer).solvePuzzle(puzzleState)
	}
}

class TilePrinter(private val parent: Container, private val tileWidth: Double, private val tileHeight: Double) {

	private var graphics: Graphics = initGraphics()
	private var text = parent.text("") {
		position(10, 10)
	}

	// TODO this looks like a hack. Find out how to clear the graphics without removing and creating a new one.
	fun clear() {
		parent.removeChild(graphics)
		graphics.close()
		graphics = initGraphics()
		parent.removeChild(text)
		parent.addChild(text)
	}

	private fun initGraphics(): Graphics {
		return parent.graphics {
			useNativeRendering = false
		}
	}

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

	fun printStates(stateCounter: Long) {
		text.text = stateCounter.toString()
	}
}