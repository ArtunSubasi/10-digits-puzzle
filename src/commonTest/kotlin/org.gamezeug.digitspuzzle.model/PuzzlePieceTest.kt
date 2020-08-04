package org.gamezeug.digitspuzzle.model

import kotlin.test.Test
import kotlin.test.assertEquals

class PuzzlePieceTest {

    @Test
    fun build1() {
        val expected = """
            [   ]
            [   ]
            [ X ]
            [ X ]
            [X X]
            [ X ]
            [ X ]
            [X  ]
            [ X ]
            [ X ]
            [X X]
            [ X ]
            [ X ]
            [   ]
            [   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory().build1()

        assertEquals("1", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun build2() {
        val expected = """
            [   ][ 2 ][   ]
            [  2][2 2][2  ]
            [   ][ 2 ][ 2 ]
            [   ][   ][ 2 ]
            [   ][   ][2 2]
            [   ][   ][ 2 ]
            [   ][ 2 ][ 2 ]
            [  2][2 2][2  ]
            [ 2 ][ 2 ][   ]
            [ 2 ][   ][   ]
            [2 2][   ][   ]
            [ 2 ][   ][   ]
            [ 2 ][ 2 ][   ]
            [  2][2 2][2  ]
            [   ][ 2 ][   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory().build2()

        assertEquals("2", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun buildFromFile() {
        val testPiece = PuzzlePieceFactory().buildFromFile('2', "testPiece.csv")
        val expected = """
            [ 2 ][   ]
            [2 2][   ]
            [ 2 ][   ]
            [   ][   ]
            [2  ][  2]
            [   ][   ]
            [   ][ 2 ]
            [   ][   ]
            [ 2 ][   ]
            [   ][ 2 ]
            [2 2][   ]
            [   ][ 2 ]
            [   ][   ]
            [   ][2 2]
            [   ][ 2 ]
        """.trimIndent()
        assertEquals(expected, testPiece.toString())
    }

}