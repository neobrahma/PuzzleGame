package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver

import com.neobrahma.puzzlegame.domain.sudoku.usecase.DEFAULT
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInColumn
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getSumOfPossibilities
import com.neobrahma.puzzlegame.presentation.sudoku.CountForVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class FindOnePossibilityByColumnUseCase(
    private val nextAlgo: ResolverAlgo? = null
) : ResolverAlgo {

    override fun invoke(
        sudokuGrid: SudokuGrid, countFor : CountForVisitor
    ): ResolverAlgoResult {
        for (indexColumn in 0 until 9) {
            countFor.invoke()
            val possibilities =
                sudokuGrid.cells.getSumOfPossibilities(indexColumn, ::getIndexInColumn)
            possibilities.forEachIndexed { indexPossibility, indexCell ->
                if (indexCell > DEFAULT) {
                    return ResolverAlgoResult.FindOnePossibility(
                        text = "find one possiblity by column",
                        index = indexCell,
                        value = indexPossibility + 1
                    )
                }
            }
        }

        return nextAlgo?.invoke(sudokuGrid, countFor) ?: ResolverAlgoResult.Nothing
    }

}

