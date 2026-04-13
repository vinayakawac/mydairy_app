package com.example.mydairy_app.feature.agent

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydairy_app.R
import com.example.mydairy_app.core.agent.NlpParser
import com.example.mydairy_app.core.agent.ParsedIntent
import com.example.mydairy_app.data.repository.EntryRepository
import com.example.mydairy_app.data.repository.SaveEntryRequest
import com.example.mydairy_app.data.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AgentUiState {
    data class Ready(
        val messages: List<AgentMessageUiModel>,
        val draftMessage: String,
        val isVoiceCaptureMode: Boolean,
        val pendingPhotoSourceUris: List<String>,
        val isRunningCommand: Boolean,
    ) : AgentUiState
}

data class AgentMessageUiModel(
    val id: Long,
    val isFromUser: Boolean,
    val text: String?,
    @StringRes val textRes: Int?,
    val textArgs: List<String>,
)

sealed interface AgentUiEvent {
    data class LaunchCamera(
        val outputUri: String,
    ) : AgentUiEvent

    data class NavigateHome(
        val searchQuery: String?,
        val tagName: String?,
        val dateFilterMillis: Long?,
    ) : AgentUiEvent

    data class ShowMessage(
        @StringRes val messageRes: Int,
    ) : AgentUiEvent
}

