package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*
import kotlin.test.*

@ExperimentalStdlibApi
class SolvePuzzleUseCaseTest {

    @Test
    fun `solvePuzzle small`() {
        val pieces = listOf(
                PuzzlePieceFactory.build3(),
                PuzzlePieceFactory.build8()
        )
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 5)
        val puzzleState = PuzzleState(initialArea, pieces)

        // When
        val solvePuzzleUseCase = SolvePuzzleUseCase(puzzleState)
        val newPuzzleState = solvePuzzleUseCase.nextState()

        // Then
        assertFalse(newPuzzleState.isSolved())
        assertTrue(solvePuzzleUseCase.hasNextState())
    }

    @Test
    fun `solvePuzzle not possible`() {
        // Given
        val pieces = listOf(PuzzlePieceFactory.build0(), PuzzlePieceFactory.build1())
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(4, 3)
        val puzzleState = PuzzleState(initialArea, pieces)

        // When
        val solvePuzzleUseCase = SolvePuzzleUseCase(puzzleState)

        // Then
        assertFalse(solvePuzzleUseCase.hasNextState())
    }

}