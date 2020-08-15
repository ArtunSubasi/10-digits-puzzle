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

    fun createInitialPuzzleState(): PuzzleState {
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(9, 11)
        val availablePieces = mutableListOf(PuzzlePieceFactory.build1())
        return PuzzleState(initialArea, availablePieces)
    }

}

