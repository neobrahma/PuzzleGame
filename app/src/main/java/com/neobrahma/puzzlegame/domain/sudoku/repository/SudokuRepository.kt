package com.neobrahma.puzzlegame.domain.sudoku.repository

import com.neobrahma.puzzlegame.domain.sudoku.model.SudokuDataDomain
import kotlinx.coroutines.flow.Flow

interface SudokuRepository {
    fun getSudokuList(): Flow<List<SudokuDataDomain>>
    fun getSudokuGridBy(id : Int):Flow<SudokuDataDomain>
}