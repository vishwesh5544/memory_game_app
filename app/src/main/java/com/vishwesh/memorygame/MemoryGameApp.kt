@file:OptIn(ExperimentalMaterial3Api::class)

package com.vishwesh.memorygame

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vishwesh.memorygame.ui.game.GameScreen
import com.vishwesh.memorygame.ui.highscores.HighScoresScreen
import com.vishwesh.memorygame.ui.welcome.WelcomeScreen
import com.vishwesh.memorygame.viewmodel.GameViewModel


@Composable
fun MemoryGameApp() {
    val navController = rememberNavController()
    val viewModel: GameViewModel = viewModel()

    Scaffold(
        topBar = {
            MemoryGameTopAppBar()
        },
        floatingActionButton = {
            ExpandableFloatingActionButton(navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "welcome") {
                composable("welcome") { WelcomeScreen(navController, viewModel) }
                composable("game") { GameScreen(viewModel) }
                composable("highScores") { HighScoresScreen(viewModel) }
            }
        }
    }
}

@Composable
fun MemoryGameTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text("Memory Game") }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableFloatingActionButton(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(onClick = { navController.navigate("welcome") }) {
                    Icon(Icons.Default.Home, contentDescription = "Go to welcome")
                }
                Spacer(modifier = Modifier.height(16.dp))
                FloatingActionButton(onClick = { navController.navigate("game") }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Go to game")
                }
                Spacer(modifier = Modifier.height(16.dp))
                FloatingActionButton(onClick = { navController.navigate("highScores") }) {
                    Icon(Icons.Default.Star, contentDescription = "Go to high scores")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        FloatingActionButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.Close else Icons.Filled.Add,
                contentDescription = if (expanded) "Close menu" else "Open menu"
            )
        }
    }
}

// Add @Composable functions for WelcomeScreen, com.vishwesh.memorygame.ui.game.GameScreen, and HighScoresScreen here...
