package com.neobrahma.puzzlegame.presentation.sudoku.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuViewModel
import com.neobrahma.puzzlegame.presentation.sudoku.ui.model.CellUi

@Composable
fun SudokuGridScreen(
    viewModel: SudokuViewModel,
) {
    val uiState by viewModel.uiStateSudokuGrid.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Sudoku(uiState.sudoku)
        Button(onClick = {
            viewModel.clickButtonFindNextAction()
        }) {
            Text(text = "find next action")
        }
        ActionsList(uiState.list)
    }
}

@Composable
fun Sudoku(sudoku: List<CellUi>) {
    Column(
        modifier = Modifier.drawBehind {
            drawRect(
                color = Color.Blue,
                style = Stroke(width = 4f)
            )
        }
    ) {
        for (row in 0 until 3) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (column in 0 until 3) {
                    val indexStart = (column * 3) + (row * 9 * 3)
                    Grid(indexStart, sudoku)
                }
            }
        }
    }
}

@Composable
fun Grid(indexStart: Int, sudoku: List<CellUi>) {
    Column(
        modifier = Modifier.drawBehind {
            drawRect(
                color = Color.Blue,
                style = Stroke(width = 4f)
            )
        }
    ) {
        for (row in 0 until 3) {
            Row {
                for (column in 0 until 3) {
                    val indexValue = indexStart + column + (row * 9)
                    when (val cellUi = sudoku[indexValue]) {
                        is CellUi.PossibilitiesUI -> Possibilities(cellUi.list)
                        is CellUi.ValueUI -> Value(value = cellUi.value)
                    }
                }
            }
        }
    }
}

@Composable
fun Possibilities(possibleValue: List<String>) {
    Column(
        modifier = Modifier
            .size(30.dp)
            .drawBehind {
                drawRect(
                    color = Color.Blue,
                    style = Stroke(width = 1f)
                )
            }
    ) {
        for (row in 0 until 3) {
            Row {
                for (index in 0 until 3) {
                    val indexValue = index + row * 3
                    Text(
                        modifier = Modifier.size(10.dp),
                        text = possibleValue[indexValue],
                        fontSize = 8.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun Value(value: String) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .drawBehind {
                drawRect(
                    color = Color.Blue,
                    style = Stroke(width = 1f)
                )
            }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = value,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun ActionsList(actions: List<String>) {
    val chatListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = chatListState
    ) {
        items(actions) {
            Text(text = it)
        }
    }
    LaunchedEffect(actions.size) {
        chatListState.animateScrollToItem(chatListState.layoutInfo.totalItemsCount)
    }
}