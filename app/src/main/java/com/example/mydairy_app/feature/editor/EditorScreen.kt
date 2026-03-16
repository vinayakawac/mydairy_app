package com.example.mydairy_app.feature.editor

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.components.StubScreen

@Composable
fun EditorScreen(
    entryId: Long?,
    onBack: () -> Unit,
): Unit {
    val description = if (entryId == null) {
        stringResource(id = R.string.new_entry_mode)
    } else {
        stringResource(id = R.string.edit_entry_mode_value, entryId)
    }

    StubScreen(
        titleRes = R.string.editor_title,
        onBack = onBack,
    ) {
        Text(text = description)
        Text(text = stringResource(id = R.string.stub_message))
    }
}
