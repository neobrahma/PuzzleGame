package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver

import com.neobrahma.puzzlegame.domain.sudoku.usecase.DEFAULT
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInGrid
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getSumOfPossibilities
import com.neobrahma.puzzlegame.presentation.sudoku.CountForVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.model.SudokuData

class FindOnePossibilityByGridUseCase(
    private val nextAlgo: ResolverAlgoUseCase? = null
) : ResolverAlgoUseCase {

    override fun invoke(sudokuData: SudokuData, countFor: CountForVisitor): ResolverAlgoResult {
        for (indexGrid in 0 until 9) {
            countFor.invoke()
            val indexStart = ((indexGrid % 3) * 3) + ((indexGrid / 3) * (3 * 9))
            val possibilities = sudokuData.cells.getSumOfPossibilities(indexStart, ::getIndexInGrid)
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

        return nextAlgo?.invoke(sudokuData, countFor) ?: ResolverAlgoResult.Nothing
    }

}

