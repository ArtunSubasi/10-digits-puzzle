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
    fun buildPuzzleBoard() {
        val numberOfRows = 2
        val numberOfColumns = 3
        val expected = """
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
        """.trimIndent()

        val puzzleBoard = PuzzleBoardFactory().buildPuzzleBoard(numberOfRows, numberOfColumns)

        assertEquals(numberOfRows, puzzleBoard.numberOfRows)
        assertEquals(numberOfColumns, puzzleBoard.numberOfColumns)
        assertEquals(expected, puzzleBoard.toString())
    }

    @Test
    fun buildPuzzleBoardWithEdges() {
        val numberOfRows = 2
        val numberOfColumns = 2
        val expected = """
            [ X ][ X ]
            [X  ][  X]
            [   ][   ]
            [   ][   ]
            [X  ][  X]
            [ X ][ X ]
        """.trimIndent()

        val puzzleBoard = PuzzleBoardFactory().buildPuzzleBoardWithEdges(numberOfRows, numberOfColumns)

        assertEquals(expected, puzzleBoard.toString())
    }

}