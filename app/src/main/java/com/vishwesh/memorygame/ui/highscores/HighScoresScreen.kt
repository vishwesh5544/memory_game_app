package com.vishwesh.memorygame.ui.highscores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vishwesh.memorygame.viewmodel.GameViewModel
import com.vishwesh.memorygame.viewmodel.HighScoresViewModel

@Composable
fun HighScoresScreen(viewModel: HighScoresViewModel) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("High Scores Screen", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.padding(25.dp))

        val highScores = viewModel.getHighScores()
        if (highScores.isEmpty()) {
            Text("No high scores found")
            return
        } else {
            highScores.forEach {
                Text("${it.username}: ${it.score}")
            }
        }

    }
}