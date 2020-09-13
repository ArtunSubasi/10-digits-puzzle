package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TileTest {

    @Test
    fun two_empty_tiles_should_be_disjoint() {
        assertTrue(emptyTile().isDisjoint(emptyTile()))
    }

    @Test
    fun an_empty_tile_should_be_disjoint_with_a_full_tile() {
        assertTrue(emptyTile().isDisjoint(fullTile()))
    }

    @Test
    fun two_half_full_tiles_with_displaces_segments_should_be_disjoint() {
        val tileWithTopAndRightSegments = Tile(topSegment = 'X', rightSegment = 'X')
        val tileWithBottomAndLeftSegments = Tile(bottomSegment = 'X', leftSegment = 'X')
        assertTrue(tileWithTopAndRightSegments.isDisjoint(tileWithBottomAndLeftSegments))
    }

    @Test
    fun two_full_tiles_should_not_be_disjoint() {
        assertFalse(fullTile().isDisjoint(fullTile()))
    }

    @Test
    fun rotate_90_degrees_clockwise() {
        val tile = Tile(topSegment = '1', leftSegment = '2')
        val expected = Tile(topSegment = '2', rightSegment = '1')
        assertEquals(expected, tile.rotate90DegreesClockwise())
    }

    @Test
    fun mirror_horizontally() {
        val tile = Tile(topSegment = '1', leftSegment = '2')
        val expected = Tile(topSegment = '1', rightSegment = '2')
        assertEquals(expected, tile.mirrorHorizontally())
    }

    @Test
    fun toString_of_an_empty_tile() {
        val expected = """
            [   ]
            [   ]
            [   ]
        """.trimIndent()
        assertEquals(expected, emptyTile().toString())
    }

    @Test
    fun toString_of_a_fulltile() {
        val expected = """
            [ X ]
            [X X]
            [ X ]
        """.trimIndent()
        assertEquals(expected, fullTile().toString())
    }

    @Test
    fun toString_with_top_and_right_segments_only() {
        val expected = """
            [ X ]
            [  X]
            [   ]
        """.trimIndent()
        assertEquals(expected, Tile(topSegment = 'X', rightSegment = 'X').toString())
    }

    @Test
    fun toString_with_bottom_segment_only_with_a_custom_char_to_print() {
        val expected = """
            [   ]
            [   ]
            [ 2 ]
        """.trimIndent()
        assertEquals(expected, Tile(bottomSegment = '2').toString())
    }

    @Test
    fun toStringFirstLine_with_full_tile() {
        assertEquals("[ X ]", fullTile().toStringFirstLine())
    }

    @Test
    fun toStringSecondLine_with_full_tile() {
        assertEquals("[X X]", fullTile().toStringSecondLine())
    }

    @Test
    fun toStringThirdLine_with_full_tile() {
        assertEquals("[ X ]", fullTile().toStringThirdLine())
    }

    @Test
    fun an_empty_tile_should_have_empty_segments() {
        assertTrue(emptyTile().hasAnyEmptySegment())
        assertTrue(emptyTile().hasEmptyLeftSegment())
        assertTrue(emptyTile().hasEmptyTopSegment())
        assertTrue(emptyTile().hasEmptyRightSegment())
        assertTrue(emptyTile().hasEmptyBottomSegment())
        assertEquals(4, emptyTile().getNumberOfEmptySegments())
    }

    @Test
    fun a_full_tile_should_not_have_empty_segments() {
        assertFalse(fullTile().hasAnyEmptySegment())
        assertFalse(fullTile().hasEmptyLeftSegment())
        assertFalse(fullTile().hasEmptyTopSegment())
        assertFalse(fullTile().hasEmptyRightSegment())
        assertFalse(fullTile().hasEmptyBottomSegment())
        assertEquals(0, fullTile().getNumberOfEmptySegments())
    }

}