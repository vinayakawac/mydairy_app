package com.example.mydairy_app.feature.tags

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.components.StubScreen

@Composable
fun TagManagerScreen(
    onBack: () -> Unit,
): Unit {
    StubScreen(
        titleRes = R.string.tags_title,
        onBack = onBack,
    ) {
        Text(text = stringResource(id = R.string.stub_message))
    }
}
