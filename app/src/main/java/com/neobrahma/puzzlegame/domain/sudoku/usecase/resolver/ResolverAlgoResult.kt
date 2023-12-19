package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver

sealed class ResolverAlgoResult {
    data class FindOnePossibility(val text: String, val index: Int, val value: Int) :
        ResolverAlgoResult()

    object Nothing : ResolverAlgoResult()

}