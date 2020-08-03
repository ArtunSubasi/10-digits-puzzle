package org.gamezeug.digitspuzzle.model

/**
 * Represents a tile of the puzzle board. Each tile itself is divided into 4 segments (left, top, right, bottom)
 * which may be occupied separately.
 *
 * Internally, the segment occupation is represented as a bit mask (flags). 1 is occupied, 0 is free.
 * 1. bit - left
 * 2. bit - top
 * 3. bit - right
 * 4. bit - bottom
 */
data class Tile(
    private val segmentOccupation: Int = 0
) {

    private val leftOnlyMask    = 0b1000
    private val topOnlyMask     = 0b0100
    private val rightOnlyMask   = 0b0010
    private val bottomOnlyMask  = 0b0001

    fun hasLeft() = segmentOccupation and leftOnlyMask == leftOnlyMask
    fun hasTop() = segmentOccupation and topOnlyMask == topOnlyMask
    fun hasRight() = segmentOccupation and rightOnlyMask == rightOnlyMask
    fun hasBottom() = segmentOccupation and bottomOnlyMask == bottomOnlyMask

    override fun toString(): String {
        val leftMark = getMark(hasLeft())
        val topMark = getMark(hasTop())
        val rightMark = getMark(hasRight())
        val bottomMark = getMark(hasBottom())
        return """
            [ $topMark ]
            [$leftMark $rightMark]
            [ $bottomMark ]
        """.trimIndent()
    }

    private fun getMark(hasSegment: Boolean) = if (hasSegment) "X" else " "
}
