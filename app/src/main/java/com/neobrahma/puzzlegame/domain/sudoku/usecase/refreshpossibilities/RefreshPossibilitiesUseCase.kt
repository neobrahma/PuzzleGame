package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid
import javax.inject.Inject

class RefreshPossibilitiesUseCase @Inject constructor() : RefreshPossibilities {
    private val refreshPossibilitiesAlgo: List<RefreshPossibilities> = listOf(
        RefreshPossibilityByRowUseCase(),
        RefreshPossibilityByColumnUseCase(),
        RefreshPossibilityByGridUseCase()
    )

    override fun invoke(sudokuGrid: SudokuGrid, indexValue: Int, value: Int) {
        refreshPossibilitiesAlgo.forEach {
            it.invoke(sudokuGrid, indexValue, value)
        }
    }
}