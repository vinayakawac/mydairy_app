package com.example.mydairy_app.feature.detail

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydairy_app.R
import com.example.mydairy_app.data.repository.EntryRepository
import com.example.mydairy_app.domain.model.Entry
import com.example.mydairy_app.ui.navigation.Screen
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
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    data object Loading : DetailUiState

    data class Success(
        val entry: DetailEntryUiModel,
        val isDeleting: Boolean,
        val showDeleteDialog: Boolean,
    ) : DetailUiState

    data class Error(
        val cause: Throwable?,
    ) : DetailUiState
}

data class DetailEntryUiModel(
    val id: Long,
    val title: String?,
    val body: String,
    val createdAtLabel: String,
    val photoPaths: List<String>,
    val tags: List<String>,
)

sealed interface DetailUiEvent {
    data object NavigateBack : DetailUiEvent

    data class NavigateEdit(
        val entryId: Long,
    ) : DetailUiEvent

    data class ShowMessage(
        @StringRes val messageRes: Int,
    ) : DetailUiEvent
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val entryRepository: EntryRepository,
) : ViewModel() {

    private val entryIdArg: Long = savedStateHandle
        .get<Long>(Screen.Detail.ENTRY_ID_ARG)
        ?: INVALID_ENTRY_ID

    private val zoneId: ZoneId = ZoneId.systemDefault()
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withLocale(Locale.getDefault())

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<DetailUiEvent> = MutableSharedFlow(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val uiEvent: SharedFlow<DetailUiEvent> = _uiEvent.asSharedFlow()

    init {
        loadEntry()
    }

    fun onRetryLoad(): Unit {
        _uiState.value = DetailUiState.Loading
        loadEntry()
    }

    fun onBackClicked(): Unit {
        viewModelScope.launch {
            _uiEvent.emit(DetailUiEvent.NavigateBack)
        }
    }

    fun onEditClicked(): Unit {
        val state = _uiState.value as? DetailUiState.Success ?: return
        viewModelScope.launch {
            _uiEvent.emit(DetailUiEvent.NavigateEdit(entryId = state.entry.id))
        }
    }

    fun onDeleteClicked(): Unit {
        val state = _uiState.value as? DetailUiState.Success ?: return
        _uiState.value = state.copy(showDeleteDialog = true)
    }

    fun onDeleteDialogDismissed(): Unit {
        val state = _uiState.value as? DetailUiState.Success ?: return
        _uiState.value = state.copy(showDeleteDialog = false)
    }

    fun onDeleteConfirmed(): Unit {
        val state = _uiState.value as? DetailUiState.Success ?: return
        _uiState.value = state.copy(isDeleting = true, showDeleteDialog = false)

        viewModelScope.launch(Dispatchers.IO) {
            val deleted = runCatching {
                entryRepository.deleteEntryById(state.entry.id)
            }.isSuccess

            if (!deleted) {
                _uiState.value = state.copy(isDeleting = false, showDeleteDialog = false)
                _uiEvent.emit(DetailUiEvent.ShowMessage(R.string.detail_delete_failed))
                return@launch
            }

            _uiEvent.emit(DetailUiEvent.NavigateBack)
        }
    }

    private fun loadEntry(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            if (entryIdArg == INVALID_ENTRY_ID) {
                _uiState.value = DetailUiState.Error(cause = null)
                return@launch
            }

            val entry = entryRepository.getEntryById(entryIdArg)
            if (entry == null) {
                _uiState.value = DetailUiState.Error(cause = null)
                return@launch
            }

            _uiState.value = DetailUiState.Success(
                entry = entry.toUiModel(),
                isDeleting = false,
                showDeleteDialog = false,
            )
        }
    }

    private fun Entry.toUiModel(): DetailEntryUiModel {
        return DetailEntryUiModel(
            id = id,
            title = title,
            body = body,
            createdAtLabel = Instant
                .ofEpochMilli(createdAt)
                .atZone(zoneId)
                .toLocalDateTime()
                .format(dateTimeFormatter),
            photoPaths = photoPaths,
            tags = tags.map { tag -> tag.name },
        )
    }

    private companion object {
        const val EVENT_BUFFER_CAPACITY: Int = 1
        const val INVALID_ENTRY_ID: Long = -1L
    }
}
