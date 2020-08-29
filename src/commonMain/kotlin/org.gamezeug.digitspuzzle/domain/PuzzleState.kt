package org.gamezeug.digitspuzzle.domain

data class PuzzleState(
        val area: PuzzleArea,
        val availablePieces: List<PuzzlePiece>,
        val moves: List<Move> = listOf(),
        val usedPieces: List<PuzzlePiece> = listOf()
) {
    fun isSolved() = availablePieces.isEmpty()
    override fun toString() = area.toString()
}

object PuzzleStateFactory {

    fun createInitialPuzzleState(availablePieces: List<PuzzlePiece>): PuzzleState {
        val initialArea = PuzzleAreaFactory.buildPuzzleAreaWithEdges(9, 11)
        return PuzzleState(initialArea, availablePieces)
    }

}

