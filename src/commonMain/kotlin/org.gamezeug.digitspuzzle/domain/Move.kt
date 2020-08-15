package org.gamezeug.digitspuzzle.domain

/**
 * A move that a player can do to put a puzzle piece to the puzzle board.
 */
data class Move(val coordinate: PuzzleAreaCoordinate, val puzzlePiece: PuzzlePiece)