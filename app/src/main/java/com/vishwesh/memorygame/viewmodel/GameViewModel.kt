package com.vishwesh.memorygame.viewmodel

import android.content.SharedPreferences
import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vishwesh.memorygame.model.ScoreEntry

class GameViewModel(private val prefs: SharedPreferences, private val gson: Gson) : ViewModel() {
    // Define constants and variables
    private val gridSize = 6 // Size of the grid
    private var numberOfTilesToHighlight = 4 // Number of tiles to highlight initially
    private var round = 1 // Current round number
    private val questionTime = 3000L // Time to display highlighted tiles for memorization
    private val answerTime = 5000L // Time to answer after tiles are hidden
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
    var correctAnswers = mutableStateListOf<Boolean>()
    var userSelections = mutableStateListOf<Boolean>()

    var username = mutableStateOf("")

    val COUNTDOWN_INTERVAL = 1000L
    var questionTimer: CountDownTimer? = null
    var answerTimer: CountDownTimer? = null

    init {
        initializeGame() // Initialize the game state
    }

    private fun initializeGame() {
        // Clear and initialize lists, reset game state
        highlightedTiles.clear()
        highlightedTiles.addAll(List(gridSize * gridSize) { false })
        userSelections.clear()
        userSelections.addAll(List(gridSize * gridSize) { false })
        correctAnswers.clear()
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
        userSelections[index] = !userSelections[index]
    }


    private fun prepareGameRound() {
        timerValue.value = questionTime.toInt() // Reset the timer to full display time at the start of the round
        initializeGame() // Initialize for each round
        showTiles.value = true // Show the tiles for memorization
        canSubmit.value = false // Set submission to false initially

        // Randomly select and highlight tiles
        val indicesToHighlight = highlightedTiles.indices.shuffled().take(numberOfTilesToHighlight)
        indicesToHighlight.forEach { index ->
            highlightedTiles[index] = true
        }

        viewModelScope.launch {
            /// answer timer
            answerTimer = object : CountDownTimer(answerTime, COUNTDOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    timerValue.value = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                    canSubmit.value = false // Disallow submission after answer time

                    if (!canSubmit.value) {
                        submitAnswers() // If submission time is up, submit answers automatically
                    }
                }
            }

            questionTimer = object : CountDownTimer(questionTime, COUNTDOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    timerValue.value = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                    correctAnswers.addAll(highlightedTiles)
                    highlightedTiles.clear();
                    highlightedTiles.addAll(List(gridSize * gridSize) { false })
                    canSubmit.value = true // Allow submission after display time

                    answerTimer!!.start()
                }
            }

            questionTimer!!.start()
        }
    }

    private fun gameEnd() {
        gameEnded.value = true
        timerValue.value = 0

        // save to json
        if (username.value.isNotEmpty()) {
            saveScore(username.value, score.value)
        }
    }


    fun submitAnswers() {
        answerTimer!!.cancel()
        canSubmit.value = true // Allow submission
        // Count the number of correct selections
        val correctCount = userSelections.indices.count { userSelections[it] && correctAnswers[it] }


        if (correctCount == numberOfTilesToHighlight) {
            // If all correct, update score and prepare for the next round
            score.value += round * 10

//            if (round % maxRounds == 0) numberOfTilesToHighlight++

            round++

            if (round > maxRounds && numberOfTilesToHighlight == 4) {
                // Increase the number of tiles to highlight after the initial rounds
                numberOfTilesToHighlight = 5
            }

//            if (round <= maxRounds) {
//                prepareGameRound()
//            } else gameEnd()
            prepareGameRound()
        } else gameEnd()
    }

    private fun saveScore(username: String, score: Int) {
        val scoresJson = prefs.getString("scores", "[]")
        val type = object : TypeToken<List<ScoreEntry>>() {}.type
        val scores = gson.fromJson<List<ScoreEntry>>(scoresJson, type).toMutableList()

        scores.add(ScoreEntry(username, score))

        val editor = prefs.edit()
        editor.putString("scores", gson.toJson(scores))
        editor.apply()
    }
}
