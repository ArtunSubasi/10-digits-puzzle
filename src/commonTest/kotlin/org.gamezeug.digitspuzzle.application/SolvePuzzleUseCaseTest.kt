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

    @Test
    fun `getAvailableValidMoves only piece0 available`() {
        // Given
        val piece0 = PuzzlePieceFactory.build0()
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 3)
        val state = PuzzleState(initialArea, listOf(piece0))

        // When
        val availableMoves = SolvePuzzleUseCase(state).getAvailableValidMoves(state)

        // Then
        val standard = Move(PuzzleAreaCoordinate(0, 0), piece0)
        val rotated180 = Move(PuzzleAreaCoordinate(0, 0), piece0, rotation = Rotation.ROTATE_180_DEGREES)
        val mirrored = Move(PuzzleAreaCoordinate(0, 0), piece0, mirroring = Mirroring.MIRROR_HORIZONTALLY)
        val mirroredAndRotated180 = Move(
                PuzzleAreaCoordinate(0, 0),
                piece0,
                mirroring = Mirroring.MIRROR_HORIZONTALLY,
                rotation = Rotation.ROTATE_180_DEGREES
        )
        assertEquals(setOf(standard, rotated180, mirrored, mirroredAndRotated180), availableMoves.toSet())
    }

}