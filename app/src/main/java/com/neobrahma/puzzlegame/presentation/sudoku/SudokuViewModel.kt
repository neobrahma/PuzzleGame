package com.neobrahma.puzzlegame.presentation.sudoku

import androidx.lifecycle.ViewModel
import com.neobrahma.puzzlegame.domain.sudoku.usecase.GetSudokuListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    getSudokuListUseCase: GetSudokuListUseCase
) : ViewModel() {

    val initHome: Flow<List<String>> = getSudokuListUseCase().map {
        it.map { "${it.id}" }
    }

    fun initGrid(id : Int){
        println("tom971 id $id")
    }
}