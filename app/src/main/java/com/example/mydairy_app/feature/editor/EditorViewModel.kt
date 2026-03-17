package com.example.mydairy_app.feature.editor

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydairy_app.R
import com.example.mydairy_app.data.repository.EntryRepository
import com.example.mydairy_app.data.repository.SaveEntryRequest
import com.example.mydairy_app.data.repository.TagRepository
import com.example.mydairy_app.domain.model.Tag
import com.example.mydairy_app.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed interface EditorUiState {
    data object Loading : EditorUiState

    data class Editing(
        val entryId: Long?,
        val title: String,
        val body: String,
        val dateTimeMillis: Long,
        val dateLabel: String,
        val timeLabel: String,
        val photos: List<EditorPhotoUiModel>,
        val tags: List<EditorTagUiModel>,
        val selectedTagNames: List<String>,
        val isSaving: Boolean,
        val showDiscardDialog: Boolean,
        val showTagSheet: Boolean,
    ) : EditorUiState

    data class Error(
        val cause: Throwable?,
    ) : EditorUiState
}

data class EditorPhotoUiModel(
    val key: String,
    val source: String,
    val isPending: Boolean,
)

data class EditorTagUiModel(
    val id: Long,
    val name: String,
    val isSelected: Boolean,
)

sealed interface EditorUiEvent {
    data object NavigateBack : EditorUiEvent

    data class LaunchCamera(
        val outputUri: String,
    ) : EditorUiEvent

    data class ShowMessage(
        @StringRes val messageRes: Int,
    ) : EditorUiEvent
}

