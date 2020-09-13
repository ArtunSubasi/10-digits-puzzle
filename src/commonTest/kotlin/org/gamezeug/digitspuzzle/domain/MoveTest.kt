package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MoveTest {

    @Test
    fun piece_1_can_be_placed_on_an_empty_1x5_area() {
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val area = PuzzleAreaFactory.buildPuzzleArea(5, 1)
        assertTrue(move.canBePlacedOn(area))
    }

    @Test
    fun piece_1_cannot_be_placed_on_a_1x5_area_containing_a_full_tile() {
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val replacement = TileReplacement(PuzzleAreaCoordinate(0, 3), fullTile())
        val area = PuzzleAreaFactory.buildPuzzleArea(5, 1).replaceTiles(replacement)
        assertFalse(move.canBePlacedOn(area))
    }

    @Test
    fun piece_1_can_be_placed_on_a_1x5_area_containing_disjoint_tiles() {
        // Given
        val areaCsv = """
            LRT
            
            R
            
            LRB
        """.trimIndent()
        val area = PuzzleAreaFactory.buildFromCsv('X', areaCsv)

        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)

        assertTrue(move.canBePlacedOn(area))
    }

}