package org.gamezeug.digitspuzzle.domain

/**
 * Represents a tiled area consisting of rows and columns.
 */
class PuzzleArea(val rows: List<PuzzleRow>) {
    val numberOfRows = rows.size
    val numberOfColumns = rows[0].size

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for ((index, row) in rows.withIndex()) {
            for (tile in row.tiles) {
                stringBuilder.append(tile.toStringFirstLine())
            }
            stringBuilder.append("\n")
            for (tile in row.tiles) {
                stringBuilder.append(tile.toStringSecondLine())
            }
            stringBuilder.append("\n")
            for (tile in row.tiles) {
                stringBuilder.append(tile.toStringThirdLine())
            }
            if (index != numberOfRows - 1) {
                stringBuilder.append("\n")
            }
        }
        return stringBuilder.toString()
    }
}

class PuzzleRow(val tiles: List<Tile>) {
    val size = tiles.size
}
