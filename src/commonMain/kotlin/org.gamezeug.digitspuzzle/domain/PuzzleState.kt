package org.gamezeug.digitspuzzle.domain

class PuzzleState(
        var area: PuzzleArea,
        val availablePieces: MutableList<PuzzlePiece>
) {
    val moves: MutableList<Move> = mutableListOf()
    val usedPieces: MutableList<PuzzlePiece> = mutableListOf()

    override fun toString() = area.toString()
}

object PuzzleStateFactory {

    fun createInitialPuzzleState(availablePieces: MutableList<PuzzlePiece>): PuzzleState {
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(9, 11)
        return PuzzleState(initialArea, availablePieces)
    }

}

