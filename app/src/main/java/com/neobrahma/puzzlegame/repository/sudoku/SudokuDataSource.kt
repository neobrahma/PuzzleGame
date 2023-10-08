package com.neobrahma.puzzlegame.repository.sudoku

import com.neobrahma.puzzlegame.domain.sudoku.model.SudokuDataDomain
import kotlinx.coroutines.flow.Flow

interface SudokuDataSource {
    fun getSudokuList(): Flow<List<SudokuDataDomain>>
}