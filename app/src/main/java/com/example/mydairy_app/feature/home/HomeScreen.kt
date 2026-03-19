package com.example.mydairy_app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.components.EntryCard
import com.example.mydairy_app.ui.components.TagChip
import com.example.mydairy_app.ui.theme.MyDiaryDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onOpenEditor: (Long?) -> Unit,
    onOpenDetail: (Long) -> Unit,
    onOpenCalendar: () -> Unit,
    onOpenTagManager: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenAgent: () -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_title)) },
                actions = {
                    TextButton(onClick = onOpenCalendar) {
                        Text(text = stringResource(id = R.string.calendar_title))
                    }
                    TextButton(onClick = onOpenTagManager) {
                        Text(text = stringResource(id = R.string.tags_title))
                    }
                    TextButton(onClick = onOpenSettings) {
                        Text(text = stringResource(id = R.string.settings_title))
                    }
                    IconButton(
                        onClick = {
                            viewModel.onSearchExpandedChanged(
                                expanded = !uiState.isSearchExpanded,
                            )
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.home_search_open),
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(FabSpacing),
            ) {
                SmallFloatingActionButton(onClick = onOpenAgent) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = null,
                    )
                }

                FloatingActionButton(onClick = { onOpenEditor(null) }) {
                    Text(text = stringResource(id = R.string.home_new_entry_fab))
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            if (uiState.isSearchExpanded) {
                SearchField(
                    query = uiState.searchQuery,
                    onQueryChange = viewModel::onSearchQueryChanged,
                    onClear = viewModel::onClearSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimens.screenPadding)
                        .padding(top = dimens.itemSpacing),
                )
            }

            if (uiState.availableTags.isNotEmpty()) {
                TagFiltersRow(
                    tags = uiState.availableTags,
                    selectedTagId = uiState.selectedTagId,
                    onSelectTag = viewModel::onTagFilterSelected,
                    onSelectAll = viewModel::onClearTagFilter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = TagFiltersRowHorizontalPadding,
                            end = TagFiltersRowHorizontalPadding,
                            top = TagFiltersRowTopPadding,
                        ),
                )
            }

            val selectedDateFilterLabel = uiState.selectedDateFilterLabel
            if (selectedDateFilterLabel != null) {
                DateFilterBar(
                    dateLabel = selectedDateFilterLabel,
                    onClear = viewModel::onDateFilterCleared,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimens.screenPadding)
                        .padding(top = dimens.itemSpacing),
                )
            }

            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    LoadingState(
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }

                is HomeUiState.Error -> {
                    ErrorState(
                        onRetry = viewModel::onRetryLoad,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimens.screenPadding),
                    )
                }

                is HomeUiState.Success -> {
                    if (state.sections.isEmpty()) {
                        if (state.searchQuery.isBlank() && state.selectedTagId == null && state.selectedDateFilterLabel == null) {
                            EmptyState(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(dimens.screenPadding),
                            )
                        } else {
                            SearchEmptyState(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(dimens.screenPadding),
                            )
                        }
                    } else {
                        EntrySectionList(
                            sections = state.sections,
                            onOpenDetail = onOpenDetail,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DateFilterBar(
    dateLabel: String,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = stringResource(id = R.string.home_date_filter_active, dateLabel))
        TextButton(onClick = onClear) {
            Text(text = stringResource(id = R.string.home_date_filter_clear))
        }
    }
}

@Composable
private fun TagFiltersRow(
    tags: List<HomeTagFilterUiModel>,
    selectedTagId: Long?,
    onSelectTag: (Long?) -> Unit,
    onSelectAll: () -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        item {
            TagChip(
                label = stringResource(id = R.string.home_filter_all),
                isSelected = selectedTagId == null,
                onClick = onSelectAll,
            )
        }

        items(tags, key = { tag -> tag.id }) { tag ->
            TagChip(
                label = tag.name,
                isSelected = selectedTagId == tag.id,
                onClick = {
                    onSelectTag(tag.id)
                },
            )
        }
    }
}

@Composable
private fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        singleLine = true,
        placeholder = {
            Text(text = stringResource(id = R.string.home_search_placeholder))
        },
        trailingIcon = {
            if (query.isNotBlank()) {
                TextButton(onClick = onClear) {
                    Text(text = stringResource(id = R.string.home_search_clear))
                }
            }
        },
    )
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
private fun SearchEmptyState(
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
                text = stringResource(id = R.string.home_search_empty_title),
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(id = R.string.home_search_empty_description),
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

private val HomeUiState.searchQuery: String
    get() {
        return when (this) {
            is HomeUiState.Loading -> searchQuery
            is HomeUiState.Success -> searchQuery
            is HomeUiState.Error -> searchQuery
        }
    }

private val HomeUiState.isSearchExpanded: Boolean
    get() {
        return when (this) {
            is HomeUiState.Loading -> isSearchExpanded
            is HomeUiState.Success -> isSearchExpanded
            is HomeUiState.Error -> isSearchExpanded
        }
    }

private val HomeUiState.availableTags: List<HomeTagFilterUiModel>
    get() {
        return when (this) {
            is HomeUiState.Loading -> availableTags
            is HomeUiState.Success -> availableTags
            is HomeUiState.Error -> availableTags
        }
    }

private val HomeUiState.selectedTagId: Long?
    get() {
        return when (this) {
            is HomeUiState.Loading -> selectedTagId
            is HomeUiState.Success -> selectedTagId
            is HomeUiState.Error -> selectedTagId
        }
    }

private val HomeUiState.selectedDateFilterLabel: String?
    get() {
        return when (this) {
            is HomeUiState.Loading -> selectedDateFilterLabel
            is HomeUiState.Success -> selectedDateFilterLabel
            is HomeUiState.Error -> selectedDateFilterLabel
        }
    }

private val TagFiltersRowHorizontalPadding = 16.dp
private val TagFiltersRowTopPadding = 8.dp
private val FabSpacing = 12.dp
