import com.soywiz.korev.Key
import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.vector.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.geom.vector.*
import org.gamezeug.digitspuzzle.application.PiecePlacementUseCase
import org.gamezeug.digitspuzzle.domain.*

suspend fun main() = Korge(width = 1000, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val puzzleState = PuzzleStateFactory.createInitialPuzzleState()
	var inputState = InputState.SELECT_PIECE
	var piece = 0
	var x = 0
	var y = 0

	val stateView = text("")
	println("Piece: $piece, X: $x, Y: $y, Next action: $inputState")

	graphics {
		val graphics = this
		graphics.useNativeRendering = false

		position(100, 100)
		fill(Colors.RED) {
			//rect(-1.0, -1.0, 50.0, 50.0)
			val point1 = Point(0.5,0.5)
			val point2 = Point(49.5,0.5)
			val point3 = Point(25.0,24.5)
			line(point1, point2)
			lineTo(point3)
			lineTo(point1)
		}

		stroke(Colors.WHITE, Context2d.StrokeInfo(thickness = 2.0)) {
			rect(0.0, 0.0, 50.0, 50.0)
			line(0.0, 0.0, 50.0, 50.0)
			line(50.0, 0.0, 0.0, 50.0)
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
				stateView.text = puzzleState.area.toString()
			}
		}
		println("Piece: $piece, X: $x, Y: $y, Next action: $inputState")
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