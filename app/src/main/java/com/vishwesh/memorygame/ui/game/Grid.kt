package com.vishwesh.memorygame.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Grid(
    gridSize: Int,
    highlightedTiles: List<Boolean>,
    userSelections: List<Boolean>,
    showTiles: Boolean,
    canSubmit: Boolean,
    onClick: (index: Int) -> Unit
) {
    for (row in 0 until gridSize) {
        Row {
            for (column in 0 until gridSize) {
                val index = row * gridSize + column
                val tileColor = when {
                    showTiles && highlightedTiles[index] && !canSubmit -> Color.Green
                    userSelections[index] -> Color.Blue  // Color for selected tiles
                    else -> Color.Gray
                }

                val clickable = (!showTiles && !canSubmit) || (canSubmit && !highlightedTiles[index])
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(tileColor)
                        .border(1.dp, Color.Black)
                        .clickable(
                            enabled = clickable
                        ) {
                            if (!showTiles || canSubmit) {
                                onClick(index)
                            }
                        }
                        .padding(4.dp)
                )
            }
        }
    }
}