package com.neobrahma.puzzlegame.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neobrahma.puzzlegame.presentation.homepage.HomepageScreen
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuGridScreen
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuHomepageScreen
import com.neobrahma.puzzlegame.presentation.sudoku.SudokuViewModel

@Composable
fun PuzzleGameApp(
    sudokuVM: SudokuViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomepageDestination.destination
    ) {
        composable(
            route = HomepageDestination.destination
        )
        {
            HomepageScreen(navController)
        }

        composable(
            route = SudokuHomepageDestination.destination
        )
        {
            SudokuHomepageScreen(sudokuVM, navController)
        }
        composable(
            route = SudokuGridDestination.destination
        )
        {
            SudokuGridScreen(sudokuVM)
        }
    }
}