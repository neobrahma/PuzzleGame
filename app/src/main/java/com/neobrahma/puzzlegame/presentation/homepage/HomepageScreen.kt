package com.neobrahma.puzzlegame.presentation.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.neobrahma.puzzlegame.presentation.navigation.SudokuHomepageDestination

@Composable
fun HomepageScreen(navController: NavHostController) {
    Column {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            onClick = {
                navController.navigate(SudokuHomepageDestination.destination)
            }
        ) {
            Text(text = "Sudoku")
        }
    }
}