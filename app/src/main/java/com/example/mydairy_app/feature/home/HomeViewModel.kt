package com.example.mydairy_app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydairy_app.data.repository.EntryRepository
import com.example.mydairy_app.domain.model.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data class Loading(
        val searchQuery: String,
        val isSearchExpanded: Boolean,
    ) : HomeUiState

    data class Success(
        val sections: List<HomeSectionUiModel>,
        val searchQuery: String,
        val isSearchExpanded: Boolean,
    ) : HomeUiState

    data class Error(
        val cause: Throwable,
        val searchQuery: String,
        val isSearchExpanded: Boolean,
    ) : HomeUiState
}

data class HomeSectionUiModel(
    val dateLabel: String,
    val entries: List<HomeEntryUiModel>,
)

data class HomeEntryUiModel(
    val id: Long,
    val title: String?,
    val bodyPreview: String,
    val createdAtLabel: String,
    val firstPhotoPath: String?,
    val tags: List<String>,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val entryRepository: EntryRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState.Loading(
            searchQuery = EMPTY_SEARCH_QUERY,
            isSearchExpanded = false,
        ),
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val searchQuery: MutableStateFlow<String> = MutableStateFlow(EMPTY_SEARCH_QUERY)
    private val isSearchExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val zoneId: ZoneId = ZoneId.systemDefault()
    private val dayFormatter: DateTimeFormatter =
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault())
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withLocale(Locale.getDefault())

    private var entriesJob: Job? = null

    init {
        observeEntries()
    }

    fun onRetryLoad(): Unit {
        _uiState.value = HomeUiState.Loading(
            searchQuery = searchQuery.value,
            isSearchExpanded = isSearchExpanded.value,
        )
        observeEntries()
    }

    fun onSearchQueryChanged(query: String): Unit {
        searchQuery.value = query
        _uiState.update { state ->
            state.withSearch(
                searchQuery = query,
                isSearchExpanded = isSearchExpanded.value,
            )
        }
    }

    fun onSearchExpandedChanged(expanded: Boolean): Unit {
        isSearchExpanded.value = expanded
        _uiState.update { state ->
            state.withSearch(
                searchQuery = searchQuery.value,
                isSearchExpanded = expanded,
            )
        }
    }

    fun onClearSearch(): Unit {
        onSearchQueryChanged(EMPTY_SEARCH_QUERY)
    }

    private fun observeEntries(): Unit {
        entriesJob?.cancel()
        entriesJob = viewModelScope.launch(Dispatchers.IO) {
            searchQuery
                .debounce(SEARCH_DEBOUNCE_MILLIS)
                .map(String::trim)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isEmpty()) {
                        entryRepository.getAllEntries()
                    } else {
                        entryRepository.searchEntries(query)
                    }
                }
                .map(::toSections)
                .catch { throwable ->
                    _uiState.value = HomeUiState.Error(
                        cause = throwable,
                        searchQuery = searchQuery.value,
                        isSearchExpanded = isSearchExpanded.value,
                    )
                }
                .collectLatest { sections ->
                    _uiState.value = HomeUiState.Success(
                        sections = sections,
                        searchQuery = searchQuery.value,
                        isSearchExpanded = isSearchExpanded.value,
                    )
                }
        }
    }

    private fun toSections(entries: List<Entry>): List<HomeSectionUiModel> {
        val grouped = entries.groupBy { entry ->
            getDayStartMillis(entry.createdAt)
        }

        return grouped
            .toSortedMap(compareByDescending<Long> { it })
            .map { (dayStartMillis, dayEntries) ->
                HomeSectionUiModel(
                    dateLabel = formatDay(dayStartMillis),
                    entries = dayEntries
                        .sortedByDescending(Entry::createdAt)
                        .map(::toEntryUiModel),
                )
            }
    }

    private fun toEntryUiModel(entry: Entry): HomeEntryUiModel {
        return HomeEntryUiModel(
            id = entry.id,
            title = entry.title,
            bodyPreview = toBodyPreview(entry.body),
            createdAtLabel = formatDateTime(entry.createdAt),
            firstPhotoPath = entry.photoPaths.firstOrNull(),
            tags = entry.tags.map { tag -> tag.name },
        )
    }

    private fun getDayStartMillis(epochMillis: Long): Long {
        return Instant.ofEpochMilli(epochMillis)
            .atZone(zoneId)
            .toLocalDate()
            .atStartOfDay(zoneId)
            .toInstant()
            .toEpochMilli()
    }

    private fun formatDay(epochMillis: Long): String {
        return Instant.ofEpochMilli(epochMillis)
            .atZone(zoneId)
            .toLocalDate()
            .format(dayFormatter)
    }

    private fun formatDateTime(epochMillis: Long): String {
        return Instant.ofEpochMilli(epochMillis)
            .atZone(zoneId)
            .toLocalDateTime()
            .format(dateTimeFormatter)
    }

    private fun toBodyPreview(body: String): String {
        return if (body.length <= BODY_PREVIEW_MAX_CHARS) {
            body
        } else {
            body.take(BODY_PREVIEW_MAX_CHARS) + PREVIEW_SUFFIX
        }
    }

    private companion object {
        const val BODY_PREVIEW_MAX_CHARS: Int = 140
        const val PREVIEW_SUFFIX: String = "..."
        const val SEARCH_DEBOUNCE_MILLIS: Long = 300L
        const val EMPTY_SEARCH_QUERY: String = ""
    }
}

private fun HomeUiState.withSearch(searchQuery: String, isSearchExpanded: Boolean): HomeUiState {
    return when (this) {
        is HomeUiState.Loading -> this.copy(
            searchQuery = searchQuery,
            isSearchExpanded = isSearchExpanded,
        )

        is HomeUiState.Success -> this.copy(
            searchQuery = searchQuery,
            isSearchExpanded = isSearchExpanded,
        )

        is HomeUiState.Error -> this.copy(
            searchQuery = searchQuery,
            isSearchExpanded = isSearchExpanded,
        )
    }
}
