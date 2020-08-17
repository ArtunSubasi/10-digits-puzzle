package org.gamezeug.digitspuzzle.domain

import kotlin.experimental.and
import kotlin.experimental.or

const val leftSegmentOnlyMask: Byte   = 0b1000
const val topSegmentOnlyMask: Byte    = 0b0100
const val rightSegmentOnlyMask: Byte  = 0b0010
const val bottomSegmentOnlyMask: Byte = 0b0001

/**
 * Represents a tile of the puzzle board. Each tile itself is divided into 4 segments (left, top, right, bottom)
 * which may be occupied separately. If a segment is not occupied, it contains the empty space char: ' '
 *
 * <pre>
 * [ X ]
 * [X X]
 * [ X ]
 * </pre>
 *
 * Internally, the segment mask is represented as a byte. 0 is free. 1 is occupied.
 * 1. bit - left
 * 2. bit - top
 * 3. bit - right
 * 4. bit - bottom
 */
data class Tile(
        val leftSegment: Char = ' ',
        val topSegment: Char = ' ',
        val rightSegment: Char = ' ',
        val bottomSegment: Char = ' '
) {

    fun isDisjoint(other: Tile): Boolean {
        val zeroByteMask: Byte = 0
        return (this.getSegmentMask() and other.getSegmentMask()) == zeroByteMask
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

    private fun getSegmentMask(): Byte {
        var segmentMask: Byte = 0x0000
        if (leftSegment != ' ') segmentMask = segmentMask or leftSegmentOnlyMask
        if (topSegment != ' ') segmentMask = segmentMask or topSegmentOnlyMask
        if (rightSegment != ' ') segmentMask = segmentMask or rightSegmentOnlyMask
        if (bottomSegment != ' ') segmentMask = segmentMask or bottomSegmentOnlyMask
        return segmentMask
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