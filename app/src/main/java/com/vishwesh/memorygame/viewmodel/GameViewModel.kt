package com.vishwesh.memorygame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class GameViewModel : ViewModel() {
    private val gridSize = 6
    private var numberOfTilesToHighlight = 4
    private var round = 1
    private val displayTime = 3// time to memorize tiles
    private val answerTime = 5 // time to select tiles
    private val maxRounds = 3

    var score = mutableStateOf(0)
    var gameEnded = mutableStateOf(false)
    var showTiles = mutableStateOf(false)
    var canSubmit = mutableStateOf(false)
    var timerValue: MutableStateFlow<Int> = MutableStateFlow(0)

    var highlightedTiles = mutableStateListOf<Boolean>()
    var userSelections = mutableStateListOf<Boolean>()

    init {
        initializeGame()
    }

    private fun initializeGame() {
        highlightedTiles.clear()
        highlightedTiles.addAll(List(gridSize * gridSize) { false })
        userSelections.clear()
        userSelections.addAll(List(gridSize * gridSize) { false })
        gameEnded.value = false
        showTiles.value = false
        canSubmit.value = false
    }

    fun startGame() {
        score.value = 0
        round = 1
        numberOfTilesToHighlight = 4
        prepareGameRound()
    }

    fun toggleTileSelection(index: Int) {
        if (!showTiles.value && !canSubmit.value) {
            // Toggle the selection state of the tile
            userSelections[index] = !userSelections[index]
        }
    }

    private fun prepareGameRound() {
        initializeGame()
        showTiles.value = true
        canSubmit.value = false
        viewModelScope.launch {
            for (i in displayTime  downTo 0) {
                timerValue.value = i
                delay(1000)
            }
            showTiles.value = false
            for (i in answerTime  downTo 0) {
                timerValue.value = i
                delay(1000)
            }
            if (!canSubmit.value) {
                submitAnswers()
            }
        }
    }

    fun submitAnswers() {
        canSubmit.value = true
        val correctCount = userSelections.indices.count { userSelections[it] && highlightedTiles[it] }

        if (correctCount == numberOfTilesToHighlight) {
            score.value += round * 10
            if (round % maxRounds == 0) numberOfTilesToHighlight++
            round++
            if (round <= maxRounds) {
                prepareGameRound()
            } else {
                gameEnded.value = true
            }
        } else {
            gameEnded.value = true
        }
    }
}