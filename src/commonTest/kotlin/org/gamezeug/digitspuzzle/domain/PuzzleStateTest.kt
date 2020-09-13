package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleStateTest {

    @Test
    fun the_initial_puzzle_state_has_an_11x9_area() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf())
        assertEquals(11, state.area.numberOfColumns)
        assertEquals(9, state.area.numberOfRows)
    }

    @Test
    fun the_initial_puzzle_state_does_not_have_used_pieces() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf())
        assertEquals(0, state.usedPieces.size)
    }

    @Test
    fun the_initial_puzzle_state_does_not_have_moves() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf())
        assertEquals(0, state.moves.size)
    }

    @Test
    fun the_initial_puzzle_state_with_piece_1_has_only_piece_1_available() {
        val piece1 = PuzzlePieceFactory.build1()
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf(piece1))
        assertTrue(state.hasAvailablePiece(piece1))
        assertEquals(1, state.availablePieces.size)
    }

    @Test
    fun the_initial_puzzle_state_with_piece_1_does_not_have_piece_2_available() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf(PuzzlePieceFactory.build1()))
        assertFalse(state.hasAvailablePiece(PuzzlePieceFactory.build2()))
    }

    @Test
    fun a_puzzle_state_with_1_puzzle_piece_is_not_solved() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf(PuzzlePieceFactory.build1()))
        assertFalse(state.isSolved())
    }

    @Test
    fun a_puzzle_state_without_any_available_pieces_is_solved() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf())
        assertTrue(state.isSolved())
    }

    @Test
    fun placing_puzzle_piece_1_to_a_1x5_area_solves_the_puzzle() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val area = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 1)
        val state = PuzzleState(area, mutableListOf(piece))

        // When
        val newState = state.placePiece(move)

        // Then
        val expected = """
            [ X ]
            [  X]
            [ 1 ]
            [ 1 ]
            [1 1]
            [ 1 ]
            [ 1 ]
            [1  ]
            [ 1 ]
            [ 1 ]
            [1 1]
            [ 1 ]
            [ 1 ]
            [  X]
            [ X ]
        """.trimIndent()
        assertEquals(expected, newState.area.toString())
        assertEquals(listOf(), newState.availablePieces)
        assertEquals(listOf(piece), newState.usedPieces)
        assertEquals(listOf(move), newState.moves)
        assertTrue(newState.isSolved())
    }

    @Test
    fun a_puzzle_state_with_a_3x5_area_and_piece_0_has_4_different_available_valid_moves() {
        // Given
        val piece0 = PuzzlePieceFactory.build0()
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 3)
        val state = PuzzleState(initialArea, listOf(piece0))

        // When
        val availableMoves = state.getAvailableValidMoves()

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