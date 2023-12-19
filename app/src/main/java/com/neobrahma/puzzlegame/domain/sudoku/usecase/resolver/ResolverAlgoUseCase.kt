package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver

import com.neobrahma.puzzlegame.presentation.sudoku.CountForVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.model.SudokuData

interface ResolverAlgoUseCase {
    operator fun invoke(sudokuData: SudokuData, countFor: CountForVisitor): ResolverAlgoResult
}