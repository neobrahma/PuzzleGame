package com.neobrahma.puzzlegame.domain.sudoku.usecase.resolve

import com.neobrahma.puzzlegame.presentation.sudoku.SudokuCell
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGrid

class FindOnePossibilityByRowUseCase  (private val nextAlgo: ResolverAlgo? = null
) : ResolverAlgo {

    companion object{
        const val DEFAULT = -1
        const val MORE_THAN_ONE_POSSIBILITY = -2
    }
    override fun invoke(sudokuGrid: SudokuGrid): ResolverAlgoResult {
        for (indexRow in 0 until 9) {
            val indexStart = indexRow * 9
            val possibilities = getSumOfPossibilities(indexStart, sudokuGrid.cells)
            possibilities.forEachIndexed{indexPossibility, indexCell ->
                if(indexCell > DEFAULT){
                    return ResolverAlgoResult.FindOnePossibility(
                        text = "find one possiblity by row",
                        index = indexCell,
                        value = indexPossibility+1
                    )
                }
            }
        }

        return nextAlgo?.invoke(sudokuGrid) ?: ResolverAlgoResult.Nothing
    }

    private fun getSumOfPossibilities(indexStart: Int, cells: List<SudokuCell>): List<Int> {
        val possibilities = MutableList(9) { DEFAULT }
        for (indexCellInRow in 0 until 9) {
            val indexCell = indexStart + indexCellInRow
            val possibleValuesInCell = cells[indexCell].possibleValue
            if (possibleValuesInCell.isNotEmpty()) {
                possibleValuesInCell.forEachIndexed { index, value ->
                    if (value == 1) {
                        if(possibilities[index] == DEFAULT){
                            possibilities[index] = indexCell
                        }else if(possibilities[index] > DEFAULT){
                            possibilities[index] = MORE_THAN_ONE_POSSIBILITY
                        }
                    }
                }
            }
        }
        return possibilities.toList()
    }

}