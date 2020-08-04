package org.gamezeug.digitspuzzle.domain

const val leftSegmentOnlyMask   = 0b1000
const val topSegmentOnlyMask    = 0b0100
const val rightSegmentOnlyMask  = 0b0010
const val bottomSegmentOnlyMask = 0b0001

/**
 * Represents a tile of the puzzle board. Each tile itself is divided into 4 segments (left, top, right, bottom)
 * which may be occupied separately.
 *
 * <pre>
 * [ X ]
 * [X X]
 * [ X ]
 * </pre>
 *
 * Internally, the segment occupation is represented as a bit mask (flags). 1 is occupied, 0 is free.
 * 1. bit - left
 * 2. bit - top
 * 3. bit - right
 * 4. bit - bottom
 */
data class Tile(val segmentMask: Int, val charToPrint: Char) {

    fun hasLeftSegment() = segmentMask and leftSegmentOnlyMask == leftSegmentOnlyMask
    fun hasTopSegment() = segmentMask and topSegmentOnlyMask == topSegmentOnlyMask
    fun hasRightSegment() = segmentMask and rightSegmentOnlyMask == rightSegmentOnlyMask
    fun hasBottomSegment() = segmentMask and bottomSegmentOnlyMask == bottomSegmentOnlyMask

    fun isDisjoint(other: Tile): Boolean {
        return (this.segmentMask and other.segmentMask) == 0
    }

    override fun toString(): String {
        return toStringFirstLine() + "\n" + toStringSecondLine() + "\n" + toStringThirdLine()
    }

    fun toStringFirstLine(): String {
        val topMark = getMark(hasTopSegment())
        return "[ $topMark ]"
    }

    fun toStringSecondLine(): String {
        val leftMark = getMark(hasLeftSegment())
        val rightMark = getMark(hasRightSegment())
        return "[$leftMark $rightMark]"
    }

    fun toStringThirdLine(): String {
        val bottomMark = getMark(hasBottomSegment())
        return "[ $bottomMark ]"
    }

    private fun getMark(hasSegment: Boolean) = if (hasSegment) charToPrint else ' '

    class Builder(
            private var segmentMask: Int = 0,
            private var charToPrint: Char = 'X'
    ) {
        fun build() = Tile(segmentMask, charToPrint)
        fun withLeftSegment(): Builder {
            segmentMask = (segmentMask or leftSegmentOnlyMask)
            return this
        }
        fun withRightSegment(): Builder {
            segmentMask = (segmentMask or rightSegmentOnlyMask)
            return this
        }
        fun withTopSegment(): Builder {
            segmentMask = (segmentMask or topSegmentOnlyMask)
            return this
        }
        fun withBottomSegment(): Builder {
            segmentMask = (segmentMask or bottomSegmentOnlyMask)
            return this
        }
        fun withCharToPrint(charToPrint: Char): Builder {
            this.charToPrint = charToPrint
            return this
        }
    }
}

fun emptyTile(charToPrint: Char = 'X') = Tile.Builder().withCharToPrint(charToPrint).build()

fun fullTile(charToPrint: Char = 'X'): Tile {
    return Tile.Builder()
            .withLeftSegment()
            .withTopSegment()
            .withRightSegment()
            .withBottomSegment()
            .withCharToPrint(charToPrint)
            .build()
}