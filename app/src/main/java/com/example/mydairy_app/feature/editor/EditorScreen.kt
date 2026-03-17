package com.example.mydairy_app.feature.editor

import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.components.PhotoGrid
import com.example.mydairy_app.ui.components.PhotoGridItemUiModel
import com.example.mydairy_app.ui.components.TagChip
import com.example.mydairy_app.ui.theme.MyDiaryDimens
import java.time.Instant
import java.time.ZoneId
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    viewModel: EditorViewModel = hiltViewModel(),
    entryId: Long?,
    onBack: () -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val effectiveEntryId = (uiState as? EditorUiState.Editing)?.entryId ?: entryId
    var cameraOutputUri by rememberSaveable { mutableStateOf<String?>(null) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    val legacyGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { selectedUri ->
        viewModel.onGalleryPhotoPicked(selectedUri?.toString())
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { selectedUri ->
        viewModel.onGalleryPhotoPicked(selectedUri?.toString())
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { isSuccess ->
        viewModel.onCameraCaptureResult(
            isSuccess = isSuccess,
            outputUri = cameraOutputUri,
        )
        cameraOutputUri = null
    }

    LaunchedEffect(viewModel, context) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                EditorUiEvent.NavigateBack -> onBack()

                is EditorUiEvent.LaunchCamera -> {
                    cameraOutputUri = event.outputUri
                    try {
                        cameraLauncher.launch(Uri.parse(event.outputUri))
                    } catch (_: ActivityNotFoundException) {
                        cameraOutputUri = null
                        viewModel.onCameraLaunchFailed()
                    }
                }

                is EditorUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.messageRes),
                    )
                }
            }
        }
    }

    val editingState = uiState as? EditorUiState.Editing
    BackHandler(enabled = editingState != null) {
        viewModel.onBackPressed()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (effectiveEntryId == null) {
                            stringResource(id = R.string.editor_title_new)
                        } else {
                            stringResource(id = R.string.editor_title_edit)
                        },
                    )
                },
                navigationIcon = {
                    TextButton(onClick = viewModel::onBackPressed) {
                        Text(text = stringResource(id = R.string.go_back))
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        when (val state = uiState) {
            EditorUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is EditorUiState.Error -> {
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
                        androidx.compose.material3.Text(text = stringResource(id = R.string.editor_error_title))
                        Button(onClick = viewModel::onRetryLoad) {
                            androidx.compose.material3.Text(text = stringResource(id = R.string.editor_retry))
                        }
                    }
                }
            }

            is EditorUiState.Editing -> {
                EditorContent(
                    state = state,
                    contentPadding = innerPadding,
                    onTitleChanged = viewModel::onTitleChanged,
                    onBodyChanged = viewModel::onBodyChanged,
                    onPickDate = { showDatePicker = true },
                    onPickTime = { showTimePicker = true },
                    onAddGallery = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            try {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly,
                                    ),
                                )
                            } catch (_: ActivityNotFoundException) {
                                try {
                                    legacyGalleryLauncher.launch(IMAGE_MIME_TYPE)
                                } catch (_: ActivityNotFoundException) {
                                    viewModel.onGalleryPickerFailed()
                                }
                            }
                        } else {
                            try {
                                legacyGalleryLauncher.launch(IMAGE_MIME_TYPE)
                            } catch (_: ActivityNotFoundException) {
                                viewModel.onGalleryPickerFailed()
                            }
                        }
                    },
                    onAddCamera = viewModel::onCameraCaptureRequested,
                    onOpenTagSheet = viewModel::onOpenTagSheet,
                    onRemovePhoto = viewModel::onRemovePhoto,
                    onSave = viewModel::onSaveClicked,
                )

                if (state.showDiscardDialog) {
                    AlertDialog(
                        onDismissRequest = viewModel::onDiscardDialogDismissed,
                        title = {
                            androidx.compose.material3.Text(text = stringResource(id = R.string.editor_discard_title))
                        },
                        text = {
                            androidx.compose.material3.Text(text = stringResource(id = R.string.editor_discard_message))
                        },
                        confirmButton = {
                            Button(onClick = viewModel::onDiscardConfirmed) {
                                androidx.compose.material3.Text(text = stringResource(id = R.string.editor_discard_confirm))
                            }
                        },
                        dismissButton = {
                            Button(onClick = viewModel::onDiscardDialogDismissed) {
                                androidx.compose.material3.Text(text = stringResource(id = R.string.editor_discard_cancel))
                            }
                        },
                    )
                }

                if (state.showTagSheet) {
                    ModalBottomSheet(
                        onDismissRequest = viewModel::onCloseTagSheet,
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimens.screenPadding),
                            contentPadding = PaddingValues(bottom = dimens.screenPadding),
                            verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                        ) {
                            items(state.tags, key = { tag -> tag.id }) { tag ->
                                FilterChip(
                                    selected = tag.isSelected,
                                    onClick = { viewModel.onToggleTagSelection(tag.id) },
                                    label = {
                                        androidx.compose.material3.Text(text = tag.name)
                                    },
                                )
                            }

                            item {
                                Button(
                                    onClick = viewModel::onCloseTagSheet,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    androidx.compose.material3.Text(text = stringResource(id = R.string.editor_tags_done))
                                }
                            }
                        }
                    }
                }

                if (showDatePicker) {
                    val datePickerState = androidx.compose.material3.rememberDatePickerState(
                        initialSelectedDateMillis = state.dateTimeMillis,
                    )
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            Button(onClick = {
                                val selectedMillis = datePickerState.selectedDateMillis
                                if (selectedMillis != null) {
                                    viewModel.onDatePicked(selectedMillis)
                                }
                                showDatePicker = false
                            }) {
                                androidx.compose.material3.Text(text = stringResource(id = R.string.editor_tags_done))
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDatePicker = false }) {
                                androidx.compose.material3.Text(text = stringResource(id = R.string.editor_discard_cancel))
                            }
                        },
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                if (showTimePicker) {
                    val selectedLocalDateTime = Instant
                        .ofEpochMilli(state.dateTimeMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                    LaunchedEffect(showTimePicker, state.dateTimeMillis) {
                        val dialog = TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                viewModel.onTimePicked(
                                    hourOfDay = hour,
                                    minute = minute,
                                )
                                showTimePicker = false
                            },
                            selectedLocalDateTime.hour,
                            selectedLocalDateTime.minute,
                            false,
                        )
                        dialog.setOnDismissListener {
                            showTimePicker = false
                        }
                        dialog.show()
                    }
                }
            }
        }
    }
}

