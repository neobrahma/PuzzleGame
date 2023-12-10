package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolve

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

interface ResolverAlgo {
    operator fun invoke(sudokuGrid: SudokuGrid) : ResolverAlgoResult
}

sealed class ResolverAlgoResult{
    data class FindOnePossibility(val text : String, val index : Int, val value : Int): ResolverAlgoResult()

    object Nothing : ResolverAlgoResult()

}