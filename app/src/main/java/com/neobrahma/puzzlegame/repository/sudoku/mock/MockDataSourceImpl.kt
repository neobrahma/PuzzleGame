package com.neobrahma.puzzlegame.repository.sudoku.mock

import com.neobrahma.puzzlegame.domain.sudoku.model.SudokuDataDomain
import com.neobrahma.puzzlegame.repository.sudoku.SudokuDataSource
import com.neobrahma.puzzlegame.repository.sudoku.mock.mapper.MockDataSourceMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor(
    private val mapper: MockDataSourceMapper
) : SudokuDataSource {

    override fun getSudokuList(): Flow<List<SudokuDataDomain>> {
        return flow {
            emit(mapper.mapSudokusRepoToDomain(sudokus))
        }
    }
}