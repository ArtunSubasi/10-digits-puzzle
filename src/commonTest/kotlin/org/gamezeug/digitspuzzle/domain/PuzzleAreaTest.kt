package org.gamezeug.digitspuzzle.domain

import kotlin.test.Test
import kotlin.test.assertEquals

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
        val replacement2 = TileReplacement(PuzzleAreaCoordinate(1, 1), Tile.Builder().withRightSegment().build())

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

}