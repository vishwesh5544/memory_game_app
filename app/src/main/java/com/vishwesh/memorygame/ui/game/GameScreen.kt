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

@Composable
fun GameScreen() {
    val gameViewModel: GameViewModel = viewModel()

    // Observing state variables from GameViewModel
    val score by gameViewModel.score // Current score
    val gameEnded by gameViewModel.gameEnded // Flag indicating if the game has ended
    val showTiles by gameViewModel.showTiles // Flag to show or hide tiles
    val canSubmit by gameViewModel.canSubmit // Flag to allow submission of answers
    val userSelections = gameViewModel.userSelections // List of user-selected tiles
    val timerValue by gameViewModel.timerValue.collectAsState() // Timer value for UI
    val showSubmitButton by gameViewModel.showSubmitButton // Flag to show or hide the submit button

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display current score
            Text("Score: $score")

            // Start button when game hasn't started or ended
            if (!showTiles && !canSubmit && !gameEnded) {
                StartButton(onClick = { gameViewModel.startGame() })
            }

            // Display instructions during memorization phase or answer phase
            if (showTiles || canSubmit) {
                Text("Memorize tiles for 3 seconds")
                Timer(timerValue)
            }

            // Display submit button during answer phase
            if (showSubmitButton && !gameEnded) {
                SubmitButton(onClick = { gameViewModel.submitAnswers() })
            }

            // Display submit button again during answer phase if needed
            if (canSubmit) {
                SubmitButton(onClick = { gameViewModel.submitAnswers() })
            }

            // Display game over screen when game has ended
            if (gameEnded) {
                GameOver(score = score, onClick = { gameViewModel.startGame() })
            }

            // Display grid of tiles during game rounds
            if (!gameEnded) {
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
fun StartButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text("Start Game")
    }
}

@Composable
fun SubmitButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text("Submit Answers")
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
fun GameOver(score: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Game Over! Your score was $score", color = Color.Red)
        Button(onClick = onClick) {
            Text("Restart Game")
        }
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

                val clickable = (!showTiles && !canSubmit) || (canSubmit && !highlightedTiles[index])
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(tileColor)
                        .border(1.dp, Color.Black)
                        .clickable(
                            enabled = clickable
                        ) {
                            if (!showTiles || canSubmit) {
                                onClick(index)
                            }
                        }
                        .padding(4.dp)
                )
            }
        }
    }
}

