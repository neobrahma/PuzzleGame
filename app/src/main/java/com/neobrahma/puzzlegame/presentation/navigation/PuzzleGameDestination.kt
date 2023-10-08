package com.neobrahma.puzzlegame.presentation.navigation

interface PuzzleGameDestination {
    val destination: String
}

object HomepageDestination : PuzzleGameDestination {
    override val destination: String
        get() = "homepage"
}

object SudokuHomepageDestination : PuzzleGameDestination {
    override val destination: String
        get() = "sudoku_homepage"
}

object SudokuGridDestination : PuzzleGameDestination {
    override val destination: String
        get() = "sudoku_grid"
}