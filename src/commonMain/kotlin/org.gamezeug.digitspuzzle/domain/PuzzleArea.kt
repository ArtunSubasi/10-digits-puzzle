package org.gamezeug.digitspuzzle.domain

/**
 * An immutable tiled area consisting of rows and columns.
 */
data class PuzzleArea(val rows: List<PuzzleRow>) {
    val numberOfRows = rows.size
    val numberOfColumns = rows[0].size

    fun replaceTiles(vararg replacements: TileReplacement): PuzzleArea {
        val mutableArea = MutableList(numberOfRows) { y -> MutableList(numberOfColumns) { x -> rows[y].tiles[x] } }
        replacements.forEach { mutableArea[it.coordinate.y][it.coordinate.x] = it.newTile }
        return PuzzleArea(mutableArea.map { PuzzleRow(it) })
    }

    fun getTile(x: Int, y: Int): Tile {
        return rows[y].tiles[x]
    }

    fun hasRoomFor(newArea: PuzzleArea, newAreaCoordinate: PuzzleAreaCoordinate): Boolean {
        val wideEnough = this.numberOfColumns >= (newArea.numberOfColumns + newAreaCoordinate.x)
        val tallEnough = this.numberOfRows >= (newArea.numberOfRows + newAreaCoordinate.y)
        return wideEnough && tallEnough
    }

