package com.example.mydairy_app.feature.agent

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.speech.RecognizerIntent
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.theme.MyDiaryDimens
import kotlinx.coroutines.flow.collectLatest
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentScreen(
    viewModel: AgentViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateHome: (String?, String?, Long?) -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val readyState = uiState.toReadyState()
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    var cameraOutputUri by rememberSaveable { mutableStateOf<String?>(null) }

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

    val voiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        val transcript = result.data
            ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()
        if (transcript.isNullOrBlank()) {
            viewModel.onVoiceCaptureFailed()
            return@rememberLauncherForActivityResult
        }

        viewModel.onVoiceTranscriptReceived(transcript)
    }

    val launchVoiceRecognition: () -> Unit = {
        val voiceIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        }
        try {
            voiceLauncher.launch(voiceIntent)
        } catch (_: ActivityNotFoundException) {
            viewModel.onVoiceCaptureFailed()
        }
    }

    val voicePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            launchVoiceRecognition()
        } else {
            viewModel.onVoicePermissionDenied()
        }
    }

    LaunchedEffect(viewModel, context) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is AgentUiEvent.LaunchCamera -> {
                    cameraOutputUri = event.outputUri
                    try {
                        cameraLauncher.launch(Uri.parse(event.outputUri))
                    } catch (_: ActivityNotFoundException) {
                        cameraOutputUri = null
                        viewModel.onCameraLaunchFailed()
                    }
                }

                is AgentUiEvent.NavigateHome -> {
                    onNavigateHome(
                        event.searchQuery,
                        event.tagName,
                        event.dateFilterMillis,
                    )
                }

                is AgentUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.messageRes),
                    )
                }
            }
        }
    }

    LaunchedEffect(readyState.isVoiceCaptureMode) {
        if (!readyState.isVoiceCaptureMode) {
            return@LaunchedEffect
        }

        val isAudioPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO,
        ) == PackageManager.PERMISSION_GRANTED

        if (isAudioPermissionGranted) {
            launchVoiceRecognition()
        } else {
            voicePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    LaunchedEffect(readyState.messages.size) {
        if (readyState.messages.isNotEmpty()) {
            listState.animateScrollToItem(index = readyState.messages.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.assistant_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back),
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.screenPadding)
                    .padding(bottom = dimens.itemSpacing),
            ) {
                if (readyState.isVoiceCaptureMode) {
                    VoiceCaptureBar(
                        onRetry = viewModel::onRetryVoiceCapture,
                        onCancel = viewModel::onCancelVoiceCapture,
                    )
                } else {
                    MessageComposer(
                        value = readyState.draftMessage,
                        pendingPhotoSourceUris = readyState.pendingPhotoSourceUris,
                        isRunningCommand = readyState.isRunningCommand,
                        onValueChange = viewModel::onDraftChanged,
                        onSend = viewModel::onSendDraft,
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
                        onRemovePendingPhoto = viewModel::onRemovePendingPhoto,
                        onStartVoiceCapture = viewModel::onStartVoiceCapture,
                    )
                }
            }
        },
    ) { innerPadding ->
        if (readyState.messages.isEmpty()) {
            EmptyConversationState(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = dimens.screenPadding),
            )
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = dimens.screenPadding,
                    vertical = dimens.screenPadding,
                ),
                verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
            ) {
                items(
                    items = readyState.messages,
                    key = AgentMessageUiModel::id,
                ) { message ->
                    MessageBubble(message = message)
                }
            }
        }
    }
}

