package com.neobrahma.puzzlegame.repository.sudoku

import com.neobrahma.puzzlegame.domain.sudoku.model.SudokuDataDomain
import com.neobrahma.puzzlegame.domain.sudoku.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SudokuRepositoryImpl @Inject constructor(
    private val dataSource: SudokuDataSource
) : SudokuRepository {

    override fun getSudokuList(): Flow<List<SudokuDataDomain>> {
        return dataSource.getSudokuList()
    }

}