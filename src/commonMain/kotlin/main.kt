import com.soywiz.korev.Key
import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.vector.*
import com.soywiz.korma.geom.vector.*
import org.gamezeug.digitspuzzle.application.PiecePlacementUseCase
import org.gamezeug.digitspuzzle.domain.*

suspend fun main() = Korge(width = 1100, height = 700, bgcolor = Colors["#444444"]) {
	val puzzleState = PuzzleStateFactory.createInitialPuzzleState()
	var inputState = InputState.SELECT_PIECE
	var piece = 0
	var x = 0
	var y = 0

	println("Piece: $piece, X: $x, Y: $y, Next action: $inputState")

	val tileWidth: Double = width / puzzleState.area.numberOfColumns
	val tileHeight: Double = height / puzzleState.area.numberOfRows
	println("tileWidth: $tileWidth, tileHeight: $tileHeight")
	val tilePrinter = TilePrinter(this, tileWidth, tileHeight)

	for ((rowIndex, row) in puzzleState.area.rows.withIndex()) {
		for ((colIndex, tile) in row.tiles.withIndex()) {
			tilePrinter.printGrids(colIndex, rowIndex)
			tilePrinter.printTile(colIndex, rowIndex, tile)
		}
	}

	onKeyDown {
		when (inputState) {
			InputState.SELECT_PIECE -> {
				piece = getIntFromKey(it.key)
				inputState = InputState.SELECT_X
			}
			InputState.SELECT_X -> {
				x = getIntFromKey(it.key)
				inputState = InputState.SELECT_Y
			}
			InputState.SELECT_Y -> {
				y = getIntFromKey(it.key)
				inputState = InputState.SELECT_PIECE
				val move = Move(PuzzleAreaCoordinate(x, y), PuzzlePieceFactory.build1())
				PiecePlacementUseCase().placePiece(move, puzzleState)

				// re-render all
				for ((rowIndex, row) in puzzleState.area.rows.withIndex()) {
					for ((colIndex, tile) in row.tiles.withIndex()) {
						tilePrinter.printTile(colIndex, rowIndex, tile)
					}
				}
			}
		}
		println("Piece: $piece, X: $x, Y: $y, Next action: $inputState")
	}
}

class TilePrinter(private val parent: Container, private val tileWidth: Double, private val tileHeight: Double) {
	fun printGrids(x: Int, y: Int) {
		parent.graphics {
			useNativeRendering = false
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
	}

	fun printTile(x: Int, y: Int, tile: Tile) {
		parent.graphics {
			useNativeRendering = false
			val x0: Double = x * tileWidth
			val y0: Double = y * tileHeight
			val x1: Double = x0 + tileWidth
			val y1: Double = y0 + tileHeight
			val xCenter: Double = x0 + (tileWidth / 2)
			val yCenter: Double = y0 + (tileHeight / 2)
			fill(Colors.WHITE) {
				if (tile.leftSegment != ' ') {
					line(x0, y0, xCenter, yCenter)
					lineTo(x0, y1)
					lineTo(x0, y0)
				}
				if (tile.topSegment != ' ') {
					line(x0, y0, x1, y0)
					lineTo(xCenter, yCenter)
					lineTo(x0, y0)
				}
				if (tile.rightSegment != ' ') {
					line(x1, y0, xCenter, yCenter)
					lineTo(x1, y1)
					lineTo(x1, y0)
				}
				if (tile.bottomSegment != ' ') {
					line(x0, y1, x1, y1)
					lineTo(xCenter, yCenter)
					lineTo(x0, y1)
				}
			}
		}
	}
}

enum class InputState {
	SELECT_PIECE,
	SELECT_X,
	SELECT_Y
}

private fun getIntFromKey(key: Key): Int {
	return when (key) {
		Key.N0 -> 0
		Key.N1 -> 1
		Key.N2 -> 2
		Key.N3 -> 3
		Key.N4 -> 4
		Key.N5 -> 5
		Key.N6 -> 6
		Key.N7 -> 7
		Key.N8 -> 8
		Key.N9 -> 9
		else -> 0
	}
}