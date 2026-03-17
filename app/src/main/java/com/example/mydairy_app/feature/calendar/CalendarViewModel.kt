package com.example.mydairy_app.feature.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydairy_app.data.repository.EntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

sealed interface CalendarUiState {
    data object Loading : CalendarUiState

    data class Success(
        val displayedMonth: YearMonth,
        val monthLabel: String,
        val datesWithEntries: Set<LocalDate>,
    ) : CalendarUiState

    data class Error(
        val cause: Throwable,
    ) : CalendarUiState
}

sealed interface CalendarUiEvent {
    data class NavigateToDate(
        val dateStartMillis: Long,
    ) : CalendarUiEvent
}

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val entryRepository: EntryRepository,
) : ViewModel() {

    private val zoneId: ZoneId = ZoneId.systemDefault()
    private val locale: Locale = Locale.getDefault()
    private val monthFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(MONTH_LABEL_PATTERN, locale)

    private val _uiState: MutableStateFlow<CalendarUiState> = MutableStateFlow(CalendarUiState.Loading)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<CalendarUiEvent> = MutableSharedFlow(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val uiEvent: SharedFlow<CalendarUiEvent> = _uiEvent.asSharedFlow()

    private val displayedMonth: MutableStateFlow<YearMonth> = MutableStateFlow(YearMonth.now(zoneId))

    init {
        observeMonth()
    }

    fun onRetryLoad(): Unit {
        _uiState.value = CalendarUiState.Loading
        observeMonth()
    }

    fun onPreviousMonth(): Unit {
        displayedMonth.value = displayedMonth.value.minusMonths(1)
    }

    fun onNextMonth(): Unit {
        displayedMonth.value = displayedMonth.value.plusMonths(1)
    }

    fun onDateSelected(date: LocalDate): Unit {
        viewModelScope.launch {
            _uiEvent.emit(
                CalendarUiEvent.NavigateToDate(
                    dateStartMillis = date.toStartOfDayMillis(zoneId),
                ),
            )
        }
    }

    private fun observeMonth(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            displayedMonth
                .distinctUntilChanged()
                .flatMapLatest { month ->
                    entryRepository
                        .getEntriesByDate(
                            dateMillisStart = monthStartMillis(month),
                            dateMillisEnd = monthEndMillis(month),
                        )
                        .map { entries ->
                            CalendarUiState.Success(
                                displayedMonth = month,
                                monthLabel = month.format(monthFormatter),
                                datesWithEntries = entries.map { entry ->
                                    Instant.ofEpochMilli(entry.createdAt)
                                        .atZone(zoneId)
                                        .toLocalDate()
                                }.toSet(),
                            )
                        }
                }
                .catch { throwable ->
                    _uiState.value = CalendarUiState.Error(cause = throwable)
                }
                .collectLatest { state ->
                    _uiState.value = state
                }
        }
    }

    private fun monthStartMillis(month: YearMonth): Long {
        return month
            .atDay(1)
            .toStartOfDayMillis(zoneId)
    }

    private fun monthEndMillis(month: YearMonth): Long {
        val nextMonthStart = month
            .plusMonths(1)
            .atDay(1)
            .toStartOfDayMillis(zoneId)
        return nextMonthStart - ONE_MILLISECOND
    }

    private companion object {
        const val EVENT_BUFFER_CAPACITY: Int = 8
        const val ONE_MILLISECOND: Long = 1L
        const val MONTH_LABEL_PATTERN: String = "MMMM yyyy"
    }
}

private fun LocalDate.toStartOfDayMillis(zoneId: ZoneId): Long {
    return atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli()
}
