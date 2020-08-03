package org.gamezeug.digitspuzzle.model

import kotlin.test.Test
import kotlin.test.assertEquals

class PuzzleBoardTest {

    @Test
    fun `toString 1x1`() {
        val expected = """
            [   ]
            [   ]
            [   ]
        """.trimIndent()
        assertEquals(expected, PuzzleBoardFactory().buildPuzzleBoard(1, 1).toString())
    }

    @Test
    fun `toString 2x3`() {
        val expected = """
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
        """.trimIndent()
        assertEquals(expected, PuzzleBoardFactory().buildPuzzleBoard(2, 3).toString())
    }

    @Test
    fun buildPuzzleBoard() {
        val numberOfRows = 2
        val numberOfColumns = 3

        val puzzleBoard = PuzzleBoardFactory().buildPuzzleBoard(numberOfRows, numberOfColumns)

        assertEquals(numberOfRows, puzzleBoard.numberOfRows)
        assertEquals(numberOfColumns, puzzleBoard.numberOfColumns)
    }

}