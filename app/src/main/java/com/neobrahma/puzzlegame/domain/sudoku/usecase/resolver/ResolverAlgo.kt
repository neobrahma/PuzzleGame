package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver

import com.neobrahma.puzzlegame.presentation.sudoku.CountForVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

interface ResolverAlgo {
    operator fun invoke(sudokuGrid: SudokuGrid, countFor : CountForVisitor): ResolverAlgoResult
}

sealed class ResolverAlgoResult {
    data class FindOnePossibility(val text: String, val index: Int, val value: Int) :
        ResolverAlgoResult()

    object Nothing : ResolverAlgoResult()

}