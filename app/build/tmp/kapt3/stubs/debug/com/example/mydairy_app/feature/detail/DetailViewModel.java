package com.example.mydairy_app.feature.detail;

import androidx.annotation.StringRes;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.mydairy_app.R;
import com.example.mydairy_app.data.repository.EntryRepository;
import com.example.mydairy_app.domain.model.Entry;
import com.example.mydairy_app.ui.navigation.Screen;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 &2\u00020\u0001:\u0001&B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u0006\u0010\u001d\u001a\u00020\u001cJ\u0006\u0010\u001e\u001a\u00020\u001cJ\u0006\u0010\u001f\u001a\u00020\u001cJ\u0006\u0010 \u001a\u00020\u001cJ\u0006\u0010!\u001a\u00020\u001cJ\u0006\u0010\"\u001a\u00020\u001cJ\f\u0010#\u001a\u00020$*\u00020%H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/example/mydairy_app/feature/detail/DetailViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "entryRepository", "Lcom/example/mydairy_app/data/repository/EntryRepository;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/example/mydairy_app/data/repository/EntryRepository;)V", "_uiEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/example/mydairy_app/feature/detail/DetailUiEvent;", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/mydairy_app/feature/detail/DetailUiState;", "dateTimeFormatter", "Ljava/time/format/DateTimeFormatter;", "entryIdArg", "", "uiEvent", "Lkotlinx/coroutines/flow/SharedFlow;", "getUiEvent", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "zoneId", "Ljava/time/ZoneId;", "loadEntry", "", "onBackClicked", "onDeleteClicked", "onDeleteConfirmed", "onDeleteDialogDismissed", "onEditClicked", "onRetryLoad", "toUiModel", "Lcom/example/mydairy_app/feature/detail/DetailEntryUiModel;", "Lcom/example/mydairy_app/domain/model/Entry;", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.repository.EntryRepository entryRepository = null;
    private final long entryIdArg = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.time.ZoneId zoneId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter dateTimeFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.mydairy_app.feature.detail.DetailUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.detail.DetailUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.example.mydairy_app.feature.detail.DetailUiEvent> _uiEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.example.mydairy_app.feature.detail.DetailUiEvent> uiEvent = null;
    @java.lang.Deprecated()
    public static final int EVENT_BUFFER_CAPACITY = 1;
    @java.lang.Deprecated()
    public static final long INVALID_ENTRY_ID = -1L;
    @org.jetbrains.annotations.NotNull()
    private static final com.example.mydairy_app.feature.detail.DetailViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public DetailViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.repository.EntryRepository entryRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.detail.DetailUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.example.mydairy_app.feature.detail.DetailUiEvent> getUiEvent() {
        return null;
    }
    
    public final void onRetryLoad() {
    }
    
    public final void onBackClicked() {
    }
    
    public final void onEditClicked() {
    }
    
    public final void onDeleteClicked() {
    }
    
    public final void onDeleteDialogDismissed() {
    }
    
    public final void onDeleteConfirmed() {
    }
    
    private final void loadEntry() {
    }
    
    private final com.example.mydairy_app.feature.detail.DetailEntryUiModel toUiModel(com.example.mydairy_app.domain.model.Entry $this$toUiModel) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/example/mydairy_app/feature/detail/DetailViewModel$Companion;", "", "()V", "EVENT_BUFFER_CAPACITY", "", "INVALID_ENTRY_ID", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}