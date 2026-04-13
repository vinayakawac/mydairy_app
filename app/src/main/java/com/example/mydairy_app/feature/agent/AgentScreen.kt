package com.example.mydairy_app.feature.agent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.theme.MyDiaryDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentScreen(
    viewModel: AgentViewModel = hiltViewModel(),
    onBack: () -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val readyState = uiState.toReadyState()
    val listState = rememberLazyListState()

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
                        onValueChange = viewModel::onDraftChanged,
                        onSend = viewModel::onSendDraft,
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
                text = message.text,
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
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onStartVoiceCapture: () -> Unit,
): Unit {
    val dimens = MyDiaryDimens.current

    Surface(
        shape = RoundedCornerShape(ComposerCornerRadius),
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = ComposerMinHeight)
                .padding(
                    horizontal = ComposerHorizontalPadding,
                    vertical = ComposerVerticalPadding,
                ),
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
                onClick = onStartVoiceCapture,
                modifier = Modifier.size(ComposerActionButtonSize),
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = stringResource(id = R.string.assistant_start_voice_capture),
                )
            }

            FilledIconButton(
                onClick = onSend,
                enabled = value.isNotBlank(),
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
