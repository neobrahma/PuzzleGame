package com.neobrahma.puzzlegame.presentation.sudoku.ui.model

sealed class CellUi {
    class ValueUI(val value: Int) : CellUi()
    class PossibilitiesUI(val list: List<Int>) : CellUi()
}