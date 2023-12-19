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
    private val mapperUI: SudokuUIMapper
) : ViewModel() {

    private var sudokuData = SudokuData()

    private var countFor = 0

    private val linkedList: LinkedList<OneSolutionData> = LinkedList()

    private val visitorCountFor: CountForVisitor = {
        countFor++
    }

    private val findOnePossibilityVisitor: FindOnePossibilityVisitor = { indexCell, value ->
        val oneSolutionData = OneSolutionData(indexCell, value)
        if (!linkedList.contains(oneSolutionData)) {
            linkedList.add(oneSolutionData)
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

    private val _uiStateSudokuData = MutableStateFlow(SudokuUi("empty sudoku", mapperUI.mapGrid(SudokuData().cells)))
    val uiStateSudokuGrid: StateFlow<SudokuUi> = _uiStateSudokuData.asStateFlow()

    fun initGrid(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            countFor = 0
            getSudokuGridByUseCase(id).collect {
                sudokuData = SudokuData()
                it.grid.forEachIndexed { index, value ->
                    sudokuData.cells[index].value = value
                    if (value != 0) {
                        sudokuData.cells[index].possibleValue.clear()
                        refreshPossibilitiesUseCase(
                            sudokuData,
                            index,
                            value,
                            findOnePossibilityVisitor
                        )
                    }
                }
                _uiStateSudokuData.value = SudokuUi("init sudoku", mapperUI.mapGrid(sudokuData.cells))
            }

            dequeueList()
        }
    }

    fun clickButtonFindNextAction() {
        when (val result = findNextAction.invoke(sudokuData, visitorCountFor)) {
            is ResolverAlgoResult.FindOnePossibility -> {
                sudokuData.cells[result.index].value = result.value
                sudokuData.cells[result.index].possibleValue.clear()

                refreshPossibilitiesUseCase(
                    sudokuData,
                    result.index,
                    result.value,
                    findOnePossibilityVisitor
                )
                println("tom971 ${result.text}")
                _uiStateSudokuData.value =
                    SudokuUi("find value ${result.index} ${result.value}", mapperUI.mapGrid(sudokuData.cells))
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
            if (sudokuData.cells[oneSolution.indexCell].value == 0) {
                sudokuData.cells[oneSolution.indexCell].value = oneSolution.value
                refreshPossibilitiesUseCase(
                    sudokuData,
                    oneSolution.indexCell,
                    oneSolution.value,
                    findOnePossibilityVisitor
                )
                _uiStateSudokuData.value = SudokuUi(
                    "one solution ${oneSolution.indexCell} ${oneSolution.value}",
                    mapperUI.mapGrid(sudokuData.cells)
                )
            }
        }
    }
}

data class SudokuUi(
    val text: String,
    val sudoku: List<CellUi>
)

sealed class CellUi{
    class Value(val value : Int): CellUi()
    class Possibilities(val list: List<Int>): CellUi()
}

data class SudokuData(
    val cells: List<CellData> = List(81) { CellData() }
)

data class CellData(
    var value: Int = 0,
    val possibleValue: MutableList<Int> = MutableList(9) { 1 }
)

data class OneSolutionData(
    val indexCell: Int,
    val value: Int
)

typealias CountForVisitor = () -> Unit
typealias FindOnePossibilityVisitor = (Int, Int) -> Unit