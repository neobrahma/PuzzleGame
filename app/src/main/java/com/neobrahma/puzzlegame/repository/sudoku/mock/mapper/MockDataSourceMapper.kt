package com.neobrahma.puzzlegame.repository.sudoku.mock.mapper

import com.neobrahma.puzzlegame.domain.sudoku.model.SudokuDataDomain
import com.neobrahma.puzzlegame.repository.sudoku.mock.model.SudokuDataRepo
import javax.inject.Inject

class MockDataSourceMapper @Inject constructor() {

    fun mapSudokuGridsRepoToDomain(list : List<SudokuDataRepo>): List<SudokuDataDomain>{
        return list.map {
            mapSudokuGridRepoToDomain(it)
        }
    }

    fun mapSudokuGridRepoToDomain(item : SudokuDataRepo): SudokuDataDomain{
        return SudokuDataDomain(
            id = item.id,
            grid = item.grid
        )
    }
}