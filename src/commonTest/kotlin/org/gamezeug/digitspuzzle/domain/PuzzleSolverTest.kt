package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleSolverTest {

    @Test
    fun puzzle_solvable() {
        val pieces = listOf(
                PuzzlePieceFactory.build3(),
                PuzzlePieceFactory.build8()
        )
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 5)
        val puzzleState = PuzzleState(initialArea, pieces)

        // When
        val solvePuzzleUseCase = PuzzleSolver(puzzleState)
        val newPuzzleState = solvePuzzleUseCase.nextState()

        // Then
        assertFalse(newPuzzleState.isSolved())
        assertTrue(solvePuzzleUseCase.hasNextState())
    }

    @Test
    fun puzzle_not_solvable() {
        // Given
        val pieces = listOf(PuzzlePieceFactory.build0(), PuzzlePieceFactory.build1())
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(4, 3)
        val puzzleState = PuzzleState(initialArea, pieces)

        // When
        val solvePuzzleUseCase = PuzzleSolver(puzzleState)

        // Then
        assertFalse(solvePuzzleUseCase.hasNextState())
    }

}