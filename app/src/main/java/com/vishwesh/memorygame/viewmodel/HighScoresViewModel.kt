package com.vishwesh.memorygame.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vishwesh.memorygame.model.ScoreEntry

class HighScoresViewModel(private val preferences: SharedPreferences, private val gson: Gson) : ViewModel() {
    // Define constants and variables
    private val highScoresKey = "highScores" // Key for storing high scores in SharedPreferences
    private val highScoresType = object : TypeToken<List<ScoreEntry>>() {}.type // Type for Gson serialization

    // Function to retrieve high scores from SharedPreferences
    fun getHighScores(): List<ScoreEntry> {
        val highScoresJson = preferences.getString(highScoresKey, null) // Retrieve high scores JSON string
        return if (highScoresJson != null) {
            val list = gson.fromJson<List<ScoreEntry>>(highScoresJson, highScoresType) // Deserialize JSON string to List<ScoreEntry>
            list.sortedByDescending { it.score } // Sort high scores by score in descending order
        } else {
            emptyList() // Return empty list if no high scores are found
        }
    }

    // Function to save high scores to SharedPreferences
    fun saveHighScores(highScores: List<ScoreEntry>) {
        val highScoresJson = gson.toJson(highScores) // Serialize List<ScoreEntry> to JSON string
        preferences.edit().putString(highScoresKey, highScoresJson).apply() // Save JSON string to SharedPreferences
    }

}