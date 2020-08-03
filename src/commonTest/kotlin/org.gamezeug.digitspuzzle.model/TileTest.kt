package org.gamezeug.digitspuzzle.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TileTest {

    @Test
    fun `hasSegments - empty tile`() {
        val tile = Tile(0b0000)
        assertFalse(tile.hasLeft())
        assertFalse(tile.hasTop())
        assertFalse(tile.hasRight())
        assertFalse(tile.hasBottom())
    }

    @Test
    fun `hasSegments - full tile`() {
        val tile = Tile(0b1111)
        assertTrue(tile.hasLeft())
        assertTrue(tile.hasTop())
        assertTrue(tile.hasRight())
        assertTrue(tile.hasBottom())
    }

    @Test
    fun `hasSegments - left segment only`() {
        val tile = Tile(0b1000)
        assertTrue(tile.hasLeft())
        assertFalse(tile.hasTop())
        assertFalse(tile.hasRight())
    }

    @Test
    fun `hasSegments - top segment only`() {
        val tile = Tile(0b0100)
        assertFalse(tile.hasLeft())
        assertTrue(tile.hasTop())
        assertFalse(tile.hasRight())
        assertFalse(tile.hasBottom())
    }

    @Test
    fun `hasSegments - right segment only`() {
        val tile = Tile(0b0010)
        assertFalse(tile.hasLeft())
        assertFalse(tile.hasTop())
        assertTrue(tile.hasRight())
        assertFalse(tile.hasBottom())
    }

    @Test
    fun `hasSegments - bottom segment only`() {
        val tile = Tile(0b0001)
        assertFalse(tile.hasLeft())
        assertFalse(tile.hasTop())
        assertFalse(tile.hasRight())
        assertTrue(tile.hasBottom())
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