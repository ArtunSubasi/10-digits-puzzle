import com.soywiz.korev.Key
import com.soywiz.korge.Korge
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import org.gamezeug.digitspuzzle.domain.*

suspend fun main() = Korge(width = 1000, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val puzzleState = PuzzleStateFactory.createInitialPuzzleState()
	var inputState = InputState.SELECT_PIECE
	var piece = 0
	var x = 0
	var y = 0

	val stateView = text(puzzleState.toString())
	println("Piece: $piece, X: $x, Y: $y, Next action: $inputState")

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
				puzzleState.area = puzzleState.area.replaceTiles(TileReplacement(PuzzleAreaCoordinate(x, y), fullTile()))
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