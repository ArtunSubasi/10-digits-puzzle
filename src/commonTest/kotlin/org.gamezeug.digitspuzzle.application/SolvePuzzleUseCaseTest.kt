package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SolvePuzzleUseCaseTest {

    @Test
    fun `solvePuzzle small`() {
        val pieces = listOf(
                PuzzlePieceFactory.build2(),
                PuzzlePieceFactory.build0(),
                PuzzlePieceFactory.build1(),
                PuzzlePieceFactory.build1()
        )
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(7, 5)
        val puzzleState = PuzzleState(initialArea, pieces)

        // When
        val solvedPuzzleState = SolvePuzzleUseCase().solvePuzzle(puzzleState)!!

        // Then
        assertEquals(listOf(), solvedPuzzleState.availablePieces)
        assertEquals(pieces.toSet(), solvedPuzzleState.usedPieces.toSet())
    }

    @Test
    fun `solvePuzzle not possible`() {
        // Given
        val pieces = listOf(PuzzlePieceFactory.build0(), PuzzlePieceFactory.build1())
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 3)
        val puzzleState = PuzzleState(initialArea, pieces)

        // When
        val solvedPuzzleState = SolvePuzzleUseCase().solvePuzzle(puzzleState)

        // Then
        assertNull(solvedPuzzleState)
    }

    @Test
    fun `getAvailableValidMoves only piece0 available`() {
        // Given
        val piece0 = PuzzlePieceFactory.build0()
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 3)
        val state = PuzzleState(initialArea, listOf(piece0))

        // When
        val availableMoves = SolvePuzzleUseCase().getAvailableValidMoves(state)

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