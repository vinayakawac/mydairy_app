package com.example.mydairy_app.feature.settings

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydairy_app.R
import com.example.mydairy_app.core.datastore.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed interface SettingsUiState {
    data object Loading : SettingsUiState

    data class Success(
        val darkModeOverride: Boolean?,
    ) : SettingsUiState

    data class Error(
        val cause: Throwable,
        val darkModeOverride: Boolean?,
    ) : SettingsUiState
}

sealed interface SettingsUiEvent {
    data class ShowMessage(
        @StringRes val messageRes: Int,
    ) : SettingsUiEvent
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState.Loading)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<SettingsUiEvent> = MutableSharedFlow(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val uiEvent: SharedFlow<SettingsUiEvent> = _uiEvent.asSharedFlow()

    private var observeJob: Job? = null

    init {
        observeSettings()
    }

    fun onRetryLoad(): Unit {
        _uiState.value = SettingsUiState.Loading
        observeSettings()
    }

    fun onSystemThemeSelected(): Unit {
        updateDarkModeOverride(null)
    }

    fun onLightThemeSelected(): Unit {
        updateDarkModeOverride(false)
    }

    fun onDarkThemeSelected(): Unit {
        updateDarkModeOverride(true)
    }

    private fun observeSettings(): Unit {
        observeJob?.cancel()
        observeJob = viewModelScope.launch(Dispatchers.IO) {
            appPreferences.darkModeOverride
                .catch { throwable ->
                    _uiState.value = SettingsUiState.Error(
                        cause = throwable,
                        darkModeOverride = currentDarkModeOverride(),
                    )
                }
                .collectLatest { overrideValue ->
                    _uiState.value = SettingsUiState.Success(
                        darkModeOverride = overrideValue,
                    )
                }
        }
    }

    private fun updateDarkModeOverride(overrideValue: Boolean?): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            val result = runCatching {
                appPreferences.setDarkModeOverride(overrideValue)
            }

            if (result.isFailure) {
                _uiEvent.emit(SettingsUiEvent.ShowMessage(R.string.settings_message_update_failed))
            }
        }
    }

    private fun currentDarkModeOverride(): Boolean? {
        val currentState = _uiState.value
        return when (currentState) {
            SettingsUiState.Loading -> null
            is SettingsUiState.Success -> currentState.darkModeOverride
            is SettingsUiState.Error -> currentState.darkModeOverride
        }
    }

    private companion object {
        const val EVENT_BUFFER_CAPACITY: Int = 8
    }
}
