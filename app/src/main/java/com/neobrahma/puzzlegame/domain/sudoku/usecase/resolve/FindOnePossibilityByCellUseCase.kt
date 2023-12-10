package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolve

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class FindOnePossibilityByCellUseCase(
    private val nextAlgo: ResolverAlgo? = null
) : ResolverAlgo {
    override fun invoke(sudokuGrid: SudokuGrid): ResolverAlgoResult {
        sudokuGrid.cells.forEachIndexed { indexCell, sudokuCell ->
            val possibilities = mutableListOf<Int>()
            sudokuCell.possibleValue.forEachIndexed { index, value ->
                if(value != 0){
                    possibilities.add(value*(index+1))
                }
            }
            if (possibilities.isNotEmpty() && possibilities.size == 1) {
                return ResolverAlgoResult.FindOnePossibility(
                    text = "find one possiblity",
                    index = indexCell,
                    value = possibilities[0]
                )
            }
        }
        return nextAlgo?.invoke(sudokuGrid) ?: ResolverAlgoResult.Nothing
    }
}