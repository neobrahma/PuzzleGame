package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.domain.sudoku.usecase.DEFAULT
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexGridX
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexGridY
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInGrid
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getSumOfPossibilities
import com.neobrahma.puzzlegame.presentation.sudoku.FindOnePossibilityVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuData

class RefreshPossibilityByGridUseCase : RefreshPossibilities {
    override fun invoke(
        sudokuData: SudokuData,
        indexValue: Int,
        value: Int,
        findOnePossibilityVisitor: FindOnePossibilityVisitor
    ) {
        val indexRowGrid = getIndexGridY(indexValue)
        val indexColumnGrid = getIndexGridX(indexValue)
        val indexStart = (9 * 3 * indexRowGrid) + (3 * indexColumnGrid)
        for (i in 0 until 9) {
            val index = getIndexInGrid(indexStart, i)
            if (index != indexValue) {
                with(sudokuData.cells[index].possibleValue) {
                    if (this.isNotEmpty()) {
                        this[value - 1] = 0
                    }
                }
            }
        }

        val possibilities = sudokuData.cells.getSumOfPossibilities(indexStart, ::getIndexInGrid)
        possibilities.forEachIndexed { indexPossibility, indexCell ->
            if (indexCell > DEFAULT) {
                findOnePossibilityVisitor(indexCell, indexPossibility + 1)
            }
        }
    }
}