@HiltViewModel
class AgentViewModel @Inject constructor(
    private val entryRepository: EntryRepository,
    private val tagRepository: TagRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<AgentUiState> = MutableStateFlow(
        AgentUiState.Ready(
            messages = emptyList(),
            draftMessage = EMPTY_DRAFT,
            isVoiceCaptureMode = false,
            pendingPhotoSourceUris = emptyList(),
            isRunningCommand = false,
        ),
    )
    val uiState: StateFlow<AgentUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<AgentUiEvent> = MutableSharedFlow(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val uiEvent: SharedFlow<AgentUiEvent> = _uiEvent.asSharedFlow()

    private var nextMessageId: Long = INITIAL_MESSAGE_ID
    private val zoneId: ZoneId = ZoneId.systemDefault()
    private val dayFormatter: DateTimeFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())

    fun onDraftChanged(draft: String): Unit {
        updateReadyState { state ->
            state.copy(draftMessage = draft)
        }
    }

    fun onGalleryPhotoPicked(sourceUri: String?): Unit {
        if (sourceUri.isNullOrBlank()) {
            return
        }

        updateReadyState { state ->
            state.copy(
                pendingPhotoSourceUris = (state.pendingPhotoSourceUris + sourceUri).distinct(),
            )
        }
    }

    fun onRemovePendingPhoto(sourceUri: String): Unit {
        updateReadyState { state ->
            state.copy(
                pendingPhotoSourceUris = state.pendingPhotoSourceUris.filterNot { value -> value == sourceUri },
            )
        }
    }

    fun onCameraCaptureRequested(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            val outputUri = runCatching {
                entryRepository.createCameraOutputUri(entryId = null)
            }.getOrNull()

            if (outputUri == null) {
                _uiEvent.emit(AgentUiEvent.ShowMessage(R.string.editor_message_camera_failed))
                return@launch
            }

            _uiEvent.emit(AgentUiEvent.LaunchCamera(outputUri = outputUri))
        }
    }

    fun onCameraCaptureResult(isSuccess: Boolean, outputUri: String?): Unit {
        if (!isSuccess || outputUri.isNullOrBlank()) {
            return
        }

        updateReadyState { state ->
            state.copy(
                pendingPhotoSourceUris = (state.pendingPhotoSourceUris + outputUri).distinct(),
            )
        }
    }

    fun onCameraLaunchFailed(): Unit {
        emitMessage(R.string.editor_message_camera_unavailable)
    }

    fun onGalleryPickerFailed(): Unit {
        emitMessage(R.string.editor_message_gallery_failed)
    }

    fun onSendDraft(): Unit {
        val state = uiState.value as? AgentUiState.Ready ?: return
        if (state.isRunningCommand) {
            return
        }

        val normalizedInput = state.draftMessage.trim()
        if (normalizedInput.isEmpty()) {
            return
        }

        val pendingPhotos = state.pendingPhotoSourceUris
        updateReadyState { ready ->
            ready.copy(
                messages = ready.messages + createUserMessage(normalizedInput),
                draftMessage = EMPTY_DRAFT,
                isVoiceCaptureMode = false,
                pendingPhotoSourceUris = emptyList(),
                isRunningCommand = true,
            )
        }

        executeInput(rawInput = normalizedInput, pendingPhotos = pendingPhotos)
    }

    fun onStartVoiceCapture(): Unit {
        updateReadyState { state ->
            state.copy(isVoiceCaptureMode = true)
        }
    }

    fun onCancelVoiceCapture(): Unit {
        updateReadyState { state ->
            state.copy(isVoiceCaptureMode = false)
        }
    }

    fun onRetryVoiceCapture(): Unit {
        updateReadyState { state ->
            state.copy(isVoiceCaptureMode = true)
        }
    }

    fun onVoicePermissionDenied(): Unit {
        updateReadyState { state ->
            state.copy(isVoiceCaptureMode = false)
        }
        emitMessage(R.string.agent_mic_permission_denied)
    }

    fun onVoiceTranscriptReceived(transcript: String): Unit {
        val state = uiState.value as? AgentUiState.Ready ?: return
        if (state.isRunningCommand) {
            return
        }

        val normalizedTranscript = transcript.trim()
        if (normalizedTranscript.isEmpty()) {
            onVoiceCaptureFailed()
            return
        }

        val pendingPhotos = state.pendingPhotoSourceUris
        updateReadyState { ready ->
            ready.copy(
                messages = ready.messages + createUserMessage(normalizedTranscript),
                draftMessage = EMPTY_DRAFT,
                isVoiceCaptureMode = false,
                pendingPhotoSourceUris = emptyList(),
                isRunningCommand = true,
            )
        }

        executeInput(rawInput = normalizedTranscript, pendingPhotos = pendingPhotos)
    }

    fun onVoiceCaptureFailed(): Unit {
        updateReadyState { state ->
            state.copy(isVoiceCaptureMode = false)
        }
        appendAgentMessage(R.string.assistant_voice_capture_failed)
    }

    private fun executeInput(rawInput: String, pendingPhotos: List<String>): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val parsedIntent = NlpParser.parse(rawInput)
                when (parsedIntent) {
                    is ParsedIntent.AddEntry -> handleAddEntry(parsedIntent, pendingPhotos)
                    is ParsedIntent.UpdateEntry -> handleUpdateEntry(parsedIntent, pendingPhotos)
                    ParsedIntent.DeleteLast -> handleDeleteLast()
                    is ParsedIntent.DeleteTitled -> handleDeleteTitled(parsedIntent)
                    is ParsedIntent.Search -> handleSearch(parsedIntent)
                    is ParsedIntent.ShowTag -> handleShowTag(parsedIntent)
                    is ParsedIntent.ShowDate -> handleShowDate(parsedIntent)
                    ParsedIntent.ListTags -> handleListTags()
                    is ParsedIntent.Unknown -> appendAgentMessage(R.string.agent_reply_unknown)
                }
            } catch (_: Throwable) {
                appendAgentMessage(R.string.agent_reply_action_failed)
            } finally {
                updateReadyState { state ->
                    state.copy(isRunningCommand = false)
                }
            }
        }
    }

    private suspend fun handleAddEntry(intent: ParsedIntent.AddEntry, pendingPhotos: List<String>): Unit {
        val normalizedBody = intent.body.trim()
        if (normalizedBody.isEmpty()) {
            appendAgentMessage(R.string.agent_reply_unknown)
            return
        }

        val now = System.currentTimeMillis()
        val savedId = runCatching {
            entryRepository.saveFromEditor(
                request = SaveEntryRequest(
                    entryId = null,
                    title = intent.title,
                    body = normalizedBody,
                    createdAt = now,
                    updatedAt = now,
                    existingPhotoPaths = emptyList(),
                    newPhotoSourceUris = pendingPhotos,
                    tagIds = emptySet(),
                ),
            )
        }.getOrNull()

        if (savedId == null) {
            appendAgentMessage(R.string.agent_reply_action_failed)
            return
        }

        appendAgentMessage(R.string.agent_reply_entry_added)
    }

    private suspend fun handleUpdateEntry(intent: ParsedIntent.UpdateEntry, pendingPhotos: List<String>): Unit {
        val targetEntry = intent.targetTitle
            ?.let { title -> entryRepository.getEntryByTitle(title) }
            ?: entryRepository.getMostRecentEntry()

        if (targetEntry == null) {
            appendAgentMessage(R.string.agent_reply_not_found)
            return
        }

        val updatedBody = intent.body.trim().ifEmpty { targetEntry.body }
        val now = System.currentTimeMillis()

        val savedId = runCatching {
            entryRepository.saveFromEditor(
                request = SaveEntryRequest(
                    entryId = targetEntry.id,
                    title = targetEntry.title,
                    body = updatedBody,
                    createdAt = targetEntry.createdAt,
                    updatedAt = now,
                    existingPhotoPaths = targetEntry.photoPaths,
                    newPhotoSourceUris = pendingPhotos,
                    tagIds = targetEntry.tags.map { tag -> tag.id }.toSet(),
                ),
            )
        }.getOrNull()

        if (savedId == null) {
            appendAgentMessage(R.string.agent_reply_action_failed)
            return
        }

        appendAgentMessage(R.string.agent_reply_entry_updated)
    }

    private suspend fun handleDeleteLast(): Unit {
        val targetEntry = entryRepository.getMostRecentEntry()
        if (targetEntry == null) {
            appendAgentMessage(R.string.agent_reply_not_found)
            return
        }

        val deleted = runCatching {
            entryRepository.deleteEntryById(targetEntry.id)
        }.isSuccess

        if (!deleted) {
            appendAgentMessage(R.string.agent_reply_action_failed)
            return
        }

        appendAgentMessage(R.string.agent_reply_entry_deleted)
    }

    private suspend fun handleDeleteTitled(intent: ParsedIntent.DeleteTitled): Unit {
        val targetEntry = entryRepository.getEntryByTitle(intent.title)
        if (targetEntry == null) {
            appendAgentMessage(R.string.agent_reply_not_found)
            return
        }

        val deleted = runCatching {
            entryRepository.deleteEntryById(targetEntry.id)
        }.isSuccess

        if (!deleted) {
            appendAgentMessage(R.string.agent_reply_action_failed)
            return
        }

        appendAgentMessage(R.string.agent_reply_entry_deleted)
    }

    private suspend fun handleSearch(intent: ParsedIntent.Search): Unit {
        val query = intent.query.trim()
        if (query.isEmpty()) {
            appendAgentMessage(R.string.agent_reply_unknown)
            return
        }

        _uiEvent.emit(
            AgentUiEvent.NavigateHome(
                searchQuery = query,
                tagName = null,
                dateFilterMillis = null,
            ),
        )
        appendAgentMessage(
            messageRes = R.string.agent_reply_search_done,
            textArgs = listOf(query),
        )
    }

    private suspend fun handleShowTag(intent: ParsedIntent.ShowTag): Unit {
        val tag = tagRepository.getTagByName(intent.tagName)
        if (tag == null) {
            appendAgentMessage(
                messageRes = R.string.agent_reply_tag_not_found,
                textArgs = listOf(intent.tagName),
            )
            return
        }

        _uiEvent.emit(
            AgentUiEvent.NavigateHome(
                searchQuery = null,
                tagName = tag.name,
                dateFilterMillis = null,
            ),
        )
        appendAgentMessage(
            messageRes = R.string.agent_reply_tag_filter,
            textArgs = listOf(tag.name),
        )
    }

    private suspend fun handleShowDate(intent: ParsedIntent.ShowDate): Unit {
        _uiEvent.emit(
            AgentUiEvent.NavigateHome(
                searchQuery = null,
                tagName = null,
                dateFilterMillis = intent.dateMillis,
            ),
        )

        val dayLabel = Instant.ofEpochMilli(intent.dateMillis)
            .atZone(zoneId)
            .toLocalDate()
            .format(dayFormatter)

        appendAgentMessage(
            messageRes = R.string.agent_reply_date_filter,
            textArgs = listOf(dayLabel),
        )
    }

    private suspend fun handleListTags(): Unit {
        val tags = tagRepository.getAllTags().first()
        if (tags.isEmpty()) {
            appendAgentMessage(R.string.agent_reply_tags_empty)
            return
        }

        val joinedTags = tags.joinToString(separator = TAGS_JOIN_SEPARATOR) { tag -> tag.name }
        appendAgentMessage(
            messageRes = R.string.agent_reply_tags_list,
            textArgs = listOf(joinedTags),
        )
    }

    private fun createUserMessage(text: String): AgentMessageUiModel {
        return AgentMessageUiModel(
            id = nextMessageId++,
            isFromUser = true,
            text = text,
            textRes = null,
            textArgs = emptyList(),
        )
    }

    private fun appendAgentMessage(
        @StringRes messageRes: Int,
        textArgs: List<String> = emptyList(),
    ): Unit {
        updateReadyState { state ->
            state.copy(
                messages = state.messages + AgentMessageUiModel(
                    id = nextMessageId++,
                    isFromUser = false,
                    text = null,
                    textRes = messageRes,
                    textArgs = textArgs,
                ),
            )
        }
    }

    private fun emitMessage(@StringRes messageRes: Int): Unit {
        viewModelScope.launch {
            _uiEvent.emit(AgentUiEvent.ShowMessage(messageRes = messageRes))
        }
    }

    private fun updateReadyState(
        transform: (AgentUiState.Ready) -> AgentUiState.Ready,
    ): Unit {
        _uiState.update { state ->
            when (state) {
                is AgentUiState.Ready -> transform(state)
            }
        }
    }

    companion object {
        private const val INITIAL_MESSAGE_ID: Long = 1L
        private const val EMPTY_DRAFT: String = ""
        private const val EVENT_BUFFER_CAPACITY: Int = 1
        private const val TAGS_JOIN_SEPARATOR: String = ", "
    }
}
