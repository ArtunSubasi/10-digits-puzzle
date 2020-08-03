package org.gamezeug.digitspuzzle.piecereader

import kotlin.test.Test
import kotlin.test.assertEquals

class PieceReaderTest {

    @Test
    fun read() {
        val testPiece = PieceReader().readFromFile('X', "testPiece.csv")
        val expected = """
            [ X ][   ]
            [X X][   ]
            [ X ][   ]
            [   ][   ]
            [X  ][  X]
            [   ][   ]
            [   ][ X ]
            [   ][   ]
            [ X ][   ]
            [   ][ X ]
            [X X][   ]
            [   ][ X ]
            [   ][   ]
            [   ][X X]
            [   ][ X ]
        """.trimIndent()
        assertEquals(expected, testPiece.toString())
    }

}