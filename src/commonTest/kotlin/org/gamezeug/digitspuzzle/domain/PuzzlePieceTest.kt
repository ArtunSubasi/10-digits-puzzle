package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class PuzzlePieceTest {

    @Test
    fun build1() {
        val expected = """
            [   ]
            [   ]
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
            [   ]
            [   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory.build1()

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

        val puzzlePiece = PuzzlePieceFactory.build2()

        assertEquals("2", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun buildFromFile() {
        val testPiece = PuzzlePieceFactory.buildFromFile('2', "testPiece.csv")
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

    @Test
    fun `rotate90DegreesClockwise Piece 1`() {
        val expected = """
            [   ][ 1 ][ 1 ][ 1 ][   ]
            [  1][1 1][1 1][1 1][1  ]
            [   ][ 1 ][   ][ 1 ][   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory.build1().rotate90DegreesClockwise()

        assertEquals("1", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun `rotate180Degrees Piece 1`() {
        val expected = """
            [   ]
            [   ]
            [ 1 ]
            [ 1 ]
            [1 1]
            [ 1 ]
            [ 1 ]
            [  1]
            [ 1 ]
            [ 1 ]
            [1 1]
            [ 1 ]
            [ 1 ]
            [   ]
            [   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory.build1().rotate180Degrees()

        assertEquals("1", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun `rotate270DegreesClockwise Piece 1`() {
        val expected = """
            [   ][ 1 ][   ][ 1 ][   ]
            [  1][1 1][1 1][1 1][1  ]
            [   ][ 1 ][ 1 ][ 1 ][   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory.build1().rotate270DegreesClockwise()

        assertEquals("1", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun `mirrorHorizontally Piece 2`() {
        val expected = """
            [   ][ 2 ][   ]
            [  2][2 2][2  ]
            [ 2 ][ 2 ][   ]
            [ 2 ][   ][   ]
            [2 2][   ][   ]
            [ 2 ][   ][   ]
            [ 2 ][ 2 ][   ]
            [  2][2 2][2  ]
            [   ][ 2 ][ 2 ]
            [   ][   ][ 2 ]
            [   ][   ][2 2]
            [   ][   ][ 2 ]
            [   ][ 2 ][ 2 ]
            [  2][2 2][2  ]
            [   ][ 2 ][   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory.build2().mirrorHorizontally()

        assertEquals("2", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

}