package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class RefreshPossibilityByGridUseCase : RefreshPossibilities {
    override fun invoke(sudokuGrid: SudokuGrid, indexValue: Int, value: Int) {
        val indexRowGrid = (indexValue / 9) / 3
        val indexColumnGrid = (indexValue % 9) / 3
        val indexStart = (9 * 3 * indexRowGrid) + (3 * indexColumnGrid)
        for (i in 0 until 9) {
            val indexRow = i / 3
            val index = indexStart + (i % 3) + (indexRow * 9)
            if(index != indexValue){
                sudokuGrid.sudokuCell[index].possibleValue[value-1] = 0
            }
        }
    }

}