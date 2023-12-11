package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver

import com.neobrahma.puzzlegame.domain.sudoku.usecase.DEFAULT
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInGrid
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getSumOfPossibilities
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class FindOnePossibilityByGridUseCase(
    private val nextAlgo: ResolverAlgo? = null
) : ResolverAlgo {

    override fun invoke(sudokuGrid: SudokuGrid): ResolverAlgoResult {
        for (indexGrid in 0 until 9) {
            val indexStart = ((indexGrid % 3) * 3) + ((indexGrid / 3) * (3 * 9))
            val possibilities = sudokuGrid.cells.getSumOfPossibilities(indexStart, ::getIndexInGrid)
            possibilities.forEachIndexed { indexPossibility, indexCell ->
                if (indexCell > DEFAULT) {
                    return ResolverAlgoResult.FindOnePossibility(
                        text = "find one possiblity",
                        index = indexCell,
                        value = indexPossibility + 1
                    )
                }
            }
        }

        return nextAlgo?.invoke(sudokuGrid) ?: ResolverAlgoResult.Nothing
    }

}

