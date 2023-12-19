package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver

import com.neobrahma.puzzlegame.domain.sudoku.usecase.DEFAULT
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInRow
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getSumOfPossibilities
import com.neobrahma.puzzlegame.presentation.sudoku.CountForVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuData

class FindOnePossibilityByRowUseCase(
    private val nextAlgo: ResolverAlgo? = null
) : ResolverAlgo {

    override fun invoke(sudokuData: SudokuData, countFor : CountForVisitor): ResolverAlgoResult {
        for (indexRow in 0 until 9) {
            countFor.invoke()
            val indexStart = indexRow * 9
            val possibilities = sudokuData.cells.getSumOfPossibilities(indexStart, ::getIndexInRow)
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

        return nextAlgo?.invoke(sudokuData, countFor) ?: ResolverAlgoResult.Nothing
    }

}

