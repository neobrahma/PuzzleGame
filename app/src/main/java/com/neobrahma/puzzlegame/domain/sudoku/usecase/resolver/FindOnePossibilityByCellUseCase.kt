package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver

import com.neobrahma.puzzlegame.presentation.sudoku.CountForVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.model.SudokuData

class FindOnePossibilityByCellUseCase(
    private val nextAlgo: ResolverAlgoUseCase? = null
) : ResolverAlgoUseCase {
    override fun invoke(sudokuData: SudokuData, countFor: CountForVisitor): ResolverAlgoResult {
        sudokuData.cells.forEachIndexed loop@{ indexCell, sudokuCell ->
            countFor.invoke()
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
        return nextAlgo?.invoke(sudokuData, countFor) ?: ResolverAlgoResult.Nothing
    }
}