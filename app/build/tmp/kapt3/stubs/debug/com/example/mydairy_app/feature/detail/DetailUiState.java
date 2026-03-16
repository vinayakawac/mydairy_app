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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/mydairy_app/feature/detail/DetailUiState;", "", "Error", "Loading", "Success", "Lcom/example/mydairy_app/feature/detail/DetailUiState$Error;", "Lcom/example/mydairy_app/feature/detail/DetailUiState$Loading;", "Lcom/example/mydairy_app/feature/detail/DetailUiState$Success;", "app_debug"})
public abstract interface DetailUiState {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/example/mydairy_app/feature/detail/DetailUiState$Error;", "Lcom/example/mydairy_app/feature/detail/DetailUiState;", "cause", "", "(Ljava/lang/Throwable;)V", "getCause", "()Ljava/lang/Throwable;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Error implements com.example.mydairy_app.feature.detail.DetailUiState {
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
        public final com.example.mydairy_app.feature.detail.DetailUiState.Error copy(@org.jetbrains.annotations.Nullable()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J\t\u0010\t\u001a\u00020\nH\u00d6\u0001\u00a8\u0006\u000b"}, d2 = {"Lcom/example/mydairy_app/feature/detail/DetailUiState$Loading;", "Lcom/example/mydairy_app/feature/detail/DetailUiState;", "()V", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Loading implements com.example.mydairy_app.feature.detail.DetailUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.mydairy_app.feature.detail.DetailUiState.Loading INSTANCE = null;
        
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n\u00a8\u0006\u0017"}, d2 = {"Lcom/example/mydairy_app/feature/detail/DetailUiState$Success;", "Lcom/example/mydairy_app/feature/detail/DetailUiState;", "entry", "Lcom/example/mydairy_app/feature/detail/DetailEntryUiModel;", "isDeleting", "", "showDeleteDialog", "(Lcom/example/mydairy_app/feature/detail/DetailEntryUiModel;ZZ)V", "getEntry", "()Lcom/example/mydairy_app/feature/detail/DetailEntryUiModel;", "()Z", "getShowDeleteDialog", "component1", "component2", "component3", "copy", "equals", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Success implements com.example.mydairy_app.feature.detail.DetailUiState {
        @org.jetbrains.annotations.NotNull()
        private final com.example.mydairy_app.feature.detail.DetailEntryUiModel entry = null;
        private final boolean isDeleting = false;
        private final boolean showDeleteDialog = false;
        
        public Success(@org.jetbrains.annotations.NotNull()
        com.example.mydairy_app.feature.detail.DetailEntryUiModel entry, boolean isDeleting, boolean showDeleteDialog) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.detail.DetailEntryUiModel getEntry() {
            return null;
        }
        
        public final boolean isDeleting() {
            return false;
        }
        
        public final boolean getShowDeleteDialog() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.detail.DetailEntryUiModel component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        public final boolean component3() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.detail.DetailUiState.Success copy(@org.jetbrains.annotations.NotNull()
        com.example.mydairy_app.feature.detail.DetailEntryUiModel entry, boolean isDeleting, boolean showDeleteDialog) {
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