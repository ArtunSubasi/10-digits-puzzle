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
        return (this.hasEmptyLeftSegment() || other.hasEmptyLeftSegment())
                && (this.hasEmptyTopSegment() || other.hasEmptyTopSegment())
                && (this.hasEmptyRightSegment() || other.hasEmptyRightSegment())
                && (this.hasEmptyBottomSegment() || other.hasEmptyBottomSegment())
    }

    fun merge(other: Tile): Tile {
        return Tile(
                leftSegment = if (hasEmptyLeftSegment()) other.leftSegment else this.leftSegment,
                topSegment = if (hasEmptyTopSegment()) other.topSegment else this.topSegment,
                rightSegment = if (hasEmptyRightSegment()) other.rightSegment else this.rightSegment,
                bottomSegment = if (hasEmptyBottomSegment()) other.bottomSegment else this.bottomSegment
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

    fun hasAnyEmptySegment(): Boolean {
        return hasEmptyLeftSegment() || hasEmptyTopSegment() || hasEmptyRightSegment() || hasEmptyBottomSegment()
    }
    fun hasEmptyLeftSegment() = leftSegment == ' '
    fun hasEmptyTopSegment() = topSegment == ' '
    fun hasEmptyRightSegment() = rightSegment == ' '
    fun hasEmptyBottomSegment() = bottomSegment == ' '
    fun hasAnyFilledSegment() = getNumberOfEmptySegments() != 4

    fun getNumberOfEmptySegments(): Int {
        var numberOfEmptySegments = 0
        if (hasEmptyLeftSegment()) numberOfEmptySegments++
        if (hasEmptyTopSegment()) numberOfEmptySegments++
        if (hasEmptyRightSegment()) numberOfEmptySegments++
        if (hasEmptyBottomSegment()) numberOfEmptySegments++
        return numberOfEmptySegments
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
        if (tileDescription.contains("F")) return fullTile(charToPrint)
        val left = if (tileDescription.contains("L")) charToPrint else ' '
        val top = if (tileDescription.contains("T")) charToPrint else ' '
        val right = if (tileDescription.contains("R")) charToPrint else ' '
        val bottom = if (tileDescription.contains("B")) charToPrint else ' '
        return Tile(left, top, right, bottom)
    }
}

fun emptyTile() = Tile()

fun fullTile(charToPrint: Char = 'X'): Tile {
    return Tile(charToPrint, charToPrint, charToPrint, charToPrint)
}