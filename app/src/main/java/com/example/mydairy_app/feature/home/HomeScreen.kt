package com.example.mydairy_app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.components.EntryCard
import com.example.mydairy_app.ui.theme.MyDiaryDimens

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onOpenEditor: (Long?) -> Unit,
    onOpenDetail: (Long) -> Unit,
    onOpenCalendar: () -> Unit,
    onOpenTagManager: () -> Unit,
    onOpenSettings: () -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_title)) },
                actions = {
                    TextButton(onClick = onOpenCalendar) {
                        Text(text = stringResource(id = R.string.open_calendar))
                    }
                    TextButton(onClick = onOpenTagManager) {
                        Text(text = stringResource(id = R.string.open_tags))
                    }
                    TextButton(onClick = onOpenSettings) {
                        Text(text = stringResource(id = R.string.open_settings))
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onOpenEditor(null) }) {
                Text(text = stringResource(id = R.string.home_new_entry_fab))
            }
        },
    ) { innerPadding ->
        when (val state = uiState) {
            HomeUiState.Loading -> {
                LoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            is HomeUiState.Error -> {
                ErrorState(
                    onRetry = viewModel::onRetryLoad,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(dimens.screenPadding),
                )
            }

            is HomeUiState.Success -> {
                if (state.sections.isEmpty()) {
                    EmptyState(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(dimens.screenPadding),
                    )
                } else {
                    EntrySectionList(
                        sections = state.sections,
                        onOpenDetail = onOpenDetail,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
private fun EntrySectionList(
    sections: List<HomeSectionUiModel>,
    onOpenDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimens.screenPadding),
        verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
    ) {
        sections.forEach { section ->
            item(key = section.dateLabel) {
                Text(
                    text = section.dateLabel,
                )
            }

            items(
                items = section.entries,
                key = { entry -> entry.id },
            ) { entry ->
                EntryCard(
                    title = entry.title,
                    bodyPreview = entry.bodyPreview,
                    createdAtLabel = entry.createdAtLabel,
                    tags = entry.tags,
                    firstPhotoPath = entry.firstPhotoPath,
                    onClick = { onOpenDetail(entry.id) },
                )
            }
        }
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier,
): Unit {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        ) {
            Text(
                text = stringResource(id = R.string.home_empty_title),
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(id = R.string.home_empty_description),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun ErrorState(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        ) {
            Text(
                text = stringResource(id = R.string.home_error_title),
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(id = R.string.home_error_description),
                textAlign = TextAlign.Center,
            )
            Button(onClick = onRetry) {
                Text(text = stringResource(id = R.string.home_retry))
            }
        }
    }
}
