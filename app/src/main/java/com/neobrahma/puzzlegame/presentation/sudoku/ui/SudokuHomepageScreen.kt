package com.neobrahma.puzzlegame.presentation.sudoku.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.neobrahma.puzzlegame.presentation.navigation.SudokuGridDestination
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuViewModel

@Composable
fun SudokuHomepageScreen(
    viewModel: SudokuViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.initHome.collectAsState(initial = emptyList())
    SudokuListView(uiState,
        onButtonClick = {
            viewModel.initGrid(id = it)
            navController.navigate(SudokuGridDestination.destination)
        })
}

@Composable
fun SudokuListView(
    list: List<String>,
    onButtonClick: (Int) -> Unit = {}
) {
    LazyColumn {
        items(list) {
            SudokuButton(it, onButtonClick)
        }
    }
}

@Composable
fun SudokuButton(
    id: String,
    onButtonClick: (Int) -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = {
            onButtonClick(id.toInt())
        }
    ) {
        Text(text = "Grille $id")
    }
}