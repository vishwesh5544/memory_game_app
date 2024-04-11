@file:OptIn(ExperimentalMaterial3Api::class)

package com.vishwesh.memorygame

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.compose.ui.platform.LocalContext
import com.google.gson.GsonBuilder
import com.vishwesh.memorygame.viewmodel.HighScoresViewModel
import com.vishwesh.memorygame.viewmodelFactory.GameViewModelFactory
import com.vishwesh.memorygame.viewmodelFactory.HighScoresViewModelFactory
import kotlinx.coroutines.launch


@Composable
fun MemoryGameApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val context = LocalContext.current

    val prefs = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
    val gson = GsonBuilder().create()

    val gameVmFactory = GameViewModelFactory(prefs, gson)
    val gameVm: GameViewModel = viewModel(factory = gameVmFactory)

    val highScoresViewModelFactory = HighScoresViewModelFactory(prefs, gson)
    val highScoresVm: HighScoresViewModel = viewModel(factory = highScoresViewModelFactory)

    CustomModalNavigationDrawer(scaffold = {
        Scaffold(
            topBar = {
                MemoryGameTopAppBar(drawerState = drawerState)
            },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { WelcomeScreen(navController, gameVm) }
                    composable("game") { GameScreen(gameVm) }
                    composable("highScores") { HighScoresScreen(highScoresVm) }
                }
            }
        }
    }, navController = navController, drawerState = drawerState)
}

@Composable
fun CustomModalNavigationDrawer(scaffold: @Composable () -> Unit, navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxSize(),
        drawerState = drawerState,
        drawerContent = { DrawerContent(navController, drawerState) },
    ) {
        scaffold()
    }

}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant

    ModalDrawerSheet {
        Text(
            text = "Navigation",
            style = typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(16.dp)
        )

        DrawerItem(
            icon = Icons.Default.Home,
            label = "Welcome",
            onClick = {
                navController.navigate("welcome")
                scope.launch { drawerState.close() }
            },
            colors = MaterialTheme.colorScheme
        )

        DrawerItem(
            icon = Icons.Default.PlayArrow,
            label = "Game",
            onClick = {
                navController.navigate("game")
                scope.launch { drawerState.close() }
            },
            colors = MaterialTheme.colorScheme
        )

        DrawerItem(
            icon = Icons.Default.Star,
            label = "High Scores",
            onClick = {
                navController.navigate("highScores")
                scope.launch { drawerState.close() }
            },
            colors = MaterialTheme.colorScheme
        )
    }
}


@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    colors: ColorScheme = MaterialTheme.colorScheme
) {
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = colors.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = typography.bodyLarge,
            color = colors.onSurface
        )
    }
}

@Composable
fun MemoryGameTopAppBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = { Text("Memory Game") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    if (drawerState.isOpen) {
                        drawerState.close()
                    } else {
                        drawerState.open()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        }

    )
}