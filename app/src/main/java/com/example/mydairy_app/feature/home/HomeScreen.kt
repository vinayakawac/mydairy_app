package com.example.mydairy_app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.navigation.Screen
import com.example.mydairy_app.ui.theme.MyDiaryDimens

@Composable
fun HomeScreen(
    onOpenEditor: (Long?) -> Unit,
    onOpenDetail: (Long) -> Unit,
    onOpenCalendar: () -> Unit,
    onOpenTagManager: () -> Unit,
    onOpenSettings: () -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_title)) },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
        ) {
            Button(onClick = { onOpenEditor(null) }) {
                Text(text = stringResource(id = R.string.open_editor_new))
            }

            Button(onClick = { onOpenDetail(Screen.SAMPLE_ENTRY_ID) }) {
                Text(text = stringResource(id = R.string.open_detail_sample))
            }

            Button(onClick = onOpenCalendar) {
                Text(text = stringResource(id = R.string.open_calendar))
            }

            Button(onClick = onOpenTagManager) {
                Text(text = stringResource(id = R.string.open_tags))
            }

            Button(onClick = onOpenSettings) {
                Text(text = stringResource(id = R.string.open_settings))
            }
        }
    }
}
