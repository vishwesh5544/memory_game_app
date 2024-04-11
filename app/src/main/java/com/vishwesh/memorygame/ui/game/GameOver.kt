package com.vishwesh.memorygame.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.vishwesh.memorygame.R

@Composable
fun GameOver(score: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val scoreLabel = stringResource(id = R.string.game_over, score)
        val restartGame = stringResource(id = R.string.restart_game)

        Text(scoreLabel, color = Color.Red)
        Button(onClick = onClick) {
            Text(restartGame)
        }
    }
}