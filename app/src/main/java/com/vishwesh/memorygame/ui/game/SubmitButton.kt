package com.vishwesh.memorygame.ui.game

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vishwesh.memorygame.R


@Composable
fun SubmitButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        val label = stringResource(id = R.string.submit_answers)
        Text(label)
    }
}