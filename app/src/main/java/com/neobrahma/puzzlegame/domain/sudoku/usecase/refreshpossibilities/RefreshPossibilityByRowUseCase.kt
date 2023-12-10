package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class RefreshPossibilityByRowUseCase : RefreshPossibilities{
    override fun invoke(sudokuGrid: SudokuGrid, indexValue: Int, value: Int) {
        val indexStart = (indexValue / 9) * 9
        for (i in 0 until 9){
            val index = indexStart + i
            if(index != indexValue){
                sudokuGrid.sudokuCell[index].possibleValue[value-1] = 0
            }
        }
    }
}