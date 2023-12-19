package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.presentation.sudoku.FindOnePossibilityVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid
import javax.inject.Inject

class RefreshPossibilitiesUseCase @Inject constructor() : RefreshPossibilities {
    private val refreshPossibilitiesAlgo: List<RefreshPossibilities> = listOf(
        RefreshPossibilityByGridUseCase(),
        RefreshPossibilityByRowUseCase(),
        RefreshPossibilityByColumnUseCase()
    )

    override fun invoke(
        sudokuGrid: SudokuGrid,
        indexValue: Int,
        value: Int,
        findOnePossibilityVisitor: FindOnePossibilityVisitor
    ) {
        refreshPossibilitiesAlgo.forEach {
            it.invoke(sudokuGrid, indexValue, value, findOnePossibilityVisitor)
        }
    }
}