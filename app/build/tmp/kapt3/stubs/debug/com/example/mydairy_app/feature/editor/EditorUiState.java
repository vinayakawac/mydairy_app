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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorUiState;", "", "Editing", "Error", "Loading", "Lcom/example/mydairy_app/feature/editor/EditorUiState$Editing;", "Lcom/example/mydairy_app/feature/editor/EditorUiState$Error;", "Lcom/example/mydairy_app/feature/editor/EditorUiState$Loading;", "app_debug"})
public abstract interface EditorUiState {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b$\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001By\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\t\u0010\'\u001a\u00020\u0011H\u00c6\u0003J\t\u0010(\u001a\u00020\u0011H\u00c6\u0003J\t\u0010)\u001a\u00020\u0011H\u00c6\u0003J\t\u0010*\u001a\u00020\u0005H\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0005H\u00c6\u0003J\t\u0010.\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010/\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003J\u000f\u00100\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bH\u00c6\u0003J\u000f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u00c6\u0003J\u009a\u0001\u00102\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u0011H\u00c6\u0001\u00a2\u0006\u0002\u00103J\u0013\u00104\u001a\u00020\u00112\b\u00105\u001a\u0004\u0018\u000106H\u00d6\u0003J\t\u00107\u001a\u000208H\u00d6\u0001J\t\u00109\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001dR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\u0013\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0016\u00a8\u0006:"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorUiState$Editing;", "Lcom/example/mydairy_app/feature/editor/EditorUiState;", "entryId", "", "title", "", "body", "dateTimeMillis", "dateLabel", "timeLabel", "photos", "", "Lcom/example/mydairy_app/feature/editor/EditorPhotoUiModel;", "tags", "Lcom/example/mydairy_app/feature/editor/EditorTagUiModel;", "selectedTagNames", "isSaving", "", "showDiscardDialog", "showTagSheet", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;ZZZ)V", "getBody", "()Ljava/lang/String;", "getDateLabel", "getDateTimeMillis", "()J", "getEntryId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "()Z", "getPhotos", "()Ljava/util/List;", "getSelectedTagNames", "getShowDiscardDialog", "getShowTagSheet", "getTags", "getTimeLabel", "getTitle", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;ZZZ)Lcom/example/mydairy_app/feature/editor/EditorUiState$Editing;", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Editing implements com.example.mydairy_app.feature.editor.EditorUiState {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long entryId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String title = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String body = null;
        private final long dateTimeMillis = 0L;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String dateLabel = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String timeLabel = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.mydairy_app.feature.editor.EditorPhotoUiModel> photos = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.mydairy_app.feature.editor.EditorTagUiModel> tags = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> selectedTagNames = null;
        private final boolean isSaving = false;
        private final boolean showDiscardDialog = false;
        private final boolean showTagSheet = false;
        
        public Editing(@org.jetbrains.annotations.Nullable()
        java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String body, long dateTimeMillis, @org.jetbrains.annotations.NotNull()
        java.lang.String dateLabel, @org.jetbrains.annotations.NotNull()
        java.lang.String timeLabel, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.editor.EditorPhotoUiModel> photos, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.editor.EditorTagUiModel> tags, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> selectedTagNames, boolean isSaving, boolean showDiscardDialog, boolean showTagSheet) {
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
        public final java.lang.String getDateLabel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTimeLabel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.mydairy_app.feature.editor.EditorPhotoUiModel> getPhotos() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.mydairy_app.feature.editor.EditorTagUiModel> getTags() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getSelectedTagNames() {
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
        
        public final boolean component11() {
            return false;
        }
        
        public final boolean component12() {
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
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.mydairy_app.feature.editor.EditorPhotoUiModel> component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.mydairy_app.feature.editor.EditorTagUiModel> component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.editor.EditorUiState.Editing copy(@org.jetbrains.annotations.Nullable()
        java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
        java.lang.String title, @org.jetbrains.annotations.NotNull()
        java.lang.String body, long dateTimeMillis, @org.jetbrains.annotations.NotNull()
        java.lang.String dateLabel, @org.jetbrains.annotations.NotNull()
        java.lang.String timeLabel, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.editor.EditorPhotoUiModel> photos, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.editor.EditorTagUiModel> tags, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> selectedTagNames, boolean isSaving, boolean showDiscardDialog, boolean showTagSheet) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorUiState$Error;", "Lcom/example/mydairy_app/feature/editor/EditorUiState;", "cause", "", "(Ljava/lang/Throwable;)V", "getCause", "()Ljava/lang/Throwable;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Error implements com.example.mydairy_app.feature.editor.EditorUiState {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Throwable cause = null;
        
        public Error(@org.jetbrains.annotations.Nullable()
        java.lang.Throwable cause) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Throwable getCause() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Throwable component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.editor.EditorUiState.Error copy(@org.jetbrains.annotations.Nullable()
        java.lang.Throwable cause) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorUiState$Loading;", "Lcom/example/mydairy_app/feature/editor/EditorUiState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Loading implements com.example.mydairy_app.feature.editor.EditorUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.mydairy_app.feature.editor.EditorUiState.Loading INSTANCE = null;
        
        private Loading() {
            super();
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