package com.example.mydairy_app.feature.editor;

import androidx.annotation.StringRes;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.mydairy_app.R;
import com.example.mydairy_app.data.repository.EntryRepository;
import com.example.mydairy_app.data.repository.SaveEntryRequest;
import com.example.mydairy_app.data.repository.TagRepository;
import com.example.mydairy_app.domain.model.Tag;
import com.example.mydairy_app.ui.navigation.Screen;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 V2\u00020\u0001:\u0003VWXB\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00102\u0006\u0010(\u001a\u00020\u0015H\u0002J\b\u0010)\u001a\u00020*H\u0002J\u0012\u0010+\u001a\u00020*2\b\b\u0001\u0010,\u001a\u00020-H\u0002J\u0010\u0010.\u001a\u00020/2\u0006\u0010(\u001a\u00020\u0015H\u0002J\b\u00100\u001a\u00020*H\u0002J\b\u00101\u001a\u00020*H\u0002J\u0006\u00102\u001a\u00020*J\u000e\u00103\u001a\u00020*2\u0006\u00104\u001a\u000205J\u0006\u00106\u001a\u00020*J\u0018\u00107\u001a\u00020*2\u0006\u00108\u001a\u00020/2\b\u00109\u001a\u0004\u0018\u000105J\u0006\u0010:\u001a\u00020*J\u0006\u0010;\u001a\u00020*J\u000e\u0010<\u001a\u00020*2\u0006\u0010=\u001a\u00020\u0019J\u0006\u0010>\u001a\u00020*J\u0006\u0010?\u001a\u00020*J\u0010\u0010@\u001a\u00020*2\b\u0010A\u001a\u0004\u0018\u000105J\u0006\u0010B\u001a\u00020*J\u0006\u0010C\u001a\u00020*J\u000e\u0010D\u001a\u00020*2\u0006\u0010E\u001a\u000205J\u0006\u0010F\u001a\u00020*J\u0006\u0010G\u001a\u00020*J\u0016\u0010H\u001a\u00020*2\u0006\u0010I\u001a\u00020-2\u0006\u0010J\u001a\u00020-J\u000e\u0010K\u001a\u00020*2\u0006\u00104\u001a\u000205J\u000e\u0010L\u001a\u00020*2\u0006\u0010M\u001a\u00020\u0019J\u0018\u0010N\u001a\u00020*2\u0006\u00104\u001a\u00020\u00152\u0006\u0010O\u001a\u00020/H\u0002J\u001c\u0010P\u001a\u00020*2\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150RH\u0002J \u0010S\u001a\b\u0012\u0004\u0012\u0002050\u0010*\b\u0012\u0004\u0012\u0002050\u00102\u0006\u0010T\u001a\u00020-H\u0002J\f\u0010U\u001a\u00020\u0017*\u00020\u0015H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u001aR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000e0!\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020%X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006Y"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "entryRepository", "Lcom/example/mydairy_app/data/repository/EntryRepository;", "tagRepository", "Lcom/example/mydairy_app/data/repository/TagRepository;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/example/mydairy_app/data/repository/EntryRepository;Lcom/example/mydairy_app/data/repository/TagRepository;)V", "_uiEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/example/mydairy_app/feature/editor/EditorUiEvent;", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/mydairy_app/feature/editor/EditorUiState;", "availableTags", "", "Lcom/example/mydairy_app/domain/model/Tag;", "dateFormatter", "Ljava/time/format/DateTimeFormatter;", "draft", "Lcom/example/mydairy_app/feature/editor/EditorViewModel$EditorDraft;", "initialSnapshot", "Lcom/example/mydairy_app/feature/editor/EditorViewModel$EditorSnapshot;", "requestedEntryId", "", "Ljava/lang/Long;", "timeFormatter", "uiEvent", "Lkotlinx/coroutines/flow/SharedFlow;", "getUiEvent", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "zoneId", "Ljava/time/ZoneId;", "buildPhotoUiModels", "Lcom/example/mydairy_app/feature/editor/EditorPhotoUiModel;", "currentDraft", "emitEditingState", "", "emitMessage", "messageRes", "", "hasUnsavedChanges", "", "loadInitialDraft", "observeTags", "onBackPressed", "onBodyChanged", "value", "", "onCameraCaptureRequested", "onCameraCaptureResult", "isSuccess", "outputUri", "onCameraLaunchFailed", "onCloseTagSheet", "onDatePicked", "selectedDateMillis", "onDiscardConfirmed", "onDiscardDialogDismissed", "onGalleryPhotoPicked", "sourceUri", "onGalleryPickerFailed", "onOpenTagSheet", "onRemovePhoto", "photoKey", "onRetryLoad", "onSaveClicked", "onTimePicked", "hourOfDay", "minute", "onTitleChanged", "onToggleTagSelection", "tagId", "setDraft", "replaceSnapshot", "updateDraft", "update", "Lkotlin/Function1;", "removeAtIndex", "index", "toSnapshot", "Companion", "EditorDraft", "EditorSnapshot", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class EditorViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.repository.EntryRepository entryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.repository.TagRepository tagRepository = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long requestedEntryId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.mydairy_app.feature.editor.EditorUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.editor.EditorUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.example.mydairy_app.feature.editor.EditorUiEvent> _uiEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.example.mydairy_app.feature.editor.EditorUiEvent> uiEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.ZoneId zoneId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter dateFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter timeFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.mydairy_app.domain.model.Tag> availableTags;
    @org.jetbrains.annotations.Nullable()
    private com.example.mydairy_app.feature.editor.EditorViewModel.EditorDraft draft;
    @org.jetbrains.annotations.Nullable()
    private com.example.mydairy_app.feature.editor.EditorViewModel.EditorSnapshot initialSnapshot;
    @java.lang.Deprecated()
    public static final int EVENT_BUFFER_CAPACITY = 1;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String PHOTO_KEY_SEPARATOR = "|";
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String EXISTING_PHOTO_KEY_PREFIX = "existing";
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String PENDING_PHOTO_KEY_PREFIX = "pending";
    @java.lang.Deprecated()
    public static final int PHOTO_KEY_PARTS = 2;
    @java.lang.Deprecated()
    public static final int PHOTO_KEY_TYPE_INDEX = 0;
    @java.lang.Deprecated()
    public static final int PHOTO_KEY_INDEX_INDEX = 1;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String EMPTY_TEXT = "";
    @org.jetbrains.annotations.NotNull()
    private static final com.example.mydairy_app.feature.editor.EditorViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public EditorViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.repository.EntryRepository entryRepository, @org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.repository.TagRepository tagRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.editor.EditorUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.example.mydairy_app.feature.editor.EditorUiEvent> getUiEvent() {
        return null;
    }
    
    public final void onRetryLoad() {
    }
    
    public final void onTitleChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onBodyChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onDatePicked(long selectedDateMillis) {
    }
    
    public final void onTimePicked(int hourOfDay, int minute) {
    }
    
    public final void onGalleryPhotoPicked(@org.jetbrains.annotations.Nullable()
    java.lang.String sourceUri) {
    }
    
    public final void onCameraCaptureRequested() {
    }
    
    public final void onCameraCaptureResult(boolean isSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String outputUri) {
    }
    
    public final void onCameraLaunchFailed() {
    }
    
    public final void onGalleryPickerFailed() {
    }
    
    public final void onRemovePhoto(@org.jetbrains.annotations.NotNull()
    java.lang.String photoKey) {
    }
    
    public final void onOpenTagSheet() {
    }
    
    public final void onCloseTagSheet() {
    }
    
    public final void onToggleTagSelection(long tagId) {
    }
    
    public final void onBackPressed() {
    }
    
    public final void onDiscardDialogDismissed() {
    }
    
    public final void onDiscardConfirmed() {
    }
    
    public final void onSaveClicked() {
    }
    
    private final void observeTags() {
    }
    
    private final void loadInitialDraft() {
    }
    
    private final void updateDraft(kotlin.jvm.functions.Function1<? super com.example.mydairy_app.feature.editor.EditorViewModel.EditorDraft, com.example.mydairy_app.feature.editor.EditorViewModel.EditorDraft> update) {
    }
    
    private final void setDraft(com.example.mydairy_app.feature.editor.EditorViewModel.EditorDraft value, boolean replaceSnapshot) {
    }
    
    private final void emitEditingState() {
    }
    
    private final java.util.List<com.example.mydairy_app.feature.editor.EditorPhotoUiModel> buildPhotoUiModels(com.example.mydairy_app.feature.editor.EditorViewModel.EditorDraft currentDraft) {
        return null;
    }
    
    private final boolean hasUnsavedChanges(com.example.mydairy_app.feature.editor.EditorViewModel.EditorDraft currentDraft) {
        return false;
    }
    
    private final com.example.mydairy_app.feature.editor.EditorViewModel.EditorSnapshot toSnapshot(com.example.mydairy_app.feature.editor.EditorViewModel.EditorDraft $this$toSnapshot) {
        return null;
    }
    
    private final java.util.List<java.lang.String> removeAtIndex(java.util.List<java.lang.String> $this$removeAtIndex, int index) {
        return null;
    }
    
    private final void emitMessage(@androidx.annotation.StringRes()
    int messageRes) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorViewModel$Companion;", "", "()V", "EMPTY_TEXT", "", "EVENT_BUFFER_CAPACITY", "", "EXISTING_PHOTO_KEY_PREFIX", "PENDING_PHOTO_KEY_PREFIX", "PHOTO_KEY_INDEX_INDEX", "PHOTO_KEY_PARTS", "PHOTO_KEY_SEPARATOR", "PHOTO_KEY_TYPE_INDEX", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0002\b\"\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001Bi\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\"\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\t\u0010#\u001a\u00020\u000eH\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003J\t\u0010*\u001a\u00020\u000eH\u00c6\u0003J\t\u0010+\u001a\u00020\u000eH\u00c6\u0003J\u0086\u0001\u0010,\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000eH\u00c6\u0001\u00a2\u0006\u0002\u0010-J\u0013\u0010.\u001a\u00020\u000e2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u000201H\u00d6\u0001J\t\u00102\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u001bR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\u0010\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0013\u00a8\u00063"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorViewModel$EditorDraft;", "", "entryId", "", "title", "", "body", "dateTimeMillis", "existingPhotoPaths", "", "newPhotoSourceUris", "selectedTagIds", "", "isSaving", "", "showDiscardDialog", "showTagSheet", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JLjava/util/List;Ljava/util/List;Ljava/util/Set;ZZZ)V", "getBody", "()Ljava/lang/String;", "getDateTimeMillis", "()J", "getEntryId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getExistingPhotoPaths", "()Ljava/util/List;", "()Z", "getNewPhotoSourceUris", "getSelectedTagIds", "()Ljava/util/Set;", "getShowDiscardDialog", "getShowTagSheet", "getTitle", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JLjava/util/List;Ljava/util/List;Ljava/util/Set;ZZZ)Lcom/example/mydairy_app/feature/editor/EditorViewModel$EditorDraft;", "equals", "other", "hashCode", "", "toString", "app_debug"})
    static final class EditorDraft {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long entryId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String title = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String body = null;
        private final long dateTimeMillis = 0L;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> existingPhotoPaths = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> newPhotoSourceUris = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.Long> selectedTagIds = null;
        private final boolean isSaving = false;
        private final boolean showDiscardDialog = false;
        private final boolean showTagSheet = false;
        
        public EditorDraft(@org.jetbrains.annotations.Nullable()
        java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String body, long dateTimeMillis, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> existingPhotoPaths, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> newPhotoSourceUris, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.Long> selectedTagIds, boolean isSaving, boolean showDiscardDialog, boolean showTagSheet) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getEntryId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getBody() {
            return null;
        }
        
        public final long getDateTimeMillis() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getExistingPhotoPaths() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getNewPhotoSourceUris() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.Long> getSelectedTagIds() {
            return null;
        }
        
        public final boolean isSaving() {
            return false;
        }
        
        public final boolean getShowDiscardDialog() {
            return false;
        }
        
        public final boolean getShowTagSheet() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component1() {
            return null;
        }
        
        public final boolean component10() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        public final long component4() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.Long> component7() {
            return null;
        }
        
        public final boolean component8() {
            return false;
        }
        
        public final boolean component9() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.editor.EditorViewModel.EditorDraft copy(@org.jetbrains.annotations.Nullable()
        java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String body, long dateTimeMillis, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> existingPhotoPaths, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> newPhotoSourceUris, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.Long> selectedTagIds, boolean isSaving, boolean showDiscardDialog, boolean showTagSheet) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001BQ\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00c6\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003Jh\u0010\"\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0001\u00a2\u0006\u0002\u0010#J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020(H\u00d6\u0001J\t\u0010)\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000f\u00a8\u0006*"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorViewModel$EditorSnapshot;", "", "entryId", "", "title", "", "body", "dateTimeMillis", "existingPhotoPaths", "", "newPhotoSourceUris", "selectedTagIds", "", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JLjava/util/List;Ljava/util/List;Ljava/util/Set;)V", "getBody", "()Ljava/lang/String;", "getDateTimeMillis", "()J", "getEntryId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getExistingPhotoPaths", "()Ljava/util/List;", "getNewPhotoSourceUris", "getSelectedTagIds", "()Ljava/util/Set;", "getTitle", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JLjava/util/List;Ljava/util/List;Ljava/util/Set;)Lcom/example/mydairy_app/feature/editor/EditorViewModel$EditorSnapshot;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    static final class EditorSnapshot {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long entryId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String title = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String body = null;
        private final long dateTimeMillis = 0L;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> existingPhotoPaths = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> newPhotoSourceUris = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.Long> selectedTagIds = null;
        
        public EditorSnapshot(@org.jetbrains.annotations.Nullable()
        java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String body, long dateTimeMillis, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> existingPhotoPaths, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> newPhotoSourceUris, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.Long> selectedTagIds) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getEntryId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getBody() {
            return null;
        }
        
        public final long getDateTimeMillis() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getExistingPhotoPaths() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getNewPhotoSourceUris() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.Long> getSelectedTagIds() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        public final long component4() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.Long> component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.editor.EditorViewModel.EditorSnapshot copy(@org.jetbrains.annotations.Nullable()
        java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String body, long dateTimeMillis, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> existingPhotoPaths, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> newPhotoSourceUris, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.Long> selectedTagIds) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}