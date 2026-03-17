package com.example.mydairy_app.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.components.PhotoGrid
import com.example.mydairy_app.ui.components.PhotoGridItemUiModel
import com.example.mydairy_app.ui.components.TagChip
import com.example.mydairy_app.ui.theme.MyDiaryDimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
): Unit {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val dimens = MyDiaryDimens.current
    var previewPhotoPath by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel, context) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                DetailUiEvent.NavigateBack -> onBack()

                is DetailUiEvent.NavigateEdit -> onEdit(event.entryId)

                is DetailUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.messageRes),
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.detail_title))
                },
                navigationIcon = {
                    TextButton(onClick = viewModel::onBackClicked) {
                        Text(text = stringResource(id = R.string.go_back))
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            val state = uiState as? DetailUiState.Success
            if (state != null && !state.isDeleting) {
                FloatingActionButton(onClick = viewModel::onEditClicked) {
                    Text(text = stringResource(id = R.string.edit_entry))
                }
            }
        },
    ) { innerPadding ->
        when (val state = uiState) {
            DetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(dimens.screenPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                    ) {
                        Text(text = stringResource(id = R.string.detail_error_title))
                        Button(onClick = viewModel::onRetryLoad) {
                            Text(text = stringResource(id = R.string.detail_retry))
                        }
                    }
                }
            }

            is DetailUiState.Success -> {
                DetailContent(
                    state = state,
                    contentPadding = innerPadding,
                    onDelete = viewModel::onDeleteClicked,
                    onPhotoClick = { path ->
                        previewPhotoPath = path
                    },
                )

                if (state.showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = viewModel::onDeleteDialogDismissed,
                        title = {
                            Text(text = stringResource(id = R.string.detail_delete_title))
                        },
                        text = {
                            Text(text = stringResource(id = R.string.detail_delete_message))
                        },
                        confirmButton = {
                            Button(onClick = viewModel::onDeleteConfirmed) {
                                Text(text = stringResource(id = R.string.detail_delete_confirm))
                            }
                        },
                        dismissButton = {
                            Button(onClick = viewModel::onDeleteDialogDismissed) {
                                Text(text = stringResource(id = R.string.detail_delete_cancel))
                            }
                        },
                    )
                }
            }
        }
    }

    if (previewPhotoPath != null) {
        AlertDialog(
            onDismissRequest = {
                previewPhotoPath = null
            },
            title = {
                Text(text = stringResource(id = R.string.detail_photo_preview_title))
            },
            text = {
                AsyncImage(
                    model = previewPhotoPath,
                    contentDescription = stringResource(id = R.string.entry_photo_content_description),
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                    error = painterResource(id = android.R.drawable.ic_menu_report_image),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            confirmButton = {
                Button(onClick = {
                    previewPhotoPath = null
                }) {
                    Text(text = stringResource(id = R.string.detail_photo_preview_close))
                }
            },
        )
    }
}

@Composable
private fun DetailContent(
    state: DetailUiState.Success,
    contentPadding: PaddingValues,
    onDelete: () -> Unit,
    onPhotoClick: (String) -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentPadding = PaddingValues(dimens.screenPadding),
        verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
    ) {
        item {
            Text(
                text = state.entry.title?.takeIf(String::isNotBlank)
                    ?: stringResource(id = R.string.entry_untitled_title),
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.detail_created_at, state.entry.createdAtLabel),
            )
        }

        item {
            Text(text = state.entry.body)
        }

        item {
            Text(text = stringResource(id = R.string.detail_photos_section))
        }

        if (state.entry.photoPaths.isEmpty()) {
            item {
                Text(text = stringResource(id = R.string.detail_no_photos))
            }
        } else {
            item {
                PhotoGrid(
                    photos = state.entry.photoPaths.mapIndexed { index, path ->
                        PhotoGridItemUiModel(
                            key = "detail_$index",
                            source = path,
                        )
                    },
                    onPhotoClick = onPhotoClick,
                    onRemovePhoto = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.photoGridCellMinSize * PHOTO_GRID_ROWS),
                )
            }
        }

        item {
            Text(text = stringResource(id = R.string.detail_tags_section))
        }

        if (state.entry.tags.isEmpty()) {
            item {
                Text(text = stringResource(id = R.string.detail_no_tags))
            }
        } else {
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                ) {
                    state.entry.tags.forEach { tagName ->
                        TagChip(label = tagName)
                    }
                }
            }
        }

        item {
            Button(
                onClick = onDelete,
                enabled = !state.isDeleting,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(id = R.string.detail_delete_button))
            }
        }
    }
}

private const val PHOTO_GRID_ROWS: Int = 2