@HiltViewModel
class EditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val entryRepository: EntryRepository,
    private val tagRepository: TagRepository,
) : ViewModel() {

    private val requestedEntryId: Long? = savedStateHandle
        .get<Long>(Screen.Editor.ENTRY_ID_ARG)
        ?.takeIf { value -> value != Screen.Editor.NEW_ENTRY_ID }

    private val _uiState: MutableStateFlow<EditorUiState> = MutableStateFlow(EditorUiState.Loading)
    val uiState: StateFlow<EditorUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<EditorUiEvent> = MutableSharedFlow(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val uiEvent: SharedFlow<EditorUiEvent> = _uiEvent.asSharedFlow()

    private val zoneId: ZoneId = ZoneId.systemDefault()
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter
        .ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.getDefault())

    private var availableTags: List<Tag> = emptyList()
    private var draft: EditorDraft? = null
    private var initialSnapshot: EditorSnapshot? = null

    init {
        observeTags()
        loadInitialDraft()
    }

    fun onRetryLoad(): Unit {
        _uiState.value = EditorUiState.Loading
        loadInitialDraft()
    }

    fun onTitleChanged(value: String): Unit {
        updateDraft { current ->
            current.copy(title = value)
        }
    }

    fun onBodyChanged(value: String): Unit {
        updateDraft { current ->
            current.copy(body = value)
        }
    }

    fun onDatePicked(selectedDateMillis: Long): Unit {
        updateDraft { current ->
            val currentLocalDateTime = Instant
                .ofEpochMilli(current.dateTimeMillis)
                .atZone(zoneId)
                .toLocalDateTime()
            val selectedDate = Instant
                .ofEpochMilli(selectedDateMillis)
                .atZone(zoneId)
                .toLocalDate()

            current.copy(
                dateTimeMillis = selectedDate
                    .atTime(currentLocalDateTime.toLocalTime())
                    .atZone(zoneId)
                    .toInstant()
                    .toEpochMilli(),
            )
        }
    }

    fun onTimePicked(hourOfDay: Int, minute: Int): Unit {
        updateDraft { current ->
            val currentLocalDate = Instant
                .ofEpochMilli(current.dateTimeMillis)
                .atZone(zoneId)
                .toLocalDate()
            val selectedTime = LocalTime.of(hourOfDay, minute)

            current.copy(
                dateTimeMillis = currentLocalDate
                    .atTime(selectedTime)
                    .atZone(zoneId)
                    .toInstant()
                    .toEpochMilli(),
            )
        }
    }

    fun onGalleryPhotoPicked(sourceUri: String?): Unit {
        if (sourceUri.isNullOrBlank()) {
            return
        }

        updateDraft { current ->
            current.copy(newPhotoSourceUris = (current.newPhotoSourceUris + sourceUri).distinct())
        }
    }

    fun onCameraCaptureRequested(): Unit {
        val currentDraft = draft ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val outputUri = runCatching {
                entryRepository.createCameraOutputUri(currentDraft.entryId)
            }.getOrNull()

            if (outputUri == null) {
                _uiEvent.emit(EditorUiEvent.ShowMessage(R.string.editor_message_camera_failed))
                return@launch
            }

            _uiEvent.emit(EditorUiEvent.LaunchCamera(outputUri = outputUri))
        }
    }

    fun onCameraCaptureResult(isSuccess: Boolean, outputUri: String?): Unit {
        if (!isSuccess || outputUri.isNullOrBlank()) {
            return
        }

        updateDraft { current ->
            current.copy(newPhotoSourceUris = (current.newPhotoSourceUris + outputUri).distinct())
        }
    }

    fun onCameraLaunchFailed(): Unit {
        emitMessage(R.string.editor_message_camera_unavailable)
    }

    fun onGalleryPickerFailed(): Unit {
        emitMessage(R.string.editor_message_gallery_failed)
    }

    fun onRemovePhoto(photoKey: String): Unit {
        val split = photoKey.split(PHOTO_KEY_SEPARATOR)
        if (split.size != PHOTO_KEY_PARTS) {
            return
        }

        val keyType = split[PHOTO_KEY_TYPE_INDEX]
        val keyIndex = split[PHOTO_KEY_INDEX_INDEX].toIntOrNull() ?: return

        updateDraft { current ->
            when (keyType) {
                EXISTING_PHOTO_KEY_PREFIX -> {
                    current.copy(
                        existingPhotoPaths = current.existingPhotoPaths.removeAtIndex(keyIndex),
                    )
                }

                PENDING_PHOTO_KEY_PREFIX -> {
                    current.copy(
                        newPhotoSourceUris = current.newPhotoSourceUris.removeAtIndex(keyIndex),
                    )
                }

                else -> current
            }
        }
    }

    fun onOpenTagSheet(): Unit {
        updateDraft { current ->
            current.copy(showTagSheet = true)
        }
    }

    fun onCloseTagSheet(): Unit {
        updateDraft { current ->
            current.copy(showTagSheet = false)
        }
    }

    fun onToggleTagSelection(tagId: Long): Unit {
        updateDraft { current ->
            val updatedTagIds = if (current.selectedTagIds.contains(tagId)) {
                current.selectedTagIds - tagId
            } else {
                current.selectedTagIds + tagId
            }
            current.copy(selectedTagIds = updatedTagIds)
        }
    }

    fun onBackPressed(): Unit {
        val currentDraft = draft ?: return
        if (currentDraft.isSaving) {
            return
        }

        if (hasUnsavedChanges(currentDraft)) {
            updateDraft { current ->
                current.copy(showDiscardDialog = true)
            }
            return
        }

        viewModelScope.launch {
            _uiEvent.emit(EditorUiEvent.NavigateBack)
        }
    }

    fun onDiscardDialogDismissed(): Unit {
        updateDraft { current ->
            current.copy(showDiscardDialog = false)
        }
    }

    fun onDiscardConfirmed(): Unit {
        updateDraft { current ->
            current.copy(showDiscardDialog = false)
        }
        viewModelScope.launch {
            _uiEvent.emit(EditorUiEvent.NavigateBack)
        }
    }

    fun onSaveClicked(): Unit {
        val currentDraft = draft ?: return
        val normalizedBody = currentDraft.body.trim()

        if (normalizedBody.isEmpty()) {
            viewModelScope.launch {
                _uiEvent.emit(EditorUiEvent.ShowMessage(R.string.editor_message_body_required))
            }
            return
        }

        updateDraft { current ->
            current.copy(isSaving = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val now = System.currentTimeMillis()
            val savedId = runCatching {
                entryRepository.saveFromEditor(
                    request = SaveEntryRequest(
                        entryId = currentDraft.entryId,
                        title = currentDraft.title,
                        body = normalizedBody,
                        createdAt = currentDraft.dateTimeMillis,
                        updatedAt = now,
                        existingPhotoPaths = currentDraft.existingPhotoPaths,
                        newPhotoSourceUris = currentDraft.newPhotoSourceUris,
                        tagIds = currentDraft.selectedTagIds,
                    ),
                )
            }.getOrNull()

            if (savedId == null) {
                updateDraft { current ->
                    current.copy(isSaving = false)
                }
                _uiEvent.emit(EditorUiEvent.ShowMessage(R.string.editor_message_save_failed))
                return@launch
            }

            val savedEntry = entryRepository.getEntryById(savedId)
            if (savedEntry == null) {
                updateDraft { current ->
                    current.copy(isSaving = false)
                }
                _uiEvent.emit(EditorUiEvent.ShowMessage(R.string.editor_message_save_failed))
                return@launch
            }

            val refreshedDraft = EditorDraft(
                entryId = savedEntry.id,
                title = savedEntry.title.orEmpty(),
                body = savedEntry.body,
                dateTimeMillis = savedEntry.createdAt,
                existingPhotoPaths = savedEntry.photoPaths,
                newPhotoSourceUris = emptyList(),
                selectedTagIds = savedEntry.tags.map(Tag::id).toSet(),
                isSaving = false,
                showDiscardDialog = false,
                showTagSheet = false,
            )
            setDraft(
                value = refreshedDraft,
                replaceSnapshot = true,
            )
            _uiEvent.emit(EditorUiEvent.NavigateBack)
        }
    }

    private fun observeTags(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            tagRepository.getAllTags()
                .catch {
                    // Ignore tag loading errors during early phases.
                }
                .collectLatest { tags ->
                    availableTags = tags
                    emitEditingState()
                }
        }
    }

    private fun loadInitialDraft(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            if (requestedEntryId == null) {
                val now = System.currentTimeMillis()
                setDraft(
                    value = EditorDraft(
                        entryId = null,
                        title = EMPTY_TEXT,
                        body = EMPTY_TEXT,
                        dateTimeMillis = now,
                        existingPhotoPaths = emptyList(),
                        newPhotoSourceUris = emptyList(),
                        selectedTagIds = emptySet(),
                        isSaving = false,
                        showDiscardDialog = false,
                        showTagSheet = false,
                    ),
                    replaceSnapshot = true,
                )
                return@launch
            }

            val existingEntry = entryRepository.getEntryById(requestedEntryId)
            if (existingEntry == null) {
                _uiState.value = EditorUiState.Error(cause = null)
                return@launch
            }

            setDraft(
                value = EditorDraft(
                    entryId = existingEntry.id,
                    title = existingEntry.title.orEmpty(),
                    body = existingEntry.body,
                    dateTimeMillis = existingEntry.createdAt,
                    existingPhotoPaths = existingEntry.photoPaths,
                    newPhotoSourceUris = emptyList(),
                    selectedTagIds = existingEntry.tags.map(Tag::id).toSet(),
                    isSaving = false,
                    showDiscardDialog = false,
                    showTagSheet = false,
                ),
                replaceSnapshot = true,
            )
        }
    }

    private fun updateDraft(update: (EditorDraft) -> EditorDraft): Unit {
        val currentDraft = draft ?: return
        draft = update(currentDraft)
        emitEditingState()
    }

    private fun setDraft(value: EditorDraft, replaceSnapshot: Boolean): Unit {
        draft = value
        if (replaceSnapshot) {
            initialSnapshot = value.toSnapshot()
        }
        emitEditingState()
    }

    private fun emitEditingState(): Unit {
        val currentDraft = draft ?: return
        val localDateTime = Instant.ofEpochMilli(currentDraft.dateTimeMillis)
            .atZone(zoneId)
            .toLocalDateTime()

        val tagUiModels = availableTags
            .sortedBy(Tag::name)
            .map { tag ->
                EditorTagUiModel(
                    id = tag.id,
                    name = tag.name,
                    isSelected = currentDraft.selectedTagIds.contains(tag.id),
                )
            }

        val selectedTagNames = tagUiModels
            .filter(EditorTagUiModel::isSelected)
            .map(EditorTagUiModel::name)

        val photos = buildPhotoUiModels(currentDraft)

        _uiState.value = EditorUiState.Editing(
            entryId = currentDraft.entryId,
            title = currentDraft.title,
            body = currentDraft.body,
            dateTimeMillis = currentDraft.dateTimeMillis,
            dateLabel = localDateTime.toLocalDate().format(dateFormatter),
            timeLabel = localDateTime.toLocalTime().format(timeFormatter),
            photos = photos,
            tags = tagUiModels,
            selectedTagNames = selectedTagNames,
            isSaving = currentDraft.isSaving,
            showDiscardDialog = currentDraft.showDiscardDialog,
            showTagSheet = currentDraft.showTagSheet,
        )
    }

    private fun buildPhotoUiModels(currentDraft: EditorDraft): List<EditorPhotoUiModel> {
        val existing = currentDraft.existingPhotoPaths.mapIndexed { index, path ->
            EditorPhotoUiModel(
                key = "$EXISTING_PHOTO_KEY_PREFIX$PHOTO_KEY_SEPARATOR$index",
                source = path,
                isPending = false,
            )
        }
        val pending = currentDraft.newPhotoSourceUris.mapIndexed { index, sourceUri ->
            EditorPhotoUiModel(
                key = "$PENDING_PHOTO_KEY_PREFIX$PHOTO_KEY_SEPARATOR$index",
                source = sourceUri,
                isPending = true,
            )
        }

        return existing + pending
    }

    private fun hasUnsavedChanges(currentDraft: EditorDraft): Boolean {
        return currentDraft.toSnapshot() != initialSnapshot
    }

    private data class EditorDraft(
        val entryId: Long?,
        val title: String,
        val body: String,
        val dateTimeMillis: Long,
        val existingPhotoPaths: List<String>,
        val newPhotoSourceUris: List<String>,
        val selectedTagIds: Set<Long>,
        val isSaving: Boolean,
        val showDiscardDialog: Boolean,
        val showTagSheet: Boolean,
    )

    private data class EditorSnapshot(
        val entryId: Long?,
        val title: String,
        val body: String,
        val dateTimeMillis: Long,
        val existingPhotoPaths: List<String>,
        val newPhotoSourceUris: List<String>,
        val selectedTagIds: Set<Long>,
    )

    private fun EditorDraft.toSnapshot(): EditorSnapshot {
        return EditorSnapshot(
            entryId = entryId,
            title = title,
            body = body,
            dateTimeMillis = dateTimeMillis,
            existingPhotoPaths = existingPhotoPaths,
            newPhotoSourceUris = newPhotoSourceUris,
            selectedTagIds = selectedTagIds,
        )
    }

    private fun List<String>.removeAtIndex(index: Int): List<String> {
        return filterIndexed { itemIndex, _ -> itemIndex != index }
    }

    private fun emitMessage(@StringRes messageRes: Int): Unit {
        viewModelScope.launch {
            _uiEvent.emit(EditorUiEvent.ShowMessage(messageRes = messageRes))
        }
    }

    private companion object {
        const val EVENT_BUFFER_CAPACITY: Int = 1
        const val PHOTO_KEY_SEPARATOR: String = "|"
        const val EXISTING_PHOTO_KEY_PREFIX: String = "existing"
        const val PENDING_PHOTO_KEY_PREFIX: String = "pending"
        const val PHOTO_KEY_PARTS: Int = 2
        const val PHOTO_KEY_TYPE_INDEX: Int = 0
        const val PHOTO_KEY_INDEX_INDEX: Int = 1
        const val EMPTY_TEXT: String = ""
    }
}
