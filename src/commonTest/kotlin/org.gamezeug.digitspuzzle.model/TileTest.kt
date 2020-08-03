package org.gamezeug.digitspuzzle.model

import com.soywiz.krypto.fillRandomBytes
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TileTest {

    @Test
    fun `hasSegments - empty tile`() {
        assertFalse(emptyTile().hasLeftSegment())
        assertFalse(emptyTile().hasTopSegment())
        assertFalse(emptyTile().hasRightSegment())
        assertFalse(emptyTile().hasBottomSegment())
    }

    @Test
    fun `hasSegments - full tile`() {
        assertTrue(fullTile().hasLeftSegment())
        assertTrue(fullTile().hasTopSegment())
        assertTrue(fullTile().hasRightSegment())
        assertTrue(fullTile().hasBottomSegment())
    }

    @Test
    fun `hasSegments - left segment only`() {
        val tile = Tile.Builder().withLeftSegment().build()
        assertTrue(tile.hasLeftSegment())
        assertFalse(tile.hasTopSegment())
        assertFalse(tile.hasRightSegment())
        assertFalse(tile.hasBottomSegment())
    }

    @Test
    fun `hasSegments - top segment only`() {
        val tile = Tile.Builder().withTopSegment().build()
        assertFalse(tile.hasLeftSegment())
        assertTrue(tile.hasTopSegment())
        assertFalse(tile.hasRightSegment())
        assertFalse(tile.hasBottomSegment())
    }

    @Test
    fun `hasSegments - right segment only`() {
        val tile = Tile.Builder().withRightSegment().build()
        assertFalse(tile.hasLeftSegment())
        assertFalse(tile.hasTopSegment())
        assertTrue(tile.hasRightSegment())
        assertFalse(tile.hasBottomSegment())
    }

    @Test
    fun `hasSegments - bottom segment only`() {
        val tile = Tile.Builder().withBottomSegment().build()
        assertFalse(tile.hasLeftSegment())
        assertFalse(tile.hasTopSegment())
        assertFalse(tile.hasRightSegment())
        assertTrue(tile.hasBottomSegment())
    }

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
        val tileWithTopAndRightSegments = Tile.Builder().withTopSegment().withRightSegment().build()
        val tileWithBottomAndLeftSegments = Tile.Builder().withBottomSegment().withLeftSegment().build()
        assertTrue(tileWithTopAndRightSegments.isDisjoint(tileWithBottomAndLeftSegments))
    }

    @Test
    fun `isDisjoint - both full`() {
        assertFalse(fullTile().isDisjoint(fullTile()))
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
        assertEquals(expected, Tile.Builder().withTopSegment().withRightSegment().build().toString())
    }

    @Test
    fun `toString with bottom segment only with a custom char to print`() {
        val expected = """
            [   ]
            [   ]
            [ 2 ]
        """.trimIndent()
        assertEquals(expected, Tile.Builder().withBottomSegment().withCharToPrint('2').build().toString())
    }

}