package com.example.mydairy_app.feature.tags;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;
import com.example.mydairy_app.R;
import com.example.mydairy_app.data.repository.TagRepository;
import com.example.mydairy_app.domain.model.Tag;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\n\b\u0007\u0018\u0000 (2\u00020\u0001:\u0001(B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\u0012\u0010\u0016\u001a\u00020\u00172\b\b\u0001\u0010\u0018\u001a\u00020\u0019H\u0002J\u001f\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002\u00a2\u0006\u0002\u0010 J\b\u0010!\u001a\u00020\u0017H\u0002J\u000e\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u001dJ\u000e\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020\u001fJ\u0016\u0010&\u001a\u00020\u00172\u0006\u0010%\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u001dJ\u0006\u0010\'\u001a\u00020\u0017R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006)"}, d2 = {"Lcom/example/mydairy_app/feature/tags/TagManagerViewModel;", "Landroidx/lifecycle/ViewModel;", "tagRepository", "Lcom/example/mydairy_app/data/repository/TagRepository;", "(Lcom/example/mydairy_app/data/repository/TagRepository;)V", "_uiEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/example/mydairy_app/feature/tags/TagManagerUiEvent;", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/mydairy_app/feature/tags/TagManagerUiState;", "uiEvent", "Lkotlinx/coroutines/flow/SharedFlow;", "getUiEvent", "()Lkotlinx/coroutines/flow/SharedFlow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "currentTags", "", "Lcom/example/mydairy_app/feature/tags/TagManagerTagUiModel;", "emitMessage", "", "messageRes", "", "isDuplicateTagName", "", "name", "", "excludedTagId", "", "(Ljava/lang/String;Ljava/lang/Long;)Z", "observeTags", "onCreateTag", "rawName", "onDeleteTag", "tagId", "onRenameTag", "onRetryLoad", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TagManagerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.repository.TagRepository tagRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.mydairy_app.feature.tags.TagManagerUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.tags.TagManagerUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.example.mydairy_app.feature.tags.TagManagerUiEvent> _uiEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.example.mydairy_app.feature.tags.TagManagerUiEvent> uiEvent = null;
    @java.lang.Deprecated()
    public static final int EVENT_BUFFER_CAPACITY = 16;
    @java.lang.Deprecated()
    public static final long INVALID_TAG_ID = 0L;
    @org.jetbrains.annotations.NotNull()
    private static final com.example.mydairy_app.feature.tags.TagManagerViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public TagManagerViewModel(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.repository.TagRepository tagRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.mydairy_app.feature.tags.TagManagerUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.example.mydairy_app.feature.tags.TagManagerUiEvent> getUiEvent() {
        return null;
    }
    
    public final void onRetryLoad() {
    }
    
    public final void onCreateTag(@org.jetbrains.annotations.NotNull()
    java.lang.String rawName) {
    }
    
    public final void onRenameTag(long tagId, @org.jetbrains.annotations.NotNull()
    java.lang.String rawName) {
    }
    
    public final void onDeleteTag(long tagId) {
    }
    
    private final void observeTags() {
    }
    
    private final boolean isDuplicateTagName(java.lang.String name, java.lang.Long excludedTagId) {
        return false;
    }
    
    private final java.util.List<com.example.mydairy_app.feature.tags.TagManagerTagUiModel> currentTags() {
        return null;
    }
    
    private final void emitMessage(@androidx.annotation.StringRes()
    int messageRes) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/example/mydairy_app/feature/tags/TagManagerViewModel$Companion;", "", "()V", "EVENT_BUFFER_CAPACITY", "", "INVALID_TAG_ID", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}