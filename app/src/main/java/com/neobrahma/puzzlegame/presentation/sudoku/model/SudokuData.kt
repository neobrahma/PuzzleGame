package com.neobrahma.puzzlegame.presentation.sudoku.model

data class SudokuData(
    val cells: List<CellData> = List(81) { CellData(cellId = it) }
)