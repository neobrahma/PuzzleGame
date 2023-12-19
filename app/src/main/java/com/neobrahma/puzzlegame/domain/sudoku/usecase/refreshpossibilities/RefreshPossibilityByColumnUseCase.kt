package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.domain.sudoku.usecase.DEFAULT
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInColumn
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getSumOfPossibilities
import com.neobrahma.puzzlegame.presentation.sudoku.FindOnePossibilityVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class RefreshPossibilityByColumnUseCase : RefreshPossibilities {
    override fun invoke(
        sudokuGrid: SudokuGrid,
        indexValue: Int,
        value: Int,
        findOnePossibilityVisitor: FindOnePossibilityVisitor
    ) {
        val indexStart = indexValue % 9
        for (i in 0 until 9) {
            val index = getIndexInColumn(indexStart, i)
            if (index != indexValue) {
                with(sudokuGrid.cells[index].possibleValue) {
                    if (this.isNotEmpty()) {
                        this[value - 1] = 0
                    }
                }
            }
        }

        val possibilities = sudokuGrid.cells.getSumOfPossibilities(indexStart, ::getIndexInColumn)
        possibilities.forEachIndexed { indexPossibility, indexCell ->
            if (indexCell > DEFAULT) {
                findOnePossibilityVisitor(indexCell, indexPossibility + 1)
            }
        }
    }
}