package com.example.mydairy_app.ui.components

import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TagChip(
    label: String,
    modifier: Modifier = Modifier,
): Unit {
    SuggestionChip(
        onClick = {},
        label = { Text(text = label) },
        modifier = modifier,
    )
}
