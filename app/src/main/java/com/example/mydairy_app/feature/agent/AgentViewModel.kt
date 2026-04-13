package com.example.mydairy_app.feature.agent

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface AgentUiState {
    data class Ready(
        val messages: List<AgentMessageUiModel>,
        val draftMessage: String,
        val isVoiceCaptureMode: Boolean,
    ) : AgentUiState
}

data class AgentMessageUiModel(
    val id: Long,
    val text: String,
    val isFromUser: Boolean,
)

@HiltViewModel
class AgentViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<AgentUiState> = MutableStateFlow(
        AgentUiState.Ready(
            messages = emptyList(),
            draftMessage = EMPTY_DRAFT,
            isVoiceCaptureMode = false,
        ),
    )
    val uiState: StateFlow<AgentUiState> = _uiState.asStateFlow()

    private var nextMessageId: Long = INITIAL_MESSAGE_ID

    fun onDraftChanged(draft: String): Unit {
        updateReadyState { state ->
            state.copy(draftMessage = draft)
        }
    }

    fun onSendDraft(): Unit {
        updateReadyState { state ->
            val normalizedInput = state.draftMessage.trim()
            if (normalizedInput.isEmpty()) {
                return@updateReadyState state
            }

            state.copy(
                messages = state.messages + AgentMessageUiModel(
                    id = nextMessageId++,
                    text = normalizedInput,
                    isFromUser = true,
                ),
                draftMessage = EMPTY_DRAFT,
                isVoiceCaptureMode = false,
            )
        }
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

    fun onVoiceTranscriptReceived(transcript: String): Unit {
        updateReadyState { state ->
            state.copy(
                draftMessage = transcript.trim(),
                isVoiceCaptureMode = false,
            )
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
    }
}
