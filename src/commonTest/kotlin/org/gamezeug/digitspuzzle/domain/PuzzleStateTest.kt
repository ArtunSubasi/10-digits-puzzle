package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class PuzzleStateTest {

    @Test
    fun `create initial puzzle state`() {
        val state = PuzzleStateFactory.createInitialPuzzleState(mutableListOf(PuzzlePieceFactory.build1()))

        assertEquals(11, state.area.numberOfColumns)
        assertEquals(9, state.area.numberOfRows)
        assertEquals(1, state.availablePieces.size)
        assertEquals(0, state.usedPieces.size)
        assertEquals(0, state.moves.size)
    }

}