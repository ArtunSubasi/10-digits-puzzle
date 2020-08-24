package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SolvePuzzleUseCaseTest {

    @Test
    fun solvePuzzle() {
        // Given
        val pieces = mapOf(
                0 to PuzzlePieceFactory.build0(),
                1 to PuzzlePieceFactory.build1(),
                2 to PuzzlePieceFactory.build2(),
                3 to PuzzlePieceFactory.build3(),
                4 to PuzzlePieceFactory.build4(),
                5 to PuzzlePieceFactory.build5(),
                6 to PuzzlePieceFactory.build6(),
                7 to PuzzlePieceFactory.build7(),
                8 to PuzzlePieceFactory.build8(),
                9 to PuzzlePieceFactory.build9()
        )
        val puzzleState = PuzzleStateFactory.createInitialPuzzleState(pieces.values.toList())

        // When
        val solvedPuzzleState = SolvePuzzleUseCase().solvePuzzle(puzzleState)

        // Then
        assertEquals(listOf(), solvedPuzzleState.availablePieces)
        assertEquals(pieces.values.toSet(), solvedPuzzleState.usedPieces.toSet())
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

    @Test
    fun `getAvailableValidMoves only piece1 available, only 90 degrees`() {
        // Given
        val piece1 = PuzzlePieceFactory.build1()
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(2, 6)
        val state = PuzzleState(initialArea, listOf(piece1))

        // When
        val availableMoves = SolvePuzzleUseCase().getAvailableValidMoves(state)

        // Then
        val rotated90X0Y0 = Move(
                PuzzleAreaCoordinate(0, 0),
                piece1,
                rotation = Rotation.ROTATE_90_DEGREES_CLOCKWISE
        )
        val rotated90MirroredX0Y0 = Move(
                PuzzleAreaCoordinate(0, 0),
                piece1,
                mirroring = Mirroring.MIRROR_HORIZONTALLY,
                rotation = Rotation.ROTATE_90_DEGREES_CLOCKWISE
        )
        val rotated90X0Y1 = Move(
                PuzzleAreaCoordinate(0, 1),
                piece1,
                rotation = Rotation.ROTATE_90_DEGREES_CLOCKWISE
        )
        val rotated90MirroredX0Y1 = Move(
                PuzzleAreaCoordinate(0, 1),
                piece1,
                mirroring = Mirroring.MIRROR_HORIZONTALLY,
                rotation = Rotation.ROTATE_90_DEGREES_CLOCKWISE
        )
        val rotated90X1Y0 = Move(
                PuzzleAreaCoordinate(1, 0),
                piece1,
                rotation = Rotation.ROTATE_90_DEGREES_CLOCKWISE
        )
        val rotated90MirroredX1Y0 = Move(
                PuzzleAreaCoordinate(1, 0),
                piece1,
                mirroring = Mirroring.MIRROR_HORIZONTALLY,
                rotation = Rotation.ROTATE_90_DEGREES_CLOCKWISE
        )
        val rotated90X1Y1 = Move(
                PuzzleAreaCoordinate(1, 1),
                piece1,
                rotation = Rotation.ROTATE_90_DEGREES_CLOCKWISE
        )
        val rotated90MirroredX1Y1 = Move(
                PuzzleAreaCoordinate(1, 1),
                piece1,
                mirroring = Mirroring.MIRROR_HORIZONTALLY,
                rotation = Rotation.ROTATE_90_DEGREES_CLOCKWISE
        )
        val expected = setOf(
                rotated90X0Y0,
                rotated90MirroredX0Y0,
                rotated90X0Y1,
                rotated90MirroredX0Y1,
                rotated90X1Y0,
                rotated90MirroredX1Y0,
                rotated90X1Y1,
                rotated90MirroredX1Y1
        )
        assertEquals(expected, availableMoves.toSet())
    }

}