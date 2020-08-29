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
    fun `area 1x1`() {
        val expected = """
            [   ]
            [   ]
            [   ]
        """.trimIndent()
        assertEquals(expected, PuzzleAreaFactory.buildPuzzleArea(1, 1).toString())
    }

    @Test
    fun `area 2x3`() {
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
    fun `build puzzle area with edges`() {
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
    fun `replace tile of empty 1x1 area`() {
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
    fun `replace multiple tiles of an empty 2x3 area`() {
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
    fun `has 1x1 area room for 1x1`() {
        val area = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        assertTrue(area.hasRoomFor(area, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun `has 1x1 area room for 1x2`() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(1, 2)
        assertFalse(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun `has 1x1 area room for 2x1`() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(1, 1)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(2, 1)
        assertFalse(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 0)))
    }

    @Test
    fun `has 2x2 area room for 1x2 with 1 y-offset`() {
        val containerArea = PuzzleAreaFactory.buildPuzzleArea(2, 2)
        val newArea = PuzzleAreaFactory.buildPuzzleArea(1, 2)
        assertTrue(containerArea.hasRoomFor(newArea, PuzzleAreaCoordinate(0, 1)))
    }

    @Test
    fun `getBlankAreaMap for 5x3`() {
        val blankAreaMap = testArea.getBlankAreaMap()
        assertEquals(4, blankAreaMap[PuzzleAreaCoordinate(0, 0)])
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(0, 2)])
        assertEquals(1, blankAreaMap[PuzzleAreaCoordinate(4, 0)])
        assertEquals(4, blankAreaMap[PuzzleAreaCoordinate(3, 1)])
        assertEquals(4, blankAreaMap.size)
    }

    @Test
    fun `getBlankAreaMap breach test`() {
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
    fun `getNumberOfFilledTiles for 5x3`() {
        assertEquals(11, testArea.getNumberOfFilledTiles())
    }


    @Test
    fun `isValidPiecePlacement, valid, empty area`() {
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
    fun `isValidPiecePlacement, invalid, tiles not disjoint`() {
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
    fun `isValidPiecePlacement, valid, tiles not disjoint, x-offset`() {
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