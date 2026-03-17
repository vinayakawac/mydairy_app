package com.example.mydairy_app.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TagChip(
    label: String,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
): Unit {
    if (onClick == null) {
        SuggestionChip(
            onClick = {},
            label = { Text(text = label) },
            modifier = modifier,
        )
    } else {
        FilterChip(
            selected = isSelected,
            onClick = onClick,
            label = { Text(text = label) },
            modifier = modifier,
        )
    }
}
