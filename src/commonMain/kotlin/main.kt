import com.soywiz.korge.Korge
import com.soywiz.korge.scene.Module
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import org.gamezeug.digitspuzzle.domain.PuzzlePieceFactory
import org.gamezeug.digitspuzzle.domain.PuzzleSolver
import org.gamezeug.digitspuzzle.domain.PuzzleStateFactory
import org.gamezeug.digitspuzzle.ui.PuzzleScene
import org.gamezeug.digitspuzzle.ui.PuzzleUiState
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalStdlibApi
suspend fun main() = Korge(
		config = Korge.Config(module = PuzzleModule)
)

@ExperimentalTime
@ExperimentalStdlibApi
object PuzzleModule: Module() {

	override val mainScene = PuzzleScene::class
	override val bgcolor = Colors["#444444"]

	override suspend fun AsyncInjector.configure() {
		val pieces = PuzzlePieceFactory.buildAll()
		val puzzleState = PuzzleStateFactory.createInitialPuzzleState(pieces)
		val puzzleUiState = PuzzleUiState(
				lastPuzzleState = puzzleState,
				puzzleSolver = PuzzleSolver(puzzleState)
		)
		mapInstance(puzzleUiState)
		mapPrototype { PuzzleScene(get()) }
	}

}