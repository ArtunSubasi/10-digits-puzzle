package org.gamezeug.digitspuzzle.domain

import org.gamezeug.digitspuzzle.application.PiecePlacementUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleStateTest {

    @Test
    fun `create initial puzzle state`() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf(PuzzlePieceFactory.build1()))
        assertEquals(11, state.area.numberOfColumns)
        assertEquals(9, state.area.numberOfRows)
        assertEquals(1, state.availablePieces.size)
        assertEquals(0, state.usedPieces.size)
        assertEquals(0, state.moves.size)
        assertFalse(state.isSolved())
    }

    @Test
    fun `isSolved, positive`() {
        val state = PuzzleStateFactory.createInitialPuzzleState(listOf())
        assertTrue(state.isSolved())
    }

    @Test
    fun `isPieceAvailable, positive`() {
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
    fun `isPieceAvailable, negative`() {
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

}