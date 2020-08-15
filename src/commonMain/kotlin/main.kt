import com.soywiz.korev.Key
import com.soywiz.korge.Korge
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import org.gamezeug.digitspuzzle.domain.*

suspend fun main() = Korge(width = 1000, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val state = PuzzleStateFactory.createInitialPuzzleState()

	val stateView = text(state.toString())

	var x = 0
	var y = 0
	var waitingForY = false

	onKeyDown {
		if (waitingForY) {
			y = getIntFromKey(it.key)
			state.area = state.area.replaceTiles(TileReplacement(PuzzleAreaCoordinate(x, y), fullTile()))
			stateView.text = state.area.toString()
		} else {
			x = getIntFromKey(it.key)
		}
		waitingForY = !waitingForY
		println("X: $x, Y: $y")
	}
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