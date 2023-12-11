package com.neobrahma.puzzlegame.domain.sudoku.usecase

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuCell

const val DEFAULT = -1
const val MORE_THAN_ONE_POSSIBILITY = -2

fun List<SudokuCell>.getSumOfPossibilities(
    indexStart: Int,
    getIndexCell: (Int, Int) -> Int
): List<Int> {
    val possibilities = MutableList(9) { DEFAULT }
    for (index in 0 until 9) {
        val indexCell = getIndexCell(indexStart, index)
        val possibleValuesInCell = this[indexCell].possibleValue
        if (possibleValuesInCell.isNotEmpty()) {
            possibleValuesInCell.forEachIndexed { indexPossibleValue, value ->
                if (value == 1) {
                    if (possibilities[indexPossibleValue] == DEFAULT) {
                        possibilities[indexPossibleValue] = indexCell
                    } else if (possibilities[indexPossibleValue] > DEFAULT) {
                        possibilities[indexPossibleValue] = MORE_THAN_ONE_POSSIBILITY
                    }
                }
            }
        }
    }
    return possibilities.toList()
}

fun getIndexInRow(indexStartRow: Int, index: Int): Int {
    return indexStartRow + index
}

fun getIndexInColumn(indexStartRow: Int, index: Int): Int {
    return indexStartRow + (index * 9)
}

fun getIndexInGrid(indexStartInGrid: Int, index: Int): Int {
    val indexRow = index / 3
    return indexStartInGrid + (index % 3) + (indexRow * 9)
}