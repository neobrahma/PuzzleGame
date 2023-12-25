package com.neobrahma.puzzlegame.presentation.sudoku

import com.neobrahma.puzzlegame.presentation.sudoku.model.CellData
import com.neobrahma.puzzlegame.presentation.sudoku.ui.model.CellUi
import javax.inject.Inject

class SudokuUIMapper @Inject constructor() {

    fun mapGrid(list: List<CellData>): List<CellUi> {
        return list.map {
            if (it.value == 0) {
                CellUi.PossibilitiesUI(it.possibleValue.mapIndexed { index, value ->
                    when(value){
                        0 -> ""
                        else -> (value * (index + 1)).toString()
                    }
                })
            } else {
                CellUi.ValueUI(it.value.toString())
            }
        }
    }

}