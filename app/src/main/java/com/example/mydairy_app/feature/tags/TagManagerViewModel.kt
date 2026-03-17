package com.example.mydairy_app.feature.tags

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydairy_app.R
import com.example.mydairy_app.data.repository.TagRepository
import com.example.mydairy_app.domain.model.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
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

sealed interface TagManagerUiState {
    data object Loading : TagManagerUiState

    data class Success(
        val tags: List<TagManagerTagUiModel>,
    ) : TagManagerUiState

    data class Error(
        val cause: Throwable,
    ) : TagManagerUiState
}

data class TagManagerTagUiModel(
    val id: Long,
    val name: String,
)

sealed interface TagManagerUiEvent {
    data class ShowMessage(
        @StringRes val messageRes: Int,
    ) : TagManagerUiEvent
}

@HiltViewModel
class TagManagerViewModel @Inject constructor(
    private val tagRepository: TagRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TagManagerUiState> = MutableStateFlow(TagManagerUiState.Loading)
    val uiState: StateFlow<TagManagerUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<TagManagerUiEvent> = MutableSharedFlow(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val uiEvent: SharedFlow<TagManagerUiEvent> = _uiEvent.asSharedFlow()

    init {
        observeTags()
    }

    fun onRetryLoad(): Unit {
        _uiState.value = TagManagerUiState.Loading
        observeTags()
    }

    fun onCreateTag(rawName: String): Unit {
        val normalizedName = rawName.trim()
        if (normalizedName.isEmpty()) {
            emitMessage(R.string.tags_message_name_required)
            return
        }

        if (isDuplicateTagName(name = normalizedName, excludedTagId = null)) {
            emitMessage(R.string.tags_message_duplicate_name)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val insertedId = runCatching {
                tagRepository.insertTag(normalizedName)
            }.getOrNull()

            if (insertedId == null || insertedId <= INVALID_TAG_ID) {
                _uiEvent.emit(TagManagerUiEvent.ShowMessage(R.string.tags_message_create_failed))
            }
        }
    }

    fun onRenameTag(tagId: Long, rawName: String): Unit {
        val normalizedName = rawName.trim()
        if (normalizedName.isEmpty()) {
            emitMessage(R.string.tags_message_name_required)
            return
        }

        if (isDuplicateTagName(name = normalizedName, excludedTagId = tagId)) {
            emitMessage(R.string.tags_message_duplicate_name)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val targetTag = currentTags().firstOrNull { tag -> tag.id == tagId } ?: return@launch
            val result = runCatching {
                tagRepository.updateTag(
                    Tag(
                        id = targetTag.id,
                        name = normalizedName,
                    ),
                )
            }

            if (result.isFailure) {
                _uiEvent.emit(TagManagerUiEvent.ShowMessage(R.string.tags_message_rename_failed))
            }
        }
    }

    fun onDeleteTag(tagId: Long): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            val targetTag = currentTags().firstOrNull { tag -> tag.id == tagId } ?: return@launch
            val result = runCatching {
                tagRepository.deleteTag(
                    Tag(
                        id = targetTag.id,
                        name = targetTag.name,
                    ),
                )
            }

            if (result.isFailure) {
                _uiEvent.emit(TagManagerUiEvent.ShowMessage(R.string.tags_message_delete_failed))
            }
        }
    }

    private fun observeTags(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            tagRepository.getAllTags()
                .catch { throwable ->
                    _uiState.value = TagManagerUiState.Error(cause = throwable)
                }
                .collectLatest { tags ->
                    _uiState.value = TagManagerUiState.Success(
                        tags = tags.map { tag ->
                            TagManagerTagUiModel(
                                id = tag.id,
                                name = tag.name,
                            )
                        },
                    )
                }
        }
    }

    private fun isDuplicateTagName(name: String, excludedTagId: Long?): Boolean {
        return currentTags()
            .filterNot { tag -> tag.id == excludedTagId }
            .any { tag -> tag.name.equals(name, ignoreCase = true) }
    }

    private fun currentTags(): List<TagManagerTagUiModel> {
        val state = _uiState.value
        return if (state is TagManagerUiState.Success) {
            state.tags
        } else {
            emptyList()
        }
    }

    private fun emitMessage(@StringRes messageRes: Int): Unit {
        viewModelScope.launch {
            _uiEvent.emit(TagManagerUiEvent.ShowMessage(messageRes = messageRes))
        }
    }

    private companion object {
        const val EVENT_BUFFER_CAPACITY: Int = 16
        const val INVALID_TAG_ID: Long = 0L
    }
}
