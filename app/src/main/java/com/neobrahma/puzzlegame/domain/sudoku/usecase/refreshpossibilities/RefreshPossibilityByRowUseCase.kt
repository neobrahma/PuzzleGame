package com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities

import com.neobrahma.puzzlegame.domain.sudoku.usecase.DEFAULT
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getIndexInRow
import com.neobrahma.puzzlegame.domain.sudoku.usecase.getSumOfPossibilities
import com.neobrahma.puzzlegame.presentation.sudoku.FindOnePossibilityVisitor
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuData

class RefreshPossibilityByRowUseCase : RefreshPossibilities {
    override fun invoke(
        sudokuData: SudokuData,
        indexValue: Int,
        value: Int,
        findOnePossibilityVisitor: FindOnePossibilityVisitor
    ) {
        val indexStart = (indexValue / 9) * 9
        for (i in 0 until 9) {
            val index = getIndexInRow(indexStart, i)
            if (index != indexValue) {
                with(sudokuData.cells[index].possibleValue) {
                    if (this.isNotEmpty()) {
                        this[value - 1] = 0
                    }
                }
            }
        }

        val possibilities = sudokuData.cells.getSumOfPossibilities(indexStart, ::getIndexInRow)
        possibilities.forEachIndexed { indexPossibility, indexCell ->
            if (indexCell > DEFAULT) {
                findOnePossibilityVisitor(indexCell, indexPossibility + 1)
            }
        }
    }
}