@Composable
private fun EditorContent(
    state: EditorUiState.Editing,
    contentPadding: PaddingValues,
    onTitleChanged: (String) -> Unit,
    onBodyChanged: (String) -> Unit,
    onPickDate: () -> Unit,
    onPickTime: () -> Unit,
    onAddGallery: () -> Unit,
    onAddCamera: () -> Unit,
    onOpenTagSheet: () -> Unit,
    onRemovePhoto: (String) -> Unit,
    onSave: () -> Unit,
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
            OutlinedTextField(
                value = state.title,
                onValueChange = onTitleChanged,
                label = {
                    androidx.compose.material3.Text(text = stringResource(id = R.string.editor_field_title_label))
                },
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            OutlinedTextField(
                value = state.body,
                onValueChange = onBodyChanged,
                label = {
                    androidx.compose.material3.Text(text = stringResource(id = R.string.editor_field_body_label))
                },
                placeholder = {
                    androidx.compose.material3.Text(text = stringResource(id = R.string.editor_field_body_placeholder))
                },
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth(),
                minLines = BODY_FIELD_MIN_LINES,
            )
        }

        item {
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.editor_date_label, state.dateLabel),
            )
        }

        item {
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.editor_time_label, state.timeLabel),
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
            ) {
                Button(
                    onClick = onPickDate,
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f),
                ) {
                    androidx.compose.material3.Text(text = stringResource(id = R.string.editor_pick_date))
                }
                Button(
                    onClick = onPickTime,
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f),
                ) {
                    androidx.compose.material3.Text(text = stringResource(id = R.string.editor_pick_time))
                }
            }
        }

        item {
            androidx.compose.material3.Text(text = stringResource(id = R.string.editor_section_photos))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
            ) {
                Button(
                    onClick = onAddGallery,
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f),
                ) {
                    androidx.compose.material3.Text(text = stringResource(id = R.string.editor_add_gallery))
                }
                Button(
                    onClick = onAddCamera,
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f),
                ) {
                    androidx.compose.material3.Text(text = stringResource(id = R.string.editor_add_camera))
                }
            }
        }

        if (state.photos.isEmpty()) {
            item {
                androidx.compose.material3.Text(text = stringResource(id = R.string.editor_no_photos))
            }
        } else {
            item {
                PhotoGrid(
                    photos = state.photos.map { photo ->
                        PhotoGridItemUiModel(
                            key = photo.key,
                            source = photo.source,
                        )
                    },
                    onPhotoClick = {},
                    onRemovePhoto = onRemovePhoto,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.photoGridCellMinSize * PHOTO_GRID_ROWS),
                )
            }
        }

        item {
            androidx.compose.material3.Text(text = stringResource(id = R.string.editor_section_tags))
        }

        item {
            Button(
                onClick = onOpenTagSheet,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth(),
            ) {
                androidx.compose.material3.Text(text = stringResource(id = R.string.editor_select_tags))
            }
        }

        if (state.selectedTagNames.isEmpty()) {
            item {
                androidx.compose.material3.Text(text = stringResource(id = R.string.editor_no_tags))
            }
        } else {
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                ) {
                    state.selectedTagNames.forEach { tagName ->
                        TagChip(label = tagName)
                    }
                }
            }
        }

        item {
            Button(
                onClick = onSave,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth(),
            ) {
                androidx.compose.material3.Text(text = stringResource(id = R.string.editor_save))
            }
        }
    }
}

private object EditorScreenConstants {
    const val BODY_FIELD_MIN_LINES: Int = 5
    const val PHOTO_GRID_ROWS: Int = 2
    const val IMAGE_MIME_TYPE: String = "image/*"
}

private val BODY_FIELD_MIN_LINES: Int = EditorScreenConstants.BODY_FIELD_MIN_LINES
private val PHOTO_GRID_ROWS: Int = EditorScreenConstants.PHOTO_GRID_ROWS
private const val IMAGE_MIME_TYPE: String = EditorScreenConstants.IMAGE_MIME_TYPE
