import com.soywiz.korge.Korge
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import org.gamezeug.digitspuzzle.domain.PuzzleAreaFactory

suspend fun main() = Korge(width = 1000, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val puzzleBoard = PuzzleAreaFactory.buildPuzzleAreaWithEdges(9, 11)
	text(puzzleBoard.toString())
}