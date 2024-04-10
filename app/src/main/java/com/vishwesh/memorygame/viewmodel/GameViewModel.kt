package com.vishwesh.memorygame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class GameViewModel : ViewModel() {
    // Define constants and variables
    private val gridSize = 6 // Size of the grid
    private var numberOfTilesToHighlight = 4 // Number of tiles to highlight initially
    private var round = 1 // Current round number
    private val displayTime = 3 // Time to display highlighted tiles for memorization
    private val answerTime = 5 // Time to answer after tiles are hidden
    private val maxRounds = 3 // Maximum number of rounds

    // State variables using MutableStateFlow and MutableStateList for Compose UI
    var score = mutableStateOf(0)
    var gameEnded = mutableStateOf(false)
    var showTiles = mutableStateOf(false)
    var canSubmit = mutableStateOf(false)
    var timerValue: MutableStateFlow<Int> = MutableStateFlow(0)
    var showSubmitButton = mutableStateOf(false)

    // Lists to track highlighted tiles and user selections
    var highlightedTiles = mutableStateListOf<Boolean>()
    var userSelections = mutableStateListOf<Boolean>()

    init {
        initializeGame() // Initialize the game state
    }

    private fun initializeGame() {
        // Clear and initialize lists, reset game state
        highlightedTiles.clear()
        highlightedTiles.addAll(List(gridSize * gridSize) { false })
        userSelections.clear()
        userSelections.addAll(List(gridSize * gridSize) { false })
        gameEnded.value = false
        showTiles.value = false
        canSubmit.value = false
    }

    fun startGame() {
        score.value = 0 // Reset score
        round = 1 // Reset round number
        numberOfTilesToHighlight = 4 // Reset number of tiles to highlight
        prepareGameRound() // Prepare for the first round
    }

    fun toggleTileSelection(index: Int) {
        print("*** tile index: $index\n")
        highlightedTiles[index] = !highlightedTiles[index]
        userSelections[index] = !userSelections[index]
    }


    private fun prepareGameRound() {
        initializeGame() // Initialize for each round
        showTiles.value = true // Show the tiles for memorization
        canSubmit.value = false // Set submission to false initially

        // Randomly select and highlight tiles
        val indicesToHighlight = highlightedTiles.indices.shuffled().take(numberOfTilesToHighlight)
        indicesToHighlight.forEach { index ->
            highlightedTiles[index] = true
        }

        viewModelScope.launch {
            for (i in displayTime downTo 1) {
                timerValue.value = i // Update the timer value for UI
                delay(1000) // Delay for 1 second
            }

            highlightedTiles.clear();
            highlightedTiles.addAll(List(gridSize * gridSize) { false })
            canSubmit.value = true // Allow submission after display time

            for (i in answerTime downTo 1) {
                timerValue.value = i // Update the timer value for UI
                delay(1000) // Delay for 1 second
            }

            canSubmit.value = false // Disallow submission after answer time

            if (!canSubmit.value) {
                submitAnswers() // If submission time is up, submit answers automatically
            }

        }
    }


    fun submitAnswers() {
        canSubmit.value = true // Allow submission
        // Count the number of correct selections
        val correctCount = userSelections.indices.count { userSelections[it] && highlightedTiles[it] }

        if (correctCount == numberOfTilesToHighlight) {
            // If all correct, update score and prepare for the next round
            score.value += round * 10
            if (round % maxRounds == 0) numberOfTilesToHighlight++
            round++
            if (round <= maxRounds) {
                prepareGameRound()
            } else {
                gameEnded.value = true // Game ends if maximum rounds reached
            }
        } else {
            gameEnded.value = true // Game ends if incorrect selection or time up
        }
    }
}
