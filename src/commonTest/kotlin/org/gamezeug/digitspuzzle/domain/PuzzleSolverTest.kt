package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleSolverTest {

    @Test
    fun a_5x5_puzzle_with_pieces_3_and_8_is_solvable() {
        val pieces = listOf(
                PuzzlePieceFactory.build3(),
                PuzzlePieceFactory.build8()
        )
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 5)
        var puzzleState = PuzzleState(initialArea, pieces)

        // When
        val puzzleSolver = PuzzleSolver(puzzleState)
        while (puzzleSolver.hasNextState() && !puzzleState.isSolved()) {
            puzzleState = puzzleSolver.nextState()
        }

        // Then
        assertTrue(puzzleState.isSolved())
    }

    @Test
    fun a_5x5_puzzle_with_pieces_5_and_8_is_not_solvable() {
        // Given
        val pieces = listOf(
                PuzzlePieceFactory.build5(),
                PuzzlePieceFactory.build8()
        )
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 5)
        var puzzleState = PuzzleState(initialArea, pieces)

        // When
        val puzzleSolver = PuzzleSolver(puzzleState)
        while (puzzleSolver.hasNextState() && !puzzleState.isSolved()) {
            puzzleState = puzzleSolver.nextState()
        }

        // Then
        assertFalse(puzzleState.isSolved())
    }

}