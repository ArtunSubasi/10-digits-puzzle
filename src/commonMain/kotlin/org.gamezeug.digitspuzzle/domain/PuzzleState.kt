package org.gamezeug.digitspuzzle.domain

data class PuzzleState(
        val area: PuzzleArea,
        val availablePieces: List<PuzzlePiece>,
        val moves: List<Move> = listOf(),
        val usedPieces: List<PuzzlePiece> = listOf()
) {
    fun isSolved() = availablePieces.isEmpty()
    fun isPieceAvailable(move: Move)= availablePieces.contains(move.piece)

    fun placePiece(move: Move): PuzzleState {
        if (!isPieceAvailable(move)) {
            throw IllegalArgumentException("The move is not valid. The piece is not available.")
        }
        if (!move.canBePlacedOn(area)) {
            throw IllegalArgumentException("The move is not valid. The piece does not fit in.")
        }
        val tileReplacements = mutableListOf<TileReplacement>()

        for ((rowIndex, row) in move.getEffectiveArea().rows.withIndex()) {
            for ((colIndex, newTile) in row.tiles.withIndex()) {
                val tileInThePuzzle = area.getTile(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                val mergedTile = tileInThePuzzle.merge(newTile)
                val coordinate = PuzzleAreaCoordinate(colIndex + move.coordinate.x, rowIndex + move.coordinate.y)
                tileReplacements.add(TileReplacement(coordinate, mergedTile))
            }
        }

        return PuzzleState(
                area = area.replaceTiles(*tileReplacements.toTypedArray()),
                availablePieces = availablePieces.minus(move.piece),
                moves = moves.plus(move),
                usedPieces = usedPieces.plus(move.piece)
        )
    }

    /**
     * Get all available valid moves without duplicates. Warning: Incoming loop of loops!
     */
    fun getAvailableValidMoves(): List<Move> {
        val availableValidMoves = mutableListOf<Move>()
        for (coordinate in area.getAvailableCoordinates()) {
            for (piece in availablePieces) {
                for (rotation in Rotation.values()) {
                    for (mirroring in Mirroring.values()) {
                        val move = Move(coordinate, piece, rotation, mirroring)
                        if (move.canBePlacedOn(area)) {
                            availableValidMoves.add(move)
                        }
                    }
                }
            }
        }
        return availableValidMoves.toList()
    }

    /**
     * Check if puzzle state is a dead end: We can definitely say that it does not make sense to continue solving.
     */
    fun isDeadEnd(): Boolean {
        // Check if the puzzle contains a blank area which is too small for the smallest available piece
        val areaOfSmallestPiece = availablePieces.map { it.area.getNumberOfFilledTiles() }.min()!!
        val tooSmallBlankArea = area.getBlankAreaMap().values.find { it in 2..areaOfSmallestPiece }
        return tooSmallBlankArea != null
    }

    override fun toString() = area.toString()
}

object PuzzleStateFactory {

    fun createInitialPuzzleState(availablePieces: List<PuzzlePiece>): PuzzleState {
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(9, 11)
        return PuzzleState(initialArea, availablePieces)
    }

}

