package org.gamezeug.digitspuzzle.application

import org.gamezeug.digitspuzzle.domain.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PiecePlacementUseCaseTest {

    @Test
    fun `placePiece, valid, area with edges`() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val area = PuzzleAreaFactory.buildPuzzleAreaWithEdges(5, 1)
        val state = PuzzleState(area, mutableListOf(piece))

        // When
        val newState = PiecePlacementUseCase().placePiece(move, state)

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

}