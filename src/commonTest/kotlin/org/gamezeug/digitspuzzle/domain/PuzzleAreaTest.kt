package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PuzzleAreaTest {

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

}