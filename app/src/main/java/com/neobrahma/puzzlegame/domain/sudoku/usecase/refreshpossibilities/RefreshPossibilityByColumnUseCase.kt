package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class RefreshPossibilityByColumnUseCase : RefreshPossibilities{
    override fun invoke(sudokuGrid: SudokuGrid, indexValue: Int, value: Int) {
        val indexStart = indexValue % 9
        for (i in 0 until 9){
            val index = indexStart + i*9
            if(index != indexValue){
                if (sudokuGrid.cells[index].possibleValue.isNotEmpty()) {
                    sudokuGrid.cells[index].possibleValue[value - 1] = 0
                }
            }
        }
    }

}