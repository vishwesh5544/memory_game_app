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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vishwesh.memorygame.R
import com.vishwesh.memorygame.viewmodel.GameViewModel

@Composable
fun WelcomeScreen(navController: NavController, viewModel: GameViewModel) {
    val username = viewModel.username.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val welcomeLabel = stringResource(id = R.string.welcome_label)
        val instructionsLabel = stringResource(id = R.string.instructions_heading)
        val instructionsOne = stringResource(id = R.string.instruction_1)
        val instructionsTwo = stringResource(id = R.string.instruction_2)
        val instructionsThree = stringResource(id = R.string.instruction_3)
        val instructionsFour = stringResource(id = R.string.instruction_4)
        val instructionsFive = stringResource(id = R.string.instruction_5)
        val instructionsSix = stringResource(id = R.string.instruction_6)
        val addUserLabel = stringResource(id = R.string.enter_name)
        val playGameLabel = stringResource(id = R.string.play_game)



        Text(welcomeLabel, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(instructionsLabel)
        Text(instructionsOne)
        Text(instructionsTwo)
        Text(instructionsThree)
        Text(instructionsFour)
        Text(instructionsFive)
        Text(instructionsSix)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { viewModel.username.value = it },
            label = { Text(addUserLabel) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("game")
        }) {
            Text(playGameLabel)
        }
    }
}




