package com.example.mydairy_app.feature.home;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.SavedStateHandle;
import com.example.mydairy_app.data.repository.EntryRepository;
import com.example.mydairy_app.data.repository.TagRepository;
import com.example.mydairy_app.domain.model.Entry;
import com.example.mydairy_app.ui.navigation.Screen;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 B2\u00020\u0001:\u0001BB\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0012H\u0002J5\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020&0%2\b\u0010(\u001a\u0004\u0018\u00010\u00122\b\u0010)\u001a\u0004\u0018\u00010\u0012H\u0002\u00a2\u0006\u0002\u0010*J\u0010\u0010+\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0012H\u0002J\u0010\u0010,\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0012H\u0002J\u0010\u0010-\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u0012H\u0002J\b\u0010.\u001a\u00020/H\u0002J\b\u00100\u001a\u00020/H\u0002J\u0006\u00101\u001a\u00020/J\u0006\u00102\u001a\u00020/J\u0006\u00103\u001a\u00020/J\u0006\u00104\u001a\u00020/J\u000e\u00105\u001a\u00020/2\u0006\u00106\u001a\u00020\u0015J\u000e\u00107\u001a\u00020/2\u0006\u00108\u001a\u00020\u0017J\u0015\u00109\u001a\u00020/2\b\u0010(\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\u0002\u0010:J\u0010\u0010;\u001a\u00020\u00172\u0006\u0010<\u001a\u00020\u0017H\u0002J\u0010\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020&H\u0002J\u001c\u0010@\u001a\b\u0012\u0004\u0012\u00020A0%2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020&0%H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0013R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006C"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "entryRepository", "Lcom/example/mydairy_app/data/repository/EntryRepository;", "tagRepository", "Lcom/example/mydairy_app/data/repository/TagRepository;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/example/mydairy_app/data/repository/EntryRepository;Lcom/example/mydairy_app/data/repository/TagRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "dateTimeFormatter", "Ljava/time/format/DateTimeFormatter;", "dayFormatter", "entriesJob", "Lkotlinx/coroutines/Job;", "initialDateFilterMillis", "", "Ljava/lang/Long;", "isSearchExpanded", "", "searchQuery", "", "selectedDateFilterMillis", "selectedTagId", "tagsJob", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "zoneId", "Ljava/time/ZoneId;", "entryCreatedDate", "Ljava/time/LocalDate;", "epochMillis", "filterEntries", "", "Lcom/example/mydairy_app/domain/model/Entry;", "entries", "tagId", "dateFilterMillis", "(Ljava/util/List;Ljava/lang/Long;Ljava/lang/Long;)Ljava/util/List;", "formatDateTime", "formatDay", "getDayStartMillis", "observeEntries", "", "observeTags", "onClearSearch", "onClearTagFilter", "onDateFilterCleared", "onRetryLoad", "onSearchExpandedChanged", "expanded", "onSearchQueryChanged", "query", "onTagFilterSelected", "(Ljava/lang/Long;)V", "toBodyPreview", "body", "toEntryUiModel", "Lcom/example/mydairy_app/feature/home/HomeEntryUiModel;", "entry", "toSections", "Lcom/example/mydairy_app/feature/home/HomeSectionUiModel;", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.repository.EntryRepository entryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.repository.TagRepository tagRepository = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long initialDateFilterMillis = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.mydairy_app.feature.home.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.home.HomeUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> isSearchExpanded = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> selectedTagId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> selectedDateFilterMillis = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.ZoneId zoneId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter dayFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter dateTimeFormatter = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job entriesJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job tagsJob;
    @java.lang.Deprecated()
    public static final int BODY_PREVIEW_MAX_CHARS = 140;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String PREVIEW_SUFFIX = "...";
    @java.lang.Deprecated()
    public static final long SEARCH_DEBOUNCE_MILLIS = 300L;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String EMPTY_SEARCH_QUERY = "";
    @org.jetbrains.annotations.NotNull()
    private static final com.example.mydairy_app.feature.home.HomeViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.repository.EntryRepository entryRepository, @org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.repository.TagRepository tagRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.home.HomeUiState> getUiState() {
        return null;
    }
    
    public final void onRetryLoad() {
    }
    
    public final void onSearchQueryChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void onSearchExpandedChanged(boolean expanded) {
    }
    
    public final void onClearSearch() {
    }
    
    public final void onTagFilterSelected(@org.jetbrains.annotations.Nullable()
    java.lang.Long tagId) {
    }
    
    public final void onClearTagFilter() {
    }
    
    public final void onDateFilterCleared() {
    }
    
    private final void observeEntries() {
    }
    
    private final void observeTags() {
    }
    
    private final java.util.List<com.example.mydairy_app.domain.model.Entry> filterEntries(java.util.List<com.example.mydairy_app.domain.model.Entry> entries, java.lang.Long tagId, java.lang.Long dateFilterMillis) {
        return null;
    }
    
    private final java.time.LocalDate entryCreatedDate(long epochMillis) {
        return null;
    }
    
    private final java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> toSections(java.util.List<com.example.mydairy_app.domain.model.Entry> entries) {
        return null;
    }
    
    private final com.example.mydairy_app.feature.home.HomeEntryUiModel toEntryUiModel(com.example.mydairy_app.domain.model.Entry entry) {
        return null;
    }
    
    private final long getDayStartMillis(long epochMillis) {
        return 0L;
    }
    
    private final java.lang.String formatDay(long epochMillis) {
        return null;
    }
    
    private final java.lang.String formatDateTime(long epochMillis) {
        return null;
    }
    
    private final java.lang.String toBodyPreview(java.lang.String body) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeViewModel$Companion;", "", "()V", "BODY_PREVIEW_MAX_CHARS", "", "EMPTY_SEARCH_QUERY", "", "PREVIEW_SUFFIX", "SEARCH_DEBOUNCE_MILLIS", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}