@Composable
private fun EmptyConversationState(
    modifier: Modifier = Modifier,
): Unit {
    Box(
        modifier = modifier,
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        Text(
            text = stringResource(id = R.string.assistant_empty_state),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun MessageBubble(
    message: AgentMessageUiModel,
): Unit {
    val messageText = message.text ?: run {
        val messageRes = message.textRes
        if (messageRes == null) {
            EMPTY_TEXT
        } else if (message.textArgs.isEmpty()) {
            stringResource(id = messageRes)
        } else {
            val formatArgs = message.textArgs.map { value -> value as Any }.toTypedArray()
            stringResource(id = messageRes, *formatArgs)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) {
            Arrangement.End
        } else {
            Arrangement.Start
        },
    ) {
        Surface(
            color = if (message.isFromUser) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            shape = RoundedCornerShape(MessageBubbleCornerRadius),
            modifier = Modifier.fillMaxWidth(MessageBubbleWidthFraction),
        ) {
            Text(
                text = messageText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    horizontal = MessageBubbleHorizontalPadding,
                    vertical = MessageBubbleVerticalPadding,
                ),
                color = if (message.isFromUser) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            )
        }
    }
}

@Composable
private fun MessageComposer(
    value: String,
    pendingPhotoSourceUris: List<String>,
    isRunningCommand: Boolean,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onAddGallery: () -> Unit,
    onAddCamera: () -> Unit,
    onRemovePendingPhoto: (String) -> Unit,
    onStartVoiceCapture: () -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current

    Surface(
        shape = RoundedCornerShape(ComposerCornerRadius),
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = ComposerMinHeight)
                .padding(
                    horizontal = ComposerHorizontalPadding,
                    vertical = ComposerVerticalPadding,
                ),
        ) {
            if (pendingPhotoSourceUris.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimens.itemSpacing),
                    horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                ) {
                    items(
                        items = pendingPhotoSourceUris,
                        key = { sourceUri -> sourceUri },
                    ) { sourceUri ->
                        Surface(
                            shape = RoundedCornerShape(AttachmentChipCornerRadius),
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ) {
                            Row(
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    start = AttachmentChipHorizontalPadding,
                                    end = AttachmentChipHorizontalPadding,
                                    top = AttachmentChipVerticalPadding,
                                    bottom = AttachmentChipVerticalPadding,
                                ),
                                horizontalArrangement = Arrangement.spacedBy(AttachmentChipIconSpacing),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.assistant_attachment_label),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                                IconButton(
                                    onClick = { onRemovePendingPhoto(sourceUri) },
                                    modifier = Modifier.size(AttachmentRemoveButtonSize),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(id = R.string.assistant_remove_attachment),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = dimens.itemSpacing),
                    contentAlignment = androidx.compose.ui.Alignment.CenterStart,
                ) {
                    if (value.isBlank()) {
                        Text(
                            text = stringResource(id = R.string.assistant_input_hint),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = { onSend() }),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    )
                }

                FilledTonalIconButton(
                    onClick = onAddGallery,
                    enabled = !isRunningCommand,
                    modifier = Modifier.size(ComposerActionButtonSize),
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = stringResource(id = R.string.assistant_add_gallery),
                    )
                }

                FilledTonalIconButton(
                    onClick = onAddCamera,
                    enabled = !isRunningCommand,
                    modifier = Modifier
                        .padding(start = AttachmentButtonSpacing)
                        .size(ComposerActionButtonSize),
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = stringResource(id = R.string.assistant_add_camera),
                    )
                }

                FilledTonalIconButton(
                    onClick = onStartVoiceCapture,
                    enabled = !isRunningCommand,
                    modifier = Modifier
                        .padding(start = AttachmentButtonSpacing)
                        .size(ComposerActionButtonSize),
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = stringResource(id = R.string.assistant_start_voice_capture),
                    )
                }

                FilledIconButton(
                    onClick = onSend,
                    enabled = value.isNotBlank() && !isRunningCommand,
                    modifier = Modifier
                        .padding(start = dimens.itemSpacing)
                        .size(ComposerActionButtonSize),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.assistant_send_message),
                    )
                }
            }
        }
    }
}

@Composable
private fun VoiceCaptureBar(
    onRetry: () -> Unit,
    onCancel: () -> Unit,
): Unit {
    Surface(
        shape = RoundedCornerShape(VoiceCaptureCornerRadius),
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = VoiceCaptureMinHeight)
                .padding(
                    horizontal = VoiceCaptureHorizontalPadding,
                    vertical = VoiceCaptureVerticalPadding,
                ),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            IconButton(onClick = onRetry) {
                Icon(
                    imageVector = Icons.Default.Undo,
                    contentDescription = stringResource(id = R.string.assistant_retry_voice_capture),
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = androidx.compose.ui.Alignment.Center,
            ) {
                FilledIconButton(
                    onClick = onCancel,
                    modifier = Modifier.size(VoiceCaptureMicButtonSize),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = stringResource(id = R.string.assistant_listening),
                    )
                }
            }

            IconButton(onClick = onCancel) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.assistant_cancel_voice_capture),
                )
            }
        }
    }
}

private fun AgentUiState.toReadyState(): AgentUiState.Ready {
    return when (this) {
        is AgentUiState.Ready -> this
    }
}

private const val MessageBubbleWidthFraction: Float = 0.82f

private val MessageBubbleCornerRadius = 18.dp
private val MessageBubbleHorizontalPadding = 12.dp
private val MessageBubbleVerticalPadding = 10.dp
private val ComposerCornerRadius = 24.dp
private val ComposerMinHeight = 56.dp
private val ComposerHorizontalPadding = 8.dp
private val ComposerVerticalPadding = 8.dp
private val ComposerActionButtonSize = 40.dp
private val VoiceCaptureCornerRadius = 22.dp
private val VoiceCaptureMinHeight = 72.dp
private val VoiceCaptureHorizontalPadding = 8.dp
private val VoiceCaptureVerticalPadding = 8.dp
private val VoiceCaptureMicButtonSize = 52.dp
private val AttachmentButtonSpacing = 6.dp
private val AttachmentChipCornerRadius = 16.dp
private val AttachmentChipHorizontalPadding = 6.dp
private val AttachmentChipVerticalPadding = 2.dp
private val AttachmentChipIconSpacing = 2.dp
private val AttachmentRemoveButtonSize = 24.dp
private const val EMPTY_TEXT: String = ""
private const val IMAGE_MIME_TYPE: String = "image/*"
