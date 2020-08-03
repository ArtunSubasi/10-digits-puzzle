package org.gamezeug.digitspuzzle.model

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
data class Tile(
        private val segmentOccupationMask: Int = 0,
        private val charToPrint: Char = 'X'
) {
    private val leftSegmentOnlyMask    = 0b1000
    private val topSegmentOnlyMask     = 0b0100
    private val rightSegmentOnlyMask   = 0b0010
    private val bottomSegmentOnlyMask  = 0b0001

    fun hasLeftSegment() = segmentOccupationMask and leftSegmentOnlyMask == leftSegmentOnlyMask
    fun hasTopSegment() = segmentOccupationMask and topSegmentOnlyMask == topSegmentOnlyMask
    fun hasRightSegment() = segmentOccupationMask and rightSegmentOnlyMask == rightSegmentOnlyMask
    fun hasBottomSegment() = segmentOccupationMask and bottomSegmentOnlyMask == bottomSegmentOnlyMask

    fun isDisjoint(other: Tile): Boolean {
        return (this.segmentOccupationMask and other.segmentOccupationMask) == 0
    }

    override fun toString(): String {
        val leftMark = getMark(hasLeftSegment())
        val topMark = getMark(hasTopSegment())
        val rightMark = getMark(hasRightSegment())
        val bottomMark = getMark(hasBottomSegment())
        return """
            [ $topMark ]
            [$leftMark $rightMark]
            [ $bottomMark ]
        """.trimIndent()
    }

    private fun getMark(hasSegment: Boolean) = if (hasSegment) charToPrint else ' '

}
