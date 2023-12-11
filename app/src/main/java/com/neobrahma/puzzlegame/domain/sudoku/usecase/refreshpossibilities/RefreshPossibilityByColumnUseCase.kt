package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInColumn
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class RefreshPossibilityByColumnUseCase : RefreshPossibilities {
    override fun invoke(sudokuGrid: SudokuGrid, indexValue: Int, value: Int) {
        val indexStart = indexValue % 9
        for (i in 0 until 9) {
            val index = getIndexInColumn(indexStart, i)
            if (index != indexValue) {
                if (sudokuGrid.cells[index].possibleValue.isNotEmpty()) {
                    sudokuGrid.cells[index].possibleValue[value - 1] = 0
                }
            }
        }
    }
}