package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInGrid
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class RefreshPossibilityByGridUseCase : RefreshPossibilities {
    override fun invoke(sudokuGrid: SudokuGrid, indexValue: Int, value: Int) {
        val indexRowGrid = (indexValue / 9) / 3
        val indexColumnGrid = (indexValue % 9) / 3
        val indexStart = (9 * 3 * indexRowGrid) + (3 * indexColumnGrid)
        for (i in 0 until 9) {
            val index = getIndexInGrid(indexStart, i)
            if (index != indexValue) {
                if (sudokuGrid.cells[index].possibleValue.isNotEmpty()) {
                    sudokuGrid.cells[index].possibleValue[value - 1] = 0
                }
            }
        }
    }
}