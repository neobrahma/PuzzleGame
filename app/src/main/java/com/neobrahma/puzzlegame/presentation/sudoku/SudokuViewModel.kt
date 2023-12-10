package com.neobrahma.puzzlegame.presentation.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobrahma.puzzlegame.domain.sudoku.usecase.GetSudokuGridByUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.GetSudokuListUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities.RefreshPossibilitiesUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolve.FindOnePossibilityByCellUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolve.ResolverAlgoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val getSudokuGridByUseCase: GetSudokuGridByUseCase,
    private val refreshPossibilitiesUseCase: RefreshPossibilitiesUseCase,
) : ViewModel() {

    private var sudokuGrid = SudokuGrid()

    val initHome: Flow<List<String>> = getSudokuListUseCase().map {
        it.map { sudoku -> "${sudoku.id}" }
    }

    private val _uiStateSudokuGrid = MutableStateFlow(SudokuUi("empty sudoku", SudokuGrid().cells))
    val uiStateSudokuGrid: StateFlow<SudokuUi> = _uiStateSudokuGrid.asStateFlow()

    fun initGrid(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getSudokuGridByUseCase(id).collect {
                sudokuGrid = SudokuGrid()
                it.grid.forEachIndexed { index, value ->
                    sudokuGrid.cells[index].value = value
                    if (value != 0) {
                        sudokuGrid.cells[index].possibleValue.clear()
                        refreshPossibilitiesUseCase(sudokuGrid, index, value)
                    }
                }
                _uiStateSudokuGrid.value = SudokuUi("init sudoku", sudokuGrid.cells)
            }
        }
    }

    fun clickButtonFindNextAction() {
        viewModelScope.launch(Dispatchers.IO) {
            val findNextAction = FindOnePossibilityByCellUseCase()
            when (val result = findNextAction.invoke(sudokuGrid)) {
                is ResolverAlgoResult.FindOnePossibility -> {
                    sudokuGrid.cells[result.index].value = result.value
                    sudokuGrid.cells[result.index].possibleValue.clear()

                    refreshPossibilitiesUseCase(sudokuGrid, result.index, result.value)
                    _uiStateSudokuGrid.value =
                        SudokuUi("find value ${result.index} ${result.value}", sudokuGrid.cells)
                }

                ResolverAlgoResult.Nothing -> {
                    println("tom971 aucun algo")
                }
            }
        }
    }
}

data class SudokuUi(
    val text: String,
    val list: List<SudokuCell>
)

data class SudokuGrid(
    val cells: List<SudokuCell> = List(81) { SudokuCell() }
)

data class SudokuCell(
    var value: Int = 0,
    val possibleValue: MutableList<Int> = MutableList(9) { 1 }
)