package com.neobrahma.puzzlegame

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neobrahma.armada.presentation.ArmadaActivity
import com.neobrahma.chargeup.presentation.ChargeUpActivity
import com.neobrahma.puzzlegame.ui.PuzzleGameTheme
import com.neobrahma.sudoku.presentation.SudokuActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PuzzleGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val context = LocalContext.current
    Column {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            onClick = {
                context.startActivity(Intent(context, ChargeUpActivity::class.java))
            }
        ) {
            Text(text = "Charge Up")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            onClick = {
                context.startActivity(Intent(context, ArmadaActivity::class.java))
            }
        ) {
            Text(text = "Armada")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            onClick = {
                context.startActivity(Intent(context, SudokuActivity::class.java))
            }
        ) {
            Text(text = "Sudoku")
        }
    }

}