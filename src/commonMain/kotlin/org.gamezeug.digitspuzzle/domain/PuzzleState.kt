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
        if (!area.isValidPiecePlacement(move)) {
            TODO("exception handling, use isValidPiecePlacement? move it to another class for testability? do it somewhere else?")
        }
        if (!isPieceAvailable(move)) {
            TODO("nope nope nope")
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

    override fun toString() = area.toString()
}

object PuzzleStateFactory {

    fun createInitialPuzzleState(availablePieces: List<PuzzlePiece>): PuzzleState {
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(9, 11)
        return PuzzleState(initialArea, availablePieces)
    }

}

