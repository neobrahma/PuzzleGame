package com.neobrahma.puzzlegame.presentation.sudoku.ui.model

sealed class CellUi {
    class ValueUI(val value: String) : CellUi()
    class PossibilitiesUI(val list: List<String>) : CellUi()
}