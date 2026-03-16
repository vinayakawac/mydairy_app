package com.example.mydairy_app.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.theme.MyDiaryDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StubScreen(
    @StringRes titleRes: Int,
    onBack: (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    val dimens = MyDiaryDimens.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = titleRes)) },
                navigationIcon = {
                    if (onBack != null) {
                        TextButton(onClick = onBack) {
                            Text(text = stringResource(id = R.string.go_back))
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
            content = content,
        )
    }
}
