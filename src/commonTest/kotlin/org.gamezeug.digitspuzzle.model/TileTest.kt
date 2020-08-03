package org.gamezeug.digitspuzzle.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TileTest {

    @Test
    fun `hasSegments - empty tile`() {
        val tile = Tile(0b0000)
        assertFalse(tile.hasLeftSegment())
        assertFalse(tile.hasTopSegment())
        assertFalse(tile.hasRightSegment())
        assertFalse(tile.hasBottomSegment())
    }

    @Test
    fun `hasSegments - full tile`() {
        val tile = Tile(0b1111)
        assertTrue(tile.hasLeftSegment())
        assertTrue(tile.hasTopSegment())
        assertTrue(tile.hasRightSegment())
        assertTrue(tile.hasBottomSegment())
    }

    @Test
    fun `hasSegments - left segment only`() {
        val tile = Tile(0b1000)
        assertTrue(tile.hasLeftSegment())
        assertFalse(tile.hasTopSegment())
        assertFalse(tile.hasRightSegment())
    }

    @Test
    fun `hasSegments - top segment only`() {
        val tile = Tile(0b0100)
        assertFalse(tile.hasLeftSegment())
        assertTrue(tile.hasTopSegment())
        assertFalse(tile.hasRightSegment())
        assertFalse(tile.hasBottomSegment())
    }

    @Test
    fun `hasSegments - right segment only`() {
        val tile = Tile(0b0010)
        assertFalse(tile.hasLeftSegment())
        assertFalse(tile.hasTopSegment())
        assertTrue(tile.hasRightSegment())
        assertFalse(tile.hasBottomSegment())
    }

    @Test
    fun `hasSegments - bottom segment only`() {
        val tile = Tile(0b0001)
        assertFalse(tile.hasLeftSegment())
        assertFalse(tile.hasTopSegment())
        assertFalse(tile.hasRightSegment())
        assertTrue(tile.hasBottomSegment())
    }

    @Test
    fun `isDisjoint - both empty`() {
        assertTrue(Tile().isDisjoint(Tile()))
    }

    @Test
    fun `isDisjoint - empty and full`() {
        assertTrue(Tile().isDisjoint(Tile(0b1111)))
    }

    @Test
    fun `isDisjoint - fifty fifty edge case`() {
        val tileWithTopAndRightSegments = Tile(0b0110)
        val tileWithBottomAndLeftSegments = Tile(0b1001)
        assertTrue(tileWithTopAndRightSegments.isDisjoint(tileWithBottomAndLeftSegments))
    }

    @Test
    fun `isDisjoint - both full`() {
        assertFalse(Tile(0b1111).isDisjoint(Tile(0b1111)))
    }

    @Test
    fun `toString with empty tile`() {
        val tile = Tile(0b0000)
        val expected = """
            [   ]
            [   ]
            [   ]
        """.trimIndent()
        assertEquals(expected, tile.toString())
    }

    @Test
    fun `toString with full tile`() {
        val tile = Tile(0b1111)
        val expected = """
            [ X ]
            [X X]
            [ X ]
        """.trimIndent()
        assertEquals(expected, tile.toString())
    }

    @Test
    fun `toString with top and right segments only`() {
        val tile = Tile(0b0110)
        val expected = """
            [ X ]
            [  X]
            [   ]
        """.trimIndent()
        assertEquals(expected, tile.toString())
    }

}