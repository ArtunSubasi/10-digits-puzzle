package org.gamezeug.digitspuzzle.domain

data class PuzzlePiece(val name: String, val area: PuzzleArea) {

    fun rotateBy(rotation: Rotation): PuzzlePiece {
        return when (rotation) {
            Rotation.NO_ROTATION -> this
            Rotation.ROTATE_90_DEGREES_CLOCKWISE -> rotate90DegreesClockwise()
            Rotation.ROTATE_180_DEGREES -> rotate180Degrees()
            Rotation.ROTATE_270_DEGREES_CLOCKWISE -> rotate270DegreesClockwise()
        }
    }

    fun rotate90DegreesClockwise(): PuzzlePiece {
        val newNumberOfColumns = area.numberOfRows
        val newNumberOfRows = area.numberOfColumns
        val newRows = List(newNumberOfRows) {
            y -> List(newNumberOfColumns) {
                x -> area.getTile(y, area.numberOfRows - 1 - x).rotate90DegreesClockwise()
            }
        }
        return PuzzlePiece(name, PuzzleArea(newRows.map { PuzzleRow(it) }))
    }

    fun rotate180Degrees(): PuzzlePiece {
        return rotate90DegreesClockwise().rotate90DegreesClockwise()
    }

    fun rotate270DegreesClockwise(): PuzzlePiece {
        return rotate180Degrees().rotate90DegreesClockwise()
    }

    fun mirrorBy(mirroring: Mirroring): PuzzlePiece {
        return when (mirroring) {
            Mirroring.NO_MIRRORING -> this
            Mirroring.MIRROR_HORIZONTALLY -> mirrorHorizontally()
        }
    }

    fun mirrorHorizontally(): PuzzlePiece {
        val newRows = List(area.numberOfRows) {
            y -> List(area.numberOfColumns) {
            x -> area.getTile(area.numberOfColumns - 1 - x, y).mirrorHorizontally()
        }
        }
        return PuzzlePiece(name, PuzzleArea(newRows.map { PuzzleRow(it) }))
    }

    override fun toString() = area.toString()
}

/**
 * Reads and constructs PuzzlePieces from CSV in format such as:
 * XXXX,XXXX,XXXX
 * X := "F" | "T" | "B" | "L" | "R" | " "
 */
object PuzzlePieceFactory {

    private val csvDigit0 = """
        BR  ,F  ,LB
        F   ,   ,F
        T   ,   ,TRB
        F   ,   ,F
        TR  ,F  ,LT
    """.trimIndent()

    private val csvDigit1 = """
        B
        F
        TBL
        F
        T
    """.trimIndent()

    private val csvDigit2 = """
        R   ,F  ,LB
            ,   ,F
        BR  ,F  ,LT
        F   ,   ,
        TR  ,F  ,L
    """.trimIndent()

    private val csvDigit3 = """
        R   ,F  ,LB
            ,   ,F
        R   ,F  ,LTB
            ,   ,F
        R   ,F  ,LT
    """.trimIndent()

    private val csvDigit4 = """
        B   ,   ,B
        F   ,   ,F
        TRB ,F  ,LT
        F   ,   ,
        T   ,   ,
    """.trimIndent()

    private val csvDigit6 = """
        BR  ,F  ,L
        F   ,   ,
        TRB ,F  ,LB
        F   ,   ,F
        TR  ,F  ,LT
    """.trimIndent()

    private val csvDigit7 = """
        R   ,F  ,LB
            ,   ,F
            ,   ,TRB
            ,   ,F
            ,   ,T
    """.trimIndent()

    private val csvDigit8 = """
        BR  ,F  ,LB
        F   ,   ,F
        TRB ,F  ,LTB
        F   ,   ,F
        TR  ,F  ,LT
    """.trimIndent()

    fun build0(): PuzzlePiece = buildFromCsv('0', csvDigit0)
    fun build1(): PuzzlePiece = buildFromCsv('1', csvDigit1)
    fun build2(): PuzzlePiece = buildFromCsv('2', csvDigit2)
    fun build3(): PuzzlePiece = buildFromCsv('3', csvDigit3)
    fun build4(): PuzzlePiece = buildFromCsv('4', csvDigit4)
    fun build5(): PuzzlePiece = buildFromCsv('5', csvDigit2).mirrorHorizontally()
    fun build6(): PuzzlePiece = buildFromCsv('6', csvDigit6)
    fun build7(): PuzzlePiece = buildFromCsv('7', csvDigit7)
    fun build8(): PuzzlePiece = buildFromCsv('8', csvDigit8)
    fun build9(): PuzzlePiece = buildFromCsv('9', csvDigit6).rotate180Degrees()

    fun buildAll() = listOf(
        build0(),
        build1(),
        build2(),
        build3(),
        build4(),
        build5(),
        build6(),
        build7(),
        build8(),
        build9()
    )

    fun buildFromCsv(charToPrint: Char, csv: String): PuzzlePiece {
        val area = PuzzleAreaFactory.buildFromCsv(charToPrint, csv)
        val puzzlePiece = PuzzlePiece(charToPrint.toString(), area)
        println("Puzzle piece created:\n $puzzlePiece")
        return puzzlePiece
    }

}
