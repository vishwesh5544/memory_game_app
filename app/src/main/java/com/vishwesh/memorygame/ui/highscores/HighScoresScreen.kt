package com.vishwesh.memorygame.ui.highscores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vishwesh.memorygame.viewmodel.GameViewModel
import com.vishwesh.memorygame.viewmodel.HighScoresViewModel
import com.vishwesh.memorygame.R

@Composable
fun HighScoresScreen(viewModel: HighScoresViewModel) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val highScoresLabel = stringResource(id = R.string.high_scores_headline)
        val highScoresNotFoundLabel = stringResource(id = R.string.no_scores_found)

        Text(highScoresLabel, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.padding(25.dp))

        val highScores = viewModel.getHighScores()
        if (highScores.isEmpty()) {
            Text(highScoresNotFoundLabel)
            return
        } else {
            highScores.forEach {
                Text("${it.username}: ${it.score}")
            }
        }

    }
}