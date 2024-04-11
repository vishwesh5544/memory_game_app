package com.vishwesh.memorygame.ui.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vishwesh.memorygame.viewmodel.GameViewModel

@Composable
fun WelcomeScreen(navController: NavController, viewModel: GameViewModel) {
    var gameEnded = viewModel.gameEnded.value
    var playerName = viewModel.username.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Welcome to the Memory Game!", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Instructions:")
        Text("1. A 6x6 grid will display and highlight 4 tiles for 3 seconds for you to remember.")
        Text("2. You must then correctly pick the 4 highlighted tiles to continue.")
        Text("3. You must select the tiles within 5 seconds.")
        Text("4. If the wrong tile is selected or time is up, the game ends.")
        Text("5. Each round you select correctly, you receive 10 points.")
        Text("6. After 3 successful rounds, you will need to pick 5 tiles, and each correct selection gains 20 points.")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = playerName,
            onValueChange = { viewModel.username.value = it },
            label = { Text("Enter your name for high score") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("game")
        }) {
            Text("Play Game")
        }
    }
}




