package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TileTest {

    @Test
    fun two_empty_tiles_are_disjoint() {
        assertTrue(emptyTile().isDisjoint(emptyTile()))
    }

    @Test
    fun an_empty_tile_is_disjoint_with_a_full_tile() {
        assertTrue(emptyTile().isDisjoint(fullTile()))
    }

    @Test
    fun two_half_full_tiles_with_displaced_segments_are_disjoint() {
        val tileWithTopAndRightSegments = Tile(topSegment = 'X', rightSegment = 'X')
        val tileWithBottomAndLeftSegments = Tile(bottomSegment = 'X', leftSegment = 'X')
        assertTrue(tileWithTopAndRightSegments.isDisjoint(tileWithBottomAndLeftSegments))
    }

    @Test
    fun two_full_tiles_are_not_disjoint() {
        assertFalse(fullTile().isDisjoint(fullTile()))
    }

    @Test
    fun rotating_a_tile_by_90_degrees_clockwise_shifts_its_segments_clockwise() {
        val tile = Tile(topSegment = '1', leftSegment = '2')
        val expected = Tile(topSegment = '2', rightSegment = '1')
        assertEquals(expected, tile.rotate90DegreesClockwise())
    }

    @Test
    fun mirroring_a_tile_horizontally_switches_its_left_and_right_segments() {
        val tile = Tile(topSegment = '1', leftSegment = '2')
        val expected = Tile(topSegment = '1', rightSegment = '2')
        assertEquals(expected, tile.mirrorHorizontally())
    }

    @Test
    fun an_empty_tile_is_represented_with_3_lines_where_each_line_contains_brackets_and_3_whitespaces_for_segments() {
        val expected = """
            [   ]
            [   ]
            [   ]
        """.trimIndent()
        assertEquals(expected, emptyTile().toString())
    }

    @Test
    fun a_full_tile_is_represented_with_3_lines_where_each_line_contains_brackets_and_Xs_for_all_segments() {
        val expected = """
            [ X ]
            [X X]
            [ X ]
        """.trimIndent()
        assertEquals(expected, fullTile().toString())
    }

    @Test
    fun a_custom_tile_is_represented_with_3_lines_where_each_line_contains_brackets_and_chars_for_filled_segments() {
        val expected = """
            [ X ]
            [  2]
            [   ]
        """.trimIndent()
        assertEquals(expected, Tile(topSegment = 'X', rightSegment = '2').toString())
    }

    @Test
    fun an_empty_tile_has_only_empty_segments() {
        assertTrue(emptyTile().hasAnyEmptySegment())
        assertTrue(emptyTile().hasEmptyLeftSegment())
        assertTrue(emptyTile().hasEmptyTopSegment())
        assertTrue(emptyTile().hasEmptyRightSegment())
        assertTrue(emptyTile().hasEmptyBottomSegment())
        assertEquals(4, emptyTile().getNumberOfEmptySegments())
    }

    @Test
    fun a_full_tile_does_not_have_any_empty_segments() {
        assertFalse(fullTile().hasAnyEmptySegment())
        assertFalse(fullTile().hasEmptyLeftSegment())
        assertFalse(fullTile().hasEmptyTopSegment())
        assertFalse(fullTile().hasEmptyRightSegment())
        assertFalse(fullTile().hasEmptyBottomSegment())
        assertEquals(0, fullTile().getNumberOfEmptySegments())
    }

}