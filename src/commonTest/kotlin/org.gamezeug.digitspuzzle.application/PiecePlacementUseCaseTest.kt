package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PiecePlacementUseCaseTest {

    @Test
    fun `isValidPiecePlacement, valid, empty area`() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val area = PuzzleAreaFactory.buildPuzzleArea(5, 1)
        val state = PuzzleState(area, mutableListOf(piece))

        // When
        val validPlacement = PiecePlacementUseCase().isValidPiecePlacement(move, state)

        // Then
        assertTrue(validPlacement)
    }

    @Test
    fun `isValidPiecePlacement, invalid, tiles not disjunctive`() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val replacement = TileReplacement(PuzzleAreaCoordinate(0, 3), fullTile())
        val area = PuzzleAreaFactory.buildPuzzleArea(5, 1).replaceTiles(replacement)
        val state = PuzzleState(area, mutableListOf(piece))

        // When
        val validPlacement = PiecePlacementUseCase().isValidPiecePlacement(move, state)

        // Then
        assertFalse(validPlacement)
    }

    @Test
    fun `isValidPiecePlacement, valid, tiles not disjunctive, x-offset`() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(1, 0), piece)
        val replacement = TileReplacement(PuzzleAreaCoordinate(0, 3), fullTile())
        val area = PuzzleAreaFactory.buildPuzzleArea(5, 2).replaceTiles(replacement)
        val state = PuzzleState(area, mutableListOf(piece))

        // When
        val validPlacement = PiecePlacementUseCase().isValidPiecePlacement(move, state)

        // Then
        assertTrue(validPlacement)
    }

    @Test
    fun `placePiece, valid, area with edges`() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val area = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 1)
        val state = PuzzleState(area, mutableListOf(piece))

        // When
        PiecePlacementUseCase().placePiece(move, state)

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
        assertEquals(expected, state.area.toString())
    }

}