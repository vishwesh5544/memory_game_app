package com.vishwesh.memorygame.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vishwesh.memorygame.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel) {

    val score by viewModel.score
    val gameEnded by viewModel.gameEnded
    val showTiles by viewModel.showTiles
    val canSubmit by viewModel.canSubmit
    val userSelections = viewModel.userSelections
    val timerValue by viewModel.timerValue.collectAsState()
    val showSubmitButton by viewModel.showSubmitButton

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Score: $score")
            Spacer(modifier = Modifier.height(16.dp))

            // Always present to keep layout stable, visibility toggled
            if (showTiles || canSubmit) {
                Text("Memorize tiles for 3 seconds")
                Timer(timerValue)
            } else {
                Spacer(modifier = Modifier.size(60.dp)) // Placeholder for Timer
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (gameEnded) {
                GameOver(score = score, onClick = { viewModel.startGame() })
            } else {
                Spacer(modifier = Modifier.size(0.dp, 60.dp)) // Placeholder for GameOver
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!gameEnded) {
                Grid(
                    gridSize = 6,
                    highlightedTiles = viewModel.highlightedTiles,
                    userSelections = userSelections,
                    showTiles = showTiles,
                    canSubmit = canSubmit,
                    onClick = { index -> viewModel.toggleTileSelection(index) }
                )
            }

            if (!showTiles && !canSubmit && !gameEnded) {
                StartButton(onClick = { viewModel.startGame() })
            } else {
                Spacer(modifier = Modifier.size(0.dp, 60.dp)) // Placeholder for StartButton
            }

            if (showSubmitButton && !gameEnded) {
                SubmitButton(onClick = { viewModel.submitAnswers() })
            } else if (canSubmit) {
                SubmitButton(onClick = { viewModel.submitAnswers() })
            } else {
                Spacer(modifier = Modifier.size(0.dp, 60.dp)) // Placeholder for SubmitButton
            }
        }
    }
}








