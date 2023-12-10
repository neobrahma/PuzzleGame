package com.neobrahma.puzzlegame.presentation.sudoku

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun SudokuGridScreen(
    viewModel: SudokuViewModel
) {
    val uiState by viewModel.uiStateSudokuGrid.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Sudoku(uiState.sudokuCell)
            Button(onClick = {
                viewModel.clickButtonFindNextAction()
            }) {
                Text(text = "find next action")
            }
        }

    }
}

@Composable
fun Sudoku(sudokuCells: List<SudokuCell>) {
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
                    Grid(indexStart, sudokuCells)

                }
            }
        }
    }
}

@Composable
fun Grid(indexStart: Int, sudokuCells: List<SudokuCell>) {
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
                    if (sudokuCells[indexValue].value != 0) {
                        Value(sudokuCells[indexValue].value)
                    } else {
                        Possibilities(sudokuCells[indexValue].possibleValue)
                    }
                }
            }
        }
    }
}

@Composable
fun Possibilities(possibleValue: MutableList<Int>) {
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
                    val value = possibleValue[indexValue]
                    Text(
                        modifier = Modifier.size(10.dp),
                        text = if (value == 0) {
                            ""
                        } else {
                            value.toString()
                        },
                        fontSize = 8.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun Value(value: Int) {
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
            text = value.toString(),
            textAlign = TextAlign.Center
        )

    }
}
