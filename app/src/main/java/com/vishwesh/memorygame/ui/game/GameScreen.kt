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

    val score by gameViewModel.score
    val gameEnded by gameViewModel.gameEnded
    val showTiles by gameViewModel.showTiles
    val canSubmit by gameViewModel.canSubmit
    val userSelections = gameViewModel.userSelections
    val timerValue by gameViewModel.timerValue.collectAsState()
    val showSubmitButton by gameViewModel.showSubmitButton

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
                Spacer(modifier = Modifier.size(48.dp)) // Placeholder for Timer
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (gameEnded) {
                GameOver(score = score, onClick = { gameViewModel.startGame() })
            } else {
                Spacer(modifier = Modifier.size(0.dp, 48.dp)) // Placeholder for GameOver
            }

            Spacer(modifier = Modifier.height(16.dp))

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

            if (!showTiles && !canSubmit && !gameEnded) {
                StartButton(onClick = { gameViewModel.startGame() })
            } else {
                Spacer(modifier = Modifier.size(0.dp, 48.dp)) // Placeholder for StartButton
            }

            if (showSubmitButton && !gameEnded) {
                SubmitButton(onClick = { gameViewModel.submitAnswers() })
            } else if (canSubmit) {
                SubmitButton(onClick = { gameViewModel.submitAnswers() })
            } else {
                Spacer(modifier = Modifier.size(0.dp, 48.dp)) // Placeholder for SubmitButton
            }
        }
    }
}








