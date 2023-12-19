package com.neobrahma.puzzlegame.presentation.sudoku.model

data class CellData(
    val cellId: Int,
    var value: Int = 0,
    val possibleValue: MutableList<Int> = MutableList(9) { 1 }
)