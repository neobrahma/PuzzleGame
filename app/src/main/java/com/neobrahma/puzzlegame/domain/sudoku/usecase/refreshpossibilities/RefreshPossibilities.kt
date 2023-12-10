package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

interface RefreshPossibilities {
    operator fun invoke(sudokuGrid : SudokuGrid, indexValue : Int, value: Int)
}