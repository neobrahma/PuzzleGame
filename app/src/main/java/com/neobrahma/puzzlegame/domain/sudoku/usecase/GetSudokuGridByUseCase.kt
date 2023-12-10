package com.neobrahma.puzzlegame.domain.sudoku.usecase

import com.neobrahma.puzzlegame.domain.sudoku.model.SudokuDataDomain
import com.neobrahma.puzzlegame.domain.sudoku.repository.SudokuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSudokuGridByUseCase @Inject constructor(
    private val repository: SudokuRepository
){

    operator fun invoke(id : Int): Flow<SudokuDataDomain> {
        return repository.getSudokuGridBy(id)
    }
}