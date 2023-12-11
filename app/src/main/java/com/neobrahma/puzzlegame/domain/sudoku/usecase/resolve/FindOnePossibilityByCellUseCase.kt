package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolve

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class FindOnePossibilityByCellUseCase(
    private val nextAlgo: ResolverAlgo? = null
) : ResolverAlgo {
    override fun invoke(sudokuGrid: SudokuGrid): ResolverAlgoResult {
        sudokuGrid.cells.forEachIndexed loop@{ indexCell, sudokuCell ->
            if (sudokuCell.value == 0) {

                var result = 0
                sudokuCell.possibleValue.forEachIndexed { index, value ->
                    if (value != 0) {
                        if (result == 0) {
                            result = value * (index + 1)
                        } else {
                            return@loop
                        }
                    }
                }

                if (result != 0) {
                    return ResolverAlgoResult.FindOnePossibility(
                        text = "find one possiblity",
                        index = indexCell,
                        value = result
                    )
                }
            }
        }
        return nextAlgo?.invoke(sudokuGrid) ?: ResolverAlgoResult.Nothing
    }
}