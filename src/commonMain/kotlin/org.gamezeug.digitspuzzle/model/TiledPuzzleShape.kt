package org.gamezeug.digitspuzzle.model

open class TiledPuzzleShape(private val rows: Array<PuzzleRow>) {
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

class PuzzleRow(val tiles: Array<Tile>) {
    val size = tiles.size
}
