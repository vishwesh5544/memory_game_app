package com.vishwesh.memorygame.viewmodelFactory

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.vishwesh.memorygame.viewmodel.GameViewModel
import com.vishwesh.memorygame.viewmodel.HighScoresViewModel

class HighScoresViewModelFactory(private val sharedPreferences: SharedPreferences, private val gson: Gson) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HighScoresViewModel(sharedPreferences, gson) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}