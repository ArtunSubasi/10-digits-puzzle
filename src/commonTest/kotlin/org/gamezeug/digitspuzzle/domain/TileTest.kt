package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TileTest {

    @Test
    fun `isDisjoint - both empty`() {
        assertTrue(emptyTile().isDisjoint(emptyTile()))
    }

    @Test
    fun `isDisjoint - empty and full`() {
        assertTrue(emptyTile().isDisjoint(fullTile()))
    }

    @Test
    fun `isDisjoint - fifty fifty edge case`() {
        val tileWithTopAndRightSegments = Tile(topSegment = 'X', rightSegment = 'X')
        val tileWithBottomAndLeftSegments = Tile(bottomSegment = 'X', leftSegment = 'X')
        assertTrue(tileWithTopAndRightSegments.isDisjoint(tileWithBottomAndLeftSegments))
    }

    @Test
    fun `isDisjoint - both full`() {
        assertFalse(fullTile().isDisjoint(fullTile()))
    }

    @Test
    fun rotate90DegreesClockwise() {
        val tile = Tile(topSegment = '1', leftSegment = '2')
        val expected = Tile(topSegment = '2', rightSegment = '1')
        assertEquals(expected, tile.rotate90DegreesClockwise())
    }

    @Test
    fun mirrorHorizontally() {
        val tile = Tile(topSegment = '1', leftSegment = '2')
        val expected = Tile(topSegment = '1', rightSegment = '2')
        assertEquals(expected, tile.mirrorHorizontally())
    }

    @Test
    fun `toString with empty tile`() {
        val expected = """
            [   ]
            [   ]
            [   ]
        """.trimIndent()
        assertEquals(expected, emptyTile().toString())
    }

    @Test
    fun `toString with full tile`() {
        val expected = """
            [ X ]
            [X X]
            [ X ]
        """.trimIndent()
        assertEquals(expected, fullTile().toString())
    }

    @Test
    fun `toString with top and right segments only`() {
        val expected = """
            [ X ]
            [  X]
            [   ]
        """.trimIndent()
        assertEquals(expected, Tile(topSegment = 'X', rightSegment = 'X').toString())
    }

    @Test
    fun `toString with bottom segment only with a custom char to print`() {
        val expected = """
            [   ]
            [   ]
            [ 2 ]
        """.trimIndent()
        assertEquals(expected, Tile(bottomSegment = '2').toString())
    }

    @Test
    fun `toStringFirstLine with full tile`() {
        assertEquals("[ X ]", fullTile().toStringFirstLine())
    }

    @Test
    fun `toStringSecondLine with full tile`() {
        assertEquals("[X X]", fullTile().toStringSecondLine())
    }

    @Test
    fun `toStringThirdLine with full tile`() {
        assertEquals("[ X ]", fullTile().toStringThirdLine())
    }

}