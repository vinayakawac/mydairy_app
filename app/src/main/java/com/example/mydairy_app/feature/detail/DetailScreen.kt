package com.example.mydairy_app.feature.detail

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.components.StubScreen

@Composable
fun DetailScreen(
    entryId: Long,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
): Unit {
    StubScreen(
        titleRes = R.string.detail_title,
        onBack = onBack,
    ) {
        Text(text = stringResource(id = R.string.entry_id_value, entryId))
        Text(text = stringResource(id = R.string.stub_message))
        Button(onClick = { onEdit(entryId) }) {
            Text(text = stringResource(id = R.string.edit_entry))
        }
    }
}
