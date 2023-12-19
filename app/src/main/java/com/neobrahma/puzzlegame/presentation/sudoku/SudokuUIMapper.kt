package com.neobrahma.puzzlegame.presentation.sudoku

import javax.inject.Inject

class SudokuUIMapper @Inject constructor(){

    fun mapGrid(list: List<CellData>) : List<CellUi>{
        return list.map {
            if (it.value == 0){
                CellUi.Possibilities(it.possibleValue.toList())
            }else{
                CellUi.Value(it.value)
            }
        }
    }

}