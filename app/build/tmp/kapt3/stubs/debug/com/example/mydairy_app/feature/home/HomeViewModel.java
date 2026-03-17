package com.example.mydairy_app.feature.home;

import androidx.lifecycle.ViewModel;
import com.example.mydairy_app.data.repository.EntryRepository;
import com.example.mydairy_app.domain.model.Entry;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\u0006\u0010\u001e\u001a\u00020\u001dJ\u0006\u0010\u001f\u001a\u00020\u001dJ\u000e\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u000eJ\u000e\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\u0010J\u0010\u0010$\u001a\u00020\u00102\u0006\u0010%\u001a\u00020\u0010H\u0002J\u0010\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020)H\u0002J\u001c\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020)0+H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "entryRepository", "Lcom/example/mydairy_app/data/repository/EntryRepository;", "(Lcom/example/mydairy_app/data/repository/EntryRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "dateTimeFormatter", "Ljava/time/format/DateTimeFormatter;", "dayFormatter", "entriesJob", "Lkotlinx/coroutines/Job;", "isSearchExpanded", "", "searchQuery", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "zoneId", "Ljava/time/ZoneId;", "formatDateTime", "epochMillis", "", "formatDay", "getDayStartMillis", "observeEntries", "", "onClearSearch", "onRetryLoad", "onSearchExpandedChanged", "expanded", "onSearchQueryChanged", "query", "toBodyPreview", "body", "toEntryUiModel", "Lcom/example/mydairy_app/feature/home/HomeEntryUiModel;", "entry", "Lcom/example/mydairy_app/domain/model/Entry;", "toSections", "", "Lcom/example/mydairy_app/feature/home/HomeSectionUiModel;", "entries", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.repository.EntryRepository entryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.mydairy_app.feature.home.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.home.HomeUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> isSearchExpanded = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.ZoneId zoneId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter dayFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter dateTimeFormatter = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job entriesJob;
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
    com.example.mydairy_app.data.repository.EntryRepository entryRepository) {
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
    
    private final void observeEntries() {
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