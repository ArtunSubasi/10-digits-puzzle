package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleStateTest {

    @Test
    fun create_initial_puzzle_state() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf(PuzzlePieceFactory.build1()))
        assertEquals(11, state.area.numberOfColumns)
        assertEquals(9, state.area.numberOfRows)
        assertEquals(1, state.availablePieces.size)
        assertEquals(0, state.usedPieces.size)
        assertEquals(0, state.moves.size)
        assertFalse(state.isSolved())
    }

    @Test
    fun puzzle_without_available_pieces_should_be_solved() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf())
        assertTrue(state.isSolved())
    }

    @Test
    fun puzzle_piece_is_available_to_make_the_move() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val area = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 1)
        val state = PuzzleState(area, mutableListOf(piece))

        // When
        val pieceAvailable = state.isPieceAvailable(move)

        // Then
        assertTrue(pieceAvailable)
    }

    @Test
    fun puzzle_piece_is_not_available_to_make_the_move() {
        // Given
        val piece1 = PuzzlePieceFactory.build1()
        val piece2 = PuzzlePieceFactory.build2()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece1)
        val area = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 1)
        val state = PuzzleState(area, mutableListOf(piece2))

        // When
        val pieceAvailable = state.isPieceAvailable(move)

        // Then
        assertFalse(pieceAvailable)
    }

    @Test
    fun place_puzzle_piece() {
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
    }

    @Test
    fun available_moves_when_only_piece_0_is_available() {
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