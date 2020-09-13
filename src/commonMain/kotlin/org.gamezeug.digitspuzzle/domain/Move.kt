package org.gamezeug.digitspuzzle.domain

/**
 * A move that a player can do to put a puzzle piece to the puzzle board.
 * If the piece gets mirrored and rotated, the mirroring applies first.
 */
data class Move(
        val coordinate: PuzzleAreaCoordinate,
        val piece: PuzzlePiece,
        val rotation: Rotation = Rotation.NO_ROTATION,
        val mirroring: Mirroring = Mirroring.NO_MIRRORING
) {
    fun getEffectiveArea(): PuzzleArea {
        return piece.mirrorBy(mirroring).rotateBy(rotation).area
    }

    fun canBePlacedOn(area: PuzzleArea): Boolean {
        val movesEffectiveArea = getEffectiveArea()
        if (!area.hasRoomFor(movesEffectiveArea, coordinate)) return false
        for ((rowIndex, row) in movesEffectiveArea.rows.withIndex()) {
            for ((colIndex, newTile) in row.tiles.withIndex()) {
                val tileInThePuzzle = area.getTile(colIndex + coordinate.x, rowIndex + coordinate.y)
                if (!newTile.isDisjoint(tileInThePuzzle)) return false
            }
        }
        return true
    }

    override fun toString(): String = getEffectiveArea().toString()
    override fun hashCode(): Int = getEffectiveArea().hashCode() * coordinate.hashCode()
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Move -> getEffectiveArea() == other.getEffectiveArea() && coordinate == other.coordinate
            else -> false
        }
    }
}

enum class Rotation {
    NO_ROTATION,
    ROTATE_90_DEGREES_CLOCKWISE,
    ROTATE_180_DEGREES,
    ROTATE_270_DEGREES_CLOCKWISE
}

enum class Mirroring {
    NO_MIRRORING,
    MIRROR_HORIZONTALLY
}