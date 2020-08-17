package org.gamezeug.digitspuzzle.domain

/**
 * Represents a tile of the puzzle board. Each tile itself is divided into 4 segments (left, top, right, bottom)
 * which may be occupied separately. If a segment is not occupied, it contains the empty space char: ' '
 *
 * <pre>
 * [ X ]
 * [X X]
 * [ X ]
 * </pre>
 */
data class Tile(
        val leftSegment: Char = ' ',
        val topSegment: Char = ' ',
        val rightSegment: Char = ' ',
        val bottomSegment: Char = ' '
) {

    fun isDisjoint(other: Tile): Boolean {
        return (this.leftSegment == ' ' || other.leftSegment == ' ')
                && (this.topSegment == ' ' || other.topSegment == ' ')
                && (this.rightSegment == ' ' || other.rightSegment == ' ')
                && (this.bottomSegment == ' ' || other.bottomSegment == ' ')
    }

    fun merge(other: Tile): Tile {
        return Tile(
                leftSegment = if (this.leftSegment != ' ') this.leftSegment else other.leftSegment,
                topSegment = if (this.topSegment != ' ') this.topSegment else other.topSegment,
                rightSegment = if (this.rightSegment != ' ') this.rightSegment else other.rightSegment,
                bottomSegment = if (this.bottomSegment != ' ') this.bottomSegment else other.bottomSegment
        )
    }

    fun rotate90DegreesClockwise(): Tile {
        return Tile(
                leftSegment = bottomSegment,
                topSegment = leftSegment,
                rightSegment = topSegment,
                bottomSegment = rightSegment
        )
    }

    fun mirrorHorizontally(): Tile {
        return Tile(
                leftSegment = rightSegment,
                topSegment = topSegment,
                rightSegment = leftSegment,
                bottomSegment = bottomSegment
        )
    }

    override fun toString(): String {
        return toStringFirstLine() + "\n" + toStringSecondLine() + "\n" + toStringThirdLine()
    }

    fun toStringFirstLine(): String {
        return "[ $topSegment ]"
    }

    fun toStringSecondLine(): String {
        return "[$leftSegment $rightSegment]"
    }

    fun toStringThirdLine(): String {
        return "[ $bottomSegment ]"
    }
}

object TileFactory {
    fun createFromTileDescription(tileDescription: String, charToPrint: Char): Tile {
        var left = ' '; var top = ' '; var right = ' '; var bottom = ' '
        if (tileDescription.contains("F")) return fullTile(charToPrint)
        if (tileDescription.contains("L")) left = charToPrint
        if (tileDescription.contains("T")) top = charToPrint
        if (tileDescription.contains("R")) right = charToPrint
        if (tileDescription.contains("B")) bottom = charToPrint
        return Tile(left, top, right, bottom)
    }
}

fun emptyTile() = Tile()

fun fullTile(charToPrint: Char = 'X'): Tile {
    return Tile(charToPrint, charToPrint, charToPrint, charToPrint)
}