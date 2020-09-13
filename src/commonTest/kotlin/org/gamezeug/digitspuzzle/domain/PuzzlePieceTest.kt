package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class PuzzlePieceTest {

    @Test
    fun piece_0_has_the_correct_area_when_built() {
        val expected = """
            [   ][ 0 ][   ]
            [  0][0 0][0  ]
            [ 0 ][ 0 ][ 0 ]
            [ 0 ][   ][ 0 ]
            [0 0][   ][0 0]
            [ 0 ][   ][ 0 ]
            [ 0 ][   ][ 0 ]
            [   ][   ][  0]
            [   ][   ][ 0 ]
            [ 0 ][   ][ 0 ]
            [0 0][   ][0 0]
            [ 0 ][   ][ 0 ]
            [ 0 ][ 0 ][ 0 ]
            [  0][0 0][0  ]
            [   ][ 0 ][   ]
        """.trimIndent()
        val puzzlePiece = PuzzlePieceFactory.build0()

        assertEquals("0", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun piece_1_has_the_correct_area_when_built() {
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
    fun piece_2_has_the_correct_area_when_built() {
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
    fun piece_3_has_the_correct_area_when_built() {
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
    fun piece_4_has_the_correct_area_when_built() {
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
    fun piece_5_has_the_correct_area_when_built() {
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
    fun piece_6_has_the_correct_area_when_built() {
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
    fun piece_7_has_the_correct_area_when_built() {
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
    fun piece_8_has_the_correct_area_when_built() {
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
    fun piece_9_has_the_correct_area_when_built() {
        val expected = """
            [   ][ 9 ][   ]
            [  9][9 9][9  ]
            [ 9 ][ 9 ][ 9 ]
            [ 9 ][   ][ 9 ]
            [9 9][   ][9 9]
            [ 9 ][   ][ 9 ]
            [ 9 ][ 9 ][ 9 ]
            [  9][9 9][9  ]
            [   ][ 9 ][ 9 ]
            [   ][   ][ 9 ]
            [   ][   ][9 9]
            [   ][   ][ 9 ]
            [   ][ 9 ][ 9 ]
            [  9][9 9][9  ]
            [   ][ 9 ][   ]
        """.trimIndent()
        val puzzlePiece = PuzzlePieceFactory.build9()

        assertEquals("9", puzzlePiece.name)
        assertEquals(expected, puzzlePiece.toString())
    }

    @Test
    fun rotating_a_piece_by_the_rotation_enum_has_the_same_effect_as_rotating_it_by_a_direct_method_call() {
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
    fun rotating_piece_1_by_90_degrees_clockwise_leads_to_the_correct_area() {
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
    fun rotating_piece_1_by_180_degrees_leads_to_the_correct_area() {
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
    fun rotating_piece_1_by_270_degrees_clockwise_leads_to_the_correct_area() {
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
    fun mirroring_the_piece_2_leads_to_the_correct_area() {
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
    fun mirroring_a_piece_by_the_mirroring_enum_has_the_same_effect_as_mirroring_it_by_a_direct_method_call() {
        val puzzlePiece = PuzzlePieceFactory.build2()
        assertEquals(puzzlePiece.toString(), puzzlePiece.mirrorBy(Mirroring.NO_MIRRORING).toString())
        assertEquals(puzzlePiece.mirrorHorizontally().toString(),
                puzzlePiece.mirrorBy(Mirroring.MIRROR_HORIZONTALLY).toString())
    }

}