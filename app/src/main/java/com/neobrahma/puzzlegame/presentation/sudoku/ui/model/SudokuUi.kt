package com.neobrahma.puzzlegame.presentation.sudoku.ui.model

data class SudokuUi(
    val text: String,
    val list : List<String>,
    val sudoku: List<CellUi>
)