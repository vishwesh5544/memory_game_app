@file:OptIn(ExperimentalMaterial3Api::class)

package com.vishwesh.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vishwesh.memorygame.ui.game.GameScreen
import com.vishwesh.memorygame.ui.highscores.HighScoresScreen
import com.vishwesh.memorygame.ui.welcome.WelcomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MemoryGameApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState, scope)
        },
    ) {
        Scaffold(
            topBar = {
                MemoryGameTopAppBar(drawerState = drawerState, scope = scope)
            }

        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { WelcomeScreen() }
                    composable("game") { GameScreen() }
                    composable("highScores") { HighScoresScreen() }
                }
            }
        }
    }
}

@Composable
fun MemoryGameTopAppBar(drawerState: DrawerState, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        title = { Text("Memory Game") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() }
            }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState, scope: CoroutineScope) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .padding(top = with(LocalDensity.current) { WindowInsets.statusBars.asPaddingValues().calculateTopPadding() }),
    ) {
        DrawerHeader()
        Spacer(modifier = Modifier.height(8.dp))  // Add a spacer for visual separation
        DrawerItem(label = "Welcome", icon = Icons.Filled.Home, onClick = {
            navController.navigate("welcome")
            scope.launch { drawerState.close() }
        })
        DrawerItem(label = "Game", icon = Icons.Filled.Home, onClick = {
            navController.navigate("game")
            scope.launch { drawerState.close() }
        })
        DrawerItem(label = "High Scores", icon = Icons.Filled.Home, onClick = {
            navController.navigate("highScores")
            scope.launch { drawerState.close() }
        })
    }
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp) // Add space below the header
        )
    }}

@Composable
fun DrawerItem(label: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp), // Padding around each item for touch target and spacing
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp) // Icon size
        )
        Spacer(Modifier.width(16.dp)) // Space between icon and text
        Text(text = label)
    }
}

