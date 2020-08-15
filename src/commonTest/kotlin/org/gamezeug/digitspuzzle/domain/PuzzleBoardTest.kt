package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class PuzzleBoardTest {

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

        val puzzleBoard = PuzzleBoardFactory.buildPuzzleBoardWithEdges(numberOfRows, numberOfColumns)

        assertEquals(expected, puzzleBoard.toString())
    }

}