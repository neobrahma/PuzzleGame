package com.neobrahma.puzzlegame.presentation.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobrahma.puzzlegame.domain.sudoku.usecase.GetSudokuGridByUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.GetSudokuListUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.refreshpossibilities.RefreshPossibilitiesUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.FindOnePossibilityByCellUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.FindOnePossibilityByColumnUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.FindOnePossibilityByGridUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.FindOnePossibilityByRowUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.ResolverAlgoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.LinkedList
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    getSudokuListUseCase: GetSudokuListUseCase,
    private val getSudokuGridByUseCase: GetSudokuGridByUseCase,
    private val refreshPossibilitiesUseCase: RefreshPossibilitiesUseCase,
) : ViewModel() {

    private var sudokuGrid = SudokuGrid()

    private var countFor = 0

    private val linkedList: LinkedList<OneSolution> = LinkedList()

    private val visitorCountFor: CountForVisitor = {
        countFor++
    }

    private val findOnePossibilityVisitor: FindOnePossibilityVisitor = { indexCell, value ->
        val oneSolution = OneSolution(indexCell, value)
        if (!linkedList.contains(oneSolution)) {
            linkedList.add(oneSolution)
        }
    }

    private val findNextAction =
        FindOnePossibilityByCellUseCase(
            FindOnePossibilityByGridUseCase(
                FindOnePossibilityByRowUseCase(
                    FindOnePossibilityByColumnUseCase()
                )
            )
        )

    val initHome: Flow<List<String>> = getSudokuListUseCase().map {
        it.map { sudoku -> "${sudoku.id}" }
    }

    private val _uiStateSudokuGrid = MutableStateFlow(SudokuUi("empty sudoku", SudokuGrid().cells))
    val uiStateSudokuGrid: StateFlow<SudokuUi> = _uiStateSudokuGrid.asStateFlow()

    fun initGrid(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            countFor = 0
            getSudokuGridByUseCase(id).collect {
                sudokuGrid = SudokuGrid()
                it.grid.forEachIndexed { index, value ->
                    sudokuGrid.cells[index].value = value
                    if (value != 0) {
                        sudokuGrid.cells[index].possibleValue.clear()
                        refreshPossibilitiesUseCase(
                            sudokuGrid,
                            index,
                            value,
                            findOnePossibilityVisitor
                        )
                    }
                }
                _uiStateSudokuGrid.value = SudokuUi("init sudoku", sudokuGrid.cells)
            }

            dequeueList()
        }
    }

    fun clickButtonFindNextAction() {
        when (val result = findNextAction.invoke(sudokuGrid, visitorCountFor)) {
            is ResolverAlgoResult.FindOnePossibility -> {
                sudokuGrid.cells[result.index].value = result.value
                sudokuGrid.cells[result.index].possibleValue.clear()

                refreshPossibilitiesUseCase(
                    sudokuGrid,
                    result.index,
                    result.value,
                    findOnePossibilityVisitor
                )
                println("tom971 ${result.text}")
                _uiStateSudokuGrid.value =
                    SudokuUi("find value ${result.index} ${result.value}", sudokuGrid.cells)
            }

            ResolverAlgoResult.Nothing -> {
                println("tom971 aucun algo")
            }
        }
        println("tom971 nombre de boucle total effectué $countFor")
    }

    private fun dequeueList() {
        while (linkedList.isNotEmpty()) {
            val oneSolution = linkedList.removeFirst()
            if (sudokuGrid.cells[oneSolution.indexCell].value == 0) {
                sudokuGrid.cells[oneSolution.indexCell].value = oneSolution.value
                refreshPossibilitiesUseCase(
                    sudokuGrid,
                    oneSolution.indexCell,
                    oneSolution.value,
                    findOnePossibilityVisitor
                )
                _uiStateSudokuGrid.value = SudokuUi(
                    "one solution ${oneSolution.indexCell} ${oneSolution.value}",
                    sudokuGrid.cells
                )
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

data class OneSolution(
    val indexCell: Int,
    val value: Int
)

typealias CountForVisitor = () -> Unit
typealias FindOnePossibilityVisitor = (Int, Int) -> Unit