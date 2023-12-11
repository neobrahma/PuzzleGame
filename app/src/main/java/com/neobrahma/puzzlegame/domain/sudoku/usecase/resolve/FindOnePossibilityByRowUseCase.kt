package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolve

import com.neobrahma.puzzlegame.domain.sudoku.usecase.DEFAULT
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInRow
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getSumOfPossibilities
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class FindOnePossibilityByRowUseCase(
    private val nextAlgo: ResolverAlgo? = null
) : ResolverAlgo {

    override fun invoke(sudokuGrid: SudokuGrid): ResolverAlgoResult {
        for (indexRow in 0 until 9) {
            val indexStart = indexRow * 9
            val possibilities = sudokuGrid.cells.getSumOfPossibilities(indexStart, ::getIndexInRow)
            possibilities.forEachIndexed { indexPossibility, indexCell ->
                if (indexCell > DEFAULT) {
                    return ResolverAlgoResult.FindOnePossibility(
                        text = "find one possiblity by row",
                        index = indexCell,
                        value = indexPossibility + 1
                    )
                }
            }
        }

        return nextAlgo?.invoke(sudokuGrid) ?: ResolverAlgoResult.Nothing
    }

}

