package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.presentation.sudoku.FindOnePossibilityVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuData

interface RefreshPossibilities {
    operator fun invoke(
        sudokuData: SudokuData,
        indexValue: Int,
        value: Int,
        findOnePossibilityVisitor: FindOnePossibilityVisitor
    )
}