import com.soywiz.korge.Korge
import com.soywiz.korge.scene.Module
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.file.std.resourcesVfs
import org.gamezeug.digitspuzzle.domain.PuzzlePieceFactory
import org.gamezeug.digitspuzzle.domain.PuzzleSolver
import org.gamezeug.digitspuzzle.domain.PuzzleStateFactory
import org.gamezeug.digitspuzzle.ui.PuzzleScene
import org.gamezeug.digitspuzzle.ui.PuzzleUiState

suspend fun main() = Korge(
		config = Korge.Config(module = PuzzleModule)
)

object PuzzleModule: Module() {

	override val mainScene = PuzzleScene::class
	override val bgcolor = Colors["#444444"]

	override suspend fun AsyncInjector.configure() {
		mapInstance(createInitialPuzzleUiState())
		mapPrototype { PuzzleScene(get()) }
	}

	private suspend fun createInitialPuzzleUiState(): PuzzleUiState {
		val pieces = PuzzlePieceFactory.buildAll()
		val puzzleState = PuzzleStateFactory.createInitialPuzzleState(pieces)
		return PuzzleUiState(
				lastPuzzleState = puzzleState,
				puzzleSolver = PuzzleSolver(puzzleState),
				version = getVersion()
		)
	}

	private suspend fun getVersion(): String {
		return resourcesVfs["version.txt"].readString()
	}

}