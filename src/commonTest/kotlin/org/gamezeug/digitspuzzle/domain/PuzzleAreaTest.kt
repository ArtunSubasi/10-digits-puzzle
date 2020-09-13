package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleAreaTest {

    @Test
    fun a_new_1x1_area_has_only_empty_segments() {
        val expected = """
            [   ]
            [   ]
            [   ]
        """.trimIndent()
        assertEquals(expected, PuzzleAreaFactory.buildPuzzleArea(1, 1).toString())
    }

    @Test
    fun a_new_2x3_area_has_only_empty_segments() {
        val numberOfRows = 2
        val numberOfColumns = 3
        val expected = """
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
            [   ][   ][   ]
        """.trimIndent()

        val area = PuzzleAreaFactory.buildPuzzleArea(numberOfRows, numberOfColumns)

        assertEquals(expected, area.toString())
    }

    @Test
    fun a_new_2x2_area_with_edges_has_only_filled_segments_at_the_edges() {
        val numberOfRows = 2
        val numberOfColumns = 2
        val expected = """
            [ X ][ X ]
            [X  ][  X]
            [   ][   ]
            [   ][   ]
            [X  ][  X]
            [ X ][ X ]
        """.trimIndent()

        val puzzleBoard = PuzzleAreaFactory.buildPuzzleAreaWithEdges(numberOfRows, numberOfColumns)

        assertEquals(expected, puzzleBoard.toString())
    }

    @Test
    fun an_empty_1x1_area_gets_full_if_its_only_tile_is_replaced_by_a_full_tile() {
        val emptyArea = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        val replacements = TileReplacement(PuzzleAreaCoordinate(0, 0), fullTile())

        val newArea = emptyArea.replaceTiles(replacements)

        val expected = """
            [ X ]
            [X X]
            [ X ]
        """.trimIndent()
        assertEquals(expected, newArea.toString())
    }

    @Test
    fun an_empty_2x3_area_gets_modified_if_its_two_tiles_are_replaced() {
        val numberOfRows = 2
        val numberOfColumns = 3
        val emptyArea = PuzzleAreaFactory.buildPuzzleArea(numberOfRows, numberOfColumns)
        val replacement1 = TileReplacement(PuzzleAreaCoordinate(0, 0), fullTile())
        val replacement2 = TileReplacement(PuzzleAreaCoordinate(1, 1), Tile(rightSegment = 'Y'))

        val newArea = emptyArea.replaceTiles(replacement1, replacement2)

        val expected = """
            [ X ][   ][   ]
            [X X][   ][   ]
            [ X ][   ][   ]
            [   ][   ][   ]
            [   ][  Y][   ]
            [   ][   ][   ]
        """.trimIndent()
        assertEquals(expected, newArea.toString())
    }

    @Test
    fun an_empty_1x1_area_has_room_for_a_1x1_area() {
        val area = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        assertTrue(area.hasRoomFor(area, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun an_empty_1x1_area_does_not_have_room_for_a_2x1_area() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(1, 2)
        assertFalse(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun an_empty_1x1_area_does_not_have_room_for_a_1x2_area() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(2, 1)
        assertFalse(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun an_empty_2x2_area_has_room_for_a_2x1_area_if_placed_with_1_y_offset() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(2, 2)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(1, 2)
        assertTrue(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 1)))
    }

    @Test
    fun an_empty_1x1_area_has_a_1_blank_area_of_size_1() {
        val blankAreaMap = PuzzleAreaFactory.buildPuzzleArea(1, 1).getBlankAreaMap()
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(0, 0)])
        assertEquals(1, blankAreaMap.size)
    }

    @Test
    fun a_1x3_area_has_a_1_blank_area_of_size_3_if_all_tiles_contains_bottom_segments() {
        val blankAreaMap = PuzzleAreaFactory.buildFromCsv('X', "B,B,B").getBlankAreaMap()
        assertEquals(3, blankAreaMap[PuzzleAreaCoordinate(0, 0)])
        assertEquals(1, blankAreaMap.size)
    }

    @Test
    fun a_rotated_5_shaped_area_has_4_blank_areas() {
        val csvBlankArea = """
            B   ,   ,BR  ,F   ,LB
            F   ,   ,F   ,    ,F
            TR  ,F  ,LT  ,    ,T
            """.trimIndent()
        val blankAreaMap = PuzzleAreaFactory.buildFromCsv('X', csvBlankArea).getBlankAreaMap()
        assertEquals(4, blankAreaMap[PuzzleAreaCoordinate(0, 0)]) // top left corner, starting with B
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(0, 2)]) // bottom left corner (TR) is enclosed
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(4, 0)]) // top right corner (LB) is enclosed
        assertEquals(4, blankAreaMap[PuzzleAreaCoordinate(3, 1)]) // center, starting with F
        assertEquals(4, blankAreaMap.size)
    }

    @Test
    fun blank_areas_are_counted_separately_even_if_a_blank_tile_acts_as_border() { // in this case the TRB tile
        val testAreaCsv = """
            F   ,F  ,LB ,
            F   ,   ,F  ,
            T   ,   ,TRB,
            F   ,   ,F  ,
            TR  ,F  ,LT ,
        """.trimIndent()
        val blankAreaMap = PuzzleAreaFactory.buildFromCsv('X', testAreaCsv).getBlankAreaMap()
        assertEquals(5, blankAreaMap[PuzzleAreaCoordinate(1, 1)]) // the blank area to left of TRB
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(0, 4)]) // bottom left corner is enclosed
        assertEquals(7, blankAreaMap[PuzzleAreaCoordinate(2, 0)]) // the blank area to the right of TRB
        assertEquals(3, blankAreaMap.size)
    }

    @Test
    fun a_rotated_5_shaped_area_has_11_filled_tiles() {
        val csvBlankArea = """
            B   ,   ,BR  ,F   ,LB
            F   ,   ,F   ,    ,F
            TR  ,F  ,LT  ,    ,T
            """.trimIndent()
        assertEquals(11, PuzzleAreaFactory.buildFromCsv('X', csvBlankArea).getNumberOfFilledTiles())
    }


    @Test
    fun piece_placement_into_an_empty_area_is_valid() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val area = PuzzleAreaFactory.buildPuzzleArea(5, 1)

        // When
        val validPlacement = area.isValidPiecePlacement(move)

        // Then
        assertTrue(validPlacement)
    }

    @Test
    fun piece_placement_with_disjoint_tiles_is_invalid() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(0, 0), piece)
        val replacement = TileReplacement(PuzzleAreaCoordinate(0, 3), fullTile())
        val area = PuzzleAreaFactory.buildPuzzleArea(5, 1).replaceTiles(replacement)

        // When
        val validPlacement = area.isValidPiecePlacement(move)

        // Then
        assertFalse(validPlacement)
    }

    @Test
    fun piece_placement_with_offset_is_valid() {
        // Given
        val piece = PuzzlePieceFactory.build1()
        val move = Move(PuzzleAreaCoordinate(1, 0), piece)
        val replacement = TileReplacement(PuzzleAreaCoordinate(0, 3), fullTile())
        val area = PuzzleAreaFactory.buildPuzzleArea(5, 2).replaceTiles(replacement)

        // When
        val validPlacement = area.isValidPiecePlacement(move)

        // Then
        assertTrue(validPlacement)
    }

}