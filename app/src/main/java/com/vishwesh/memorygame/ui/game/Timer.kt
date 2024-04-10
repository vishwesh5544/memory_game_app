package com.vishwesh.memorygame.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun Timer(timerValue: Int) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.Gray)
            .border(1.dp, Color.Black)
            .padding(4.dp)
    ) {
        Text(
            text = "$timerValue",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}