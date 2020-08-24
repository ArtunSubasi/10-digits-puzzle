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
    fun build3() {
        val expected = """
            [   ][ 3 ][   ]
            [  3][3 3][3  ]
            [   ][ 3 ][ 3 ]
            [   ][   ][ 3 ]
            [   ][   ][3 3]
            [   ][   ][ 3 ]
            [   ][ 3 ][ 3 ]
            [  3][3 3][3  ]
            [   ][ 3 ][ 3 ]
            [   ][   ][ 3 ]
            [   ][   ][3 3]
            [   ][   ][ 3 ]
            [   ][ 3 ][ 3 ]
            [  3][3 3][3  ]
            [   ][ 3 ][   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory.build3()

        assertEquals("3", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun build4() {
        val expected = """
            [   ][   ][   ]
            [   ][   ][   ]
            [ 4 ][   ][ 4 ]
            [ 4 ][   ][ 4 ]
            [4 4][   ][4 4]
            [ 4 ][   ][ 4 ]
            [ 4 ][ 4 ][ 4 ]
            [  4][4 4][4  ]
            [ 4 ][ 4 ][   ]
            [ 4 ][   ][   ]
            [4 4][   ][   ]
            [ 4 ][   ][   ]
            [ 4 ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
        """.trimIndent()

        val puzzlePiece = PuzzlePieceFactory.build4()

        assertEquals("4", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun build5() {
        val expected = """
            [   ][ 5 ][   ]
            [  5][5 5][5  ]
            [ 5 ][ 5 ][   ]
            [ 5 ][   ][   ]
            [5 5][   ][   ]
            [ 5 ][   ][   ]
            [ 5 ][ 5 ][   ]
            [  5][5 5][5  ]
            [   ][ 5 ][ 5 ]
            [   ][   ][ 5 ]
            [   ][   ][5 5]
            [   ][   ][ 5 ]
            [   ][ 5 ][ 5 ]
            [  5][5 5][5  ]
            [   ][ 5 ][   ]
        """.trimIndent()
        val puzzlePiece = PuzzlePieceFactory.build5()

        assertEquals("5", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun build6() {
        val expected = """
            [   ][ 6 ][   ]
            [  6][6 6][6  ]
            [ 6 ][ 6 ][   ]
            [ 6 ][   ][   ]
            [6 6][   ][   ]
            [ 6 ][   ][   ]
            [ 6 ][ 6 ][   ]
            [  6][6 6][6  ]
            [ 6 ][ 6 ][ 6 ]
            [ 6 ][   ][ 6 ]
            [6 6][   ][6 6]
            [ 6 ][   ][ 6 ]
            [ 6 ][ 6 ][ 6 ]
            [  6][6 6][6  ]
            [   ][ 6 ][   ]
        """.trimIndent()
        val puzzlePiece = PuzzlePieceFactory.build6()

        assertEquals("6", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun build7() {
        val expected = """
            [   ][ 7 ][   ]
            [  7][7 7][7  ]
            [   ][ 7 ][ 7 ]
            [   ][   ][ 7 ]
            [   ][   ][7 7]
            [   ][   ][ 7 ]
            [   ][   ][ 7 ]
            [   ][   ][  7]
            [   ][   ][ 7 ]
            [   ][   ][ 7 ]
            [   ][   ][7 7]
            [   ][   ][ 7 ]
            [   ][   ][ 7 ]
            [   ][   ][   ]
            [   ][   ][   ]
        """.trimIndent()
        val puzzlePiece = PuzzlePieceFactory.build7()

        assertEquals("7", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun build8() {
        val expected = """
            [   ][ 8 ][   ]
            [  8][8 8][8  ]
            [ 8 ][ 8 ][ 8 ]
            [ 8 ][   ][ 8 ]
            [8 8][   ][8 8]
            [ 8 ][   ][ 8 ]
            [ 8 ][ 8 ][ 8 ]
            [  8][8 8][8  ]
            [ 8 ][ 8 ][ 8 ]
            [ 8 ][   ][ 8 ]
            [8 8][   ][8 8]
            [ 8 ][   ][ 8 ]
            [ 8 ][ 8 ][ 8 ]
            [  8][8 8][8  ]
            [   ][ 8 ][   ]
        """.trimIndent()
        val puzzlePiece = PuzzlePieceFactory.build8()

        assertEquals("8", puzzlePiece.name)
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
    fun rotateBy() {
        val puzzlePiece = PuzzlePieceFactory.build2()
        assertEquals(puzzlePiece, puzzlePiece.rotateBy(Rotation.NO_ROTATION))
        assertEquals(puzzlePiece.rotate90DegreesClockwise().toString(),
                puzzlePiece.rotateBy(Rotation.ROTATE_90_DEGREES_CLOCKWISE).toString())
        assertEquals(puzzlePiece.rotate180Degrees().toString(),
                puzzlePiece.rotateBy(Rotation.ROTATE_180_DEGREES).toString())
        assertEquals(puzzlePiece.rotate270DegreesClockwise().toString(),
                puzzlePiece.rotateBy(Rotation.ROTATE_270_DEGREES_CLOCKWISE).toString())
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

    @Test
    fun mirrorBy() {
        val puzzlePiece = PuzzlePieceFactory.build2()
        assertEquals(puzzlePiece.toString(), puzzlePiece.mirrorBy(Mirroring.NO_MIRRORING).toString())
        assertEquals(puzzlePiece.mirrorHorizontally().toString(),
                puzzlePiece.mirrorBy(Mirroring.MIRROR_HORIZONTALLY).toString())
    }

}