package com.neobrahma.puzzlegame.presentation.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobrahma.puzzlegame.domain.sudoku.usecase.GetSudokuGridByUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.GetSudokuListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    getSudokuListUseCase: GetSudokuListUseCase,
    private val getSudokuGridByUseCase: GetSudokuGridByUseCase
) : ViewModel() {

    private val sudokuGrid = SudokuGrid()

    val initHome: Flow<List<String>> = getSudokuListUseCase().map {
        it.map { sudoku -> "${sudoku.id}" }
    }

    private val _uiStateSudokuGrid = MutableStateFlow(sudokuGrid)
    val uiStateSudokuGrid: StateFlow<SudokuGrid> = _uiStateSudokuGrid.asStateFlow()

    fun initGrid(id: Int) {
        viewModelScope.launch {
            getSudokuGridByUseCase(id).collect {
                it.grid.forEachIndexed { index, i ->
                    sudokuGrid.sudokuCell[index].value = i
                }
                _uiStateSudokuGrid.value = sudokuGrid
            }
        }
    }
}

data class SudokuGrid(
    val sudokuCell : List<SudokuCell> = List(81){ SudokuCell() }
)

data class SudokuCell(
    var value : Int = 0,
    val possibleValue : MutableList<Int> = mutableListOf(1,2,3,4,5,6,7,8,9)
)