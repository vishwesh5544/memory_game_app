package com.vishwesh.memorygame.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import com.google.gson.Gson
import com.vishwesh.memorygame.viewmodel.GameViewModel

class GameViewModelFactory(private val sharedPreferences: SharedPreferences, private val gson: Gson) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(sharedPreferences, gson) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
