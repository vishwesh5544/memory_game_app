package com.vishwesh.memorygame.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vishwesh.memorygame.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameScreen() {
    val gameViewModel: GameViewModel = viewModel()

    val score by gameViewModel.score
    val gameEnded by gameViewModel.gameEnded
    val showTiles by gameViewModel.showTiles
    val canSubmit by gameViewModel.canSubmit
    val userSelections = gameViewModel.userSelections
    val timerValue by gameViewModel.timerValue.collectAsState()

    if (gameEnded) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Game Over! Your score was $score", color = Color.Red)
            Button(onClick = { gameViewModel.startGame() }) {
                Text("Restart Game")
            }
        }
    } else {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Score: $score")
                if (!showTiles) {
                    Text("Memorize tiles for 3 seconds")
                    Timer(timerValue)
                } else if (canSubmit) {
                    Text("Submit your answers within 5 seconds")
                    Timer(timerValue)
                    Button(onClick = { gameViewModel.submitAnswers() }) {
                        Text("Submit Answers")
                    }
                }
                Grid(
                    gridSize = 6,
                    highlightedTiles = gameViewModel.highlightedTiles,
                    userSelections = userSelections,
                    showTiles = showTiles,
                    canSubmit = canSubmit,
                    onClick = { index -> gameViewModel.toggleTileSelection(index) }
                )
            }
        }
    }
}

@Composable
fun Timer(timerValue: Int) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.Gray)
            .border(1.dp, Color.Black)
            .padding(4.dp)
    ) {
        Text(
            text = "$timerValue",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}

@Composable
fun Grid(
    gridSize: Int,
    highlightedTiles: List<Boolean>,
    userSelections: List<Boolean>,
    showTiles: Boolean,
    canSubmit: Boolean,
    onClick: (index: Int) -> Unit
) {
    for (row in 0 until gridSize) {
        Row {
            for (column in 0 until gridSize) {
                val index = row * gridSize + column
                val tileColor = when {
                    showTiles && highlightedTiles[index] -> Color.Green
                    userSelections[index] -> Color.Blue  // Color for selected tiles
                    else -> Color.Gray
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(tileColor)
                        .border(1.dp, Color.Black)
                        .clickable(enabled = !showTiles && !canSubmit) { onClick(index) }
                        .padding(4.dp)
                )
            }
        }
    }
}