    fun isValidPiecePlacement(move: Move): Boolean {
        val effectiveArea = move.getEffectiveArea()
        if (!hasRoomFor(effectiveArea, move.coordinate)) return false
        for ((rowIndex, row) in effectiveArea.rows.withIndex()) {
            for ((colIndex, newTile) in row.tiles.withIndex()) {
                val tileInThePuzzle = getTile(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                if (!newTile.isDisjoint(tileInThePuzzle)) return false
            }
        }
        return true
    }

    fun getNumberOfFilledTiles(): Int {
        var filledTiles = 0
        for (y in 0 until numberOfRows) {
            for (x in 0 until numberOfColumns) {
                if (getTile(x, y).hasAnyFilledSegment()) {
                    filledTiles++
                }
            }
        }
        return filledTiles
    }

    /**
     * Calculates a map of blank areas of coordinates to the number of tiles containing blank segments.
     * This is only an approximate implementation.
     * If a tile contains multiple, not connected blank segments, it still counts.
     * The coordinate refers to the top left corner of the blank area.
     * For example, for a is 1x1 area and which contains a tile with three blank segments,
     * this return would return a map with a single entry "1x1" to "3".
     */
    fun getBlankAreaMap(): Map<PuzzleAreaCoordinate, Int> {
        val foundBlankTiles = mutableSetOf<PuzzleAreaCoordinate>()
        val blankAreaMap: MutableMap<PuzzleAreaCoordinate, Int> = mutableMapOf()
        for (y in 0 until numberOfRows) {
            for (x in 0 until numberOfColumns) {
                val coordinate = PuzzleAreaCoordinate(x, y)
                if (!foundBlankTiles.contains(coordinate)) {
                    val tileNavigator = TileNavigator(this, coordinate)
                    val blankTiles = mutableSetOf<PuzzleAreaCoordinate>()
                    fillNeighborBlankTilesRecursive(tileNavigator, blankTiles)
                    if (blankTiles.isNotEmpty()) {
                        foundBlankTiles.addAll(blankTiles)
                        blankAreaMap[coordinate] = blankTiles.size
                    }
                }
            }
        }
        return blankAreaMap
    }

    private fun fillNeighborBlankTilesRecursive(
            tileNavigator: TileNavigator,
            blankTiles: MutableSet<PuzzleAreaCoordinate>
    ) {
        if (blankTiles.contains(tileNavigator.coordinate) || !tileNavigator.getTile().hasAnyEmptySegment()) {
            return
        }
        blankTiles.add(tileNavigator.coordinate)
        if (tileNavigator.hasLeftTile()
                && tileNavigator.getTile().hasEmptyLeftSegment()
                && tileNavigator.left().getTile().hasEmptyRightSegment()) {
            fillNeighborBlankTilesRecursive(tileNavigator.left(), blankTiles)
        }
        if (tileNavigator.hasBottomTile()
                && tileNavigator.getTile().hasEmptyBottomSegment()
                && tileNavigator.bottom().getTile().hasEmptyTopSegment()) {
            fillNeighborBlankTilesRecursive(tileNavigator.bottom(), blankTiles)
        }
        if (tileNavigator.hasRightTile()
                && tileNavigator.getTile().hasEmptyRightSegment()
                && tileNavigator.right().getTile().hasEmptyLeftSegment()) {
            fillNeighborBlankTilesRecursive(tileNavigator.right(), blankTiles)
        }
    }

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

// TODO check if there is a better way to define factories, such as by using companion objects, or plain constuctors
object PuzzleAreaFactory {
    fun buildPuzzleArea(numberOfRows: Int, numberOfColumns: Int): PuzzleArea {
        val puzzleRows = MutableList(numberOfRows) { _ -> PuzzleRowFactory.buildPuzzleRow(numberOfColumns) }
        return PuzzleArea(puzzleRows)
    }
    fun buildPuzzleAreaWithEdges(numberOfRows: Int, numberOfColumns: Int): PuzzleArea {
        val emptyArea = buildPuzzleArea(numberOfRows, numberOfColumns)
        return emptyArea.replaceTiles(
                TileReplacement(PuzzleAreaCoordinate(0, 0), buildTopLeftTile()),
                TileReplacement(PuzzleAreaCoordinate(numberOfColumns - 1, 0), buildTopRightTile()),
                TileReplacement(PuzzleAreaCoordinate(0, numberOfRows - 1), buildBottomLeftTile()),
                TileReplacement(PuzzleAreaCoordinate(numberOfColumns - 1, numberOfRows - 1), buildBottomRightTile())
        )
    }
    private fun buildTopLeftTile() = Tile(topSegment = 'X', leftSegment = 'X')
    private fun buildTopRightTile() = Tile(topSegment = 'X', rightSegment = 'X')
    private fun buildBottomLeftTile() = Tile(bottomSegment = 'X', leftSegment = 'X')
    private fun buildBottomRightTile() = Tile(bottomSegment = 'X', rightSegment = 'X')

    fun buildFromCsv(charToPrint: Char, csv: String): PuzzleArea {
        val rows = mutableListOf<PuzzleRow>()
        for (line in csv.lines()) {
            val tiles = line.split(",")
                    .map { TileFactory.createFromTileDescription(it, charToPrint) }
                    .toList()
            rows.add(PuzzleRow(tiles))
        }
        return PuzzleArea(rows)
    }
}

class TileNavigator(private val area: PuzzleArea, val coordinate: PuzzleAreaCoordinate) {
    fun hasLeftTile() = coordinate.x > 0
    fun hasTopTile() = coordinate.y > 0
    fun hasRightTile() = coordinate.x + 1 < area.numberOfColumns
    fun hasBottomTile() = coordinate.y + 1 < area.numberOfRows
    fun getTile() = area.getTile(coordinate.x , coordinate.y)
    fun left(): TileNavigator {
        val newCoordinate = PuzzleAreaCoordinate(coordinate.x - 1, coordinate.y)
        return TileNavigator(area, newCoordinate)
    }
    fun top(): TileNavigator {
        val newCoordinate = PuzzleAreaCoordinate(coordinate.x, coordinate.y - 1)
        return TileNavigator(area, newCoordinate)
    }
    fun right(): TileNavigator {
        val newCoordinate = PuzzleAreaCoordinate(coordinate.x + 1, coordinate.y)
        return TileNavigator(area, newCoordinate)
    }
    fun bottom(): TileNavigator {
        val newCoordinate = PuzzleAreaCoordinate(coordinate.x, coordinate.y + 1)
        return TileNavigator(area, newCoordinate)
    }
    fun carriageReturn(): TileNavigator {
        val newCoordinate = PuzzleAreaCoordinate(0, coordinate.y + 1)
        return TileNavigator(area, newCoordinate)
    }
}

data class PuzzleAreaCoordinate(val x: Int, val y: Int)

data class TileReplacement(val coordinate: PuzzleAreaCoordinate, val newTile: Tile)

data class PuzzleRow(val tiles: List<Tile>) {
    val size = tiles.size
}

object PuzzleRowFactory {
    fun buildPuzzleRow(numberOfTiles: Int): PuzzleRow = PuzzleRow(List(numberOfTiles) { _ -> emptyTile() })
}
