package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleAreaTest {

    private val csvBlankArea = """
            B   ,   ,BR  ,F   ,LB
            F   ,   ,F   ,    ,F
            TR  ,F  ,LT  ,    ,T
            """.trimIndent()
    private val testArea = PuzzleAreaFactory.buildFromCsv('X', csvBlankArea)

    @Test
    fun build_1x1_area() {
        val expected = """
            [   ]
            [   ]
            [   ]
        """.trimIndent()
        assertEquals(expected, PuzzleAreaFactory.buildPuzzleArea(1, 1).toString())
    }

    @Test
    fun build_2x3_area() {
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
    fun build_2x3_area_with_edges() {
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
    fun replace_tile_of_empty_1x1_area() {
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
    fun replace_multiple_tiles_of_an_empty_2x3_area() {
        val numberOfRows = 2
        val numberOfColumns = 3
        val emptyArea = PuzzleAreaFactory.buildPuzzleArea(numberOfRows, numberOfColumns)
        val replacement1 = TileReplacement(PuzzleAreaCoordinate(0, 0), fullTile())
        val replacement2 = TileReplacement(PuzzleAreaCoordinate(1, 1), Tile(rightSegment = 'X'))

        val newArea = emptyArea.replaceTiles(replacement1, replacement2)

        val expected = """
            [ X ][   ][   ]
            [X X][   ][   ]
            [ X ][   ][   ]
            [   ][   ][   ]
            [   ][  X][   ]
            [   ][   ][   ]
        """.trimIndent()
        assertEquals(expected, newArea.toString())
    }

    @Test
    fun has_1x1_area_room_for_1x1_area() {
        val area = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        assertTrue(area.hasRoomFor(area, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun has_1x1_area_room_for_1x2_area() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(1, 2)
        assertFalse(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun has_1x1_area_room_for_2x1_area() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(2, 1)
        assertFalse(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun has_2x2_area_room_for_1x2_with_1_y_offset() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(2, 2)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(1, 2)
        assertTrue(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 1)))
    }

    @Test
    fun blank_area_map_of_5x3_area() {
        val blankAreaMap = testArea.getBlankAreaMap()
        assertEquals(4, blankAreaMap[PuzzleAreaCoordinate(0, 0)])
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(0, 2)])
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(4, 0)])
        assertEquals(4, blankAreaMap[PuzzleAreaCoordinate(3, 1)])
        assertEquals(4, blankAreaMap.size)
    }

    @Test
    fun blank_area_map_edge_cases() {
        val testAreaCsv = """
            F   ,F  ,LB ,
            F   ,   ,F  ,
            T   ,   ,TRB,
            F   ,   ,F  ,
            TR  ,F  ,LT ,
        """.trimIndent()
        val blankAreaMap = PuzzleAreaFactory.buildFromCsv('X', testAreaCsv).getBlankAreaMap()
        assertEquals(5, blankAreaMap[PuzzleAreaCoordinate(1, 1)])
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(0, 4)])
        assertEquals(7, blankAreaMap[PuzzleAreaCoordinate(2, 0)])
        assertEquals(3, blankAreaMap.size)
    }

    @Test
    fun number_of_filled_tiles_of_5x3_area() {
        assertEquals(11, testArea.getNumberOfFilledTiles())
    }


    @Test
    fun piece_placement_into_an_empty_area_should_be_valid() {
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
    fun piece_placement_with_disjoint_tiles_should_be_invalid() {
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
    fun piece_placement_with_offset_should_be_valid() {
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