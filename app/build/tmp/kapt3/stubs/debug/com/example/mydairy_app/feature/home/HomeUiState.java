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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeUiState;", "", "Error", "Loading", "Success", "Lcom/example/mydairy_app/feature/home/HomeUiState$Error;", "Lcom/example/mydairy_app/feature/home/HomeUiState$Loading;", "Lcom/example/mydairy_app/feature/home/HomeUiState$Success;", "app_debug"})
public abstract interface HomeUiState {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0018"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeUiState$Error;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "cause", "", "searchQuery", "", "isSearchExpanded", "", "(Ljava/lang/Throwable;Ljava/lang/String;Z)V", "getCause", "()Ljava/lang/Throwable;", "()Z", "getSearchQuery", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error implements com.example.mydairy_app.feature.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.Throwable cause = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String searchQuery = null;
        private final boolean isSearchExpanded = false;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable cause, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.Throwable getCause() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSearchQuery() {
            return null;
        }
        
        public final boolean isSearchExpanded() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.Throwable component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        public final boolean component3() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.home.HomeUiState.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable cause, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000b\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0013"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeUiState$Loading;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "searchQuery", "", "isSearchExpanded", "", "(Ljava/lang/String;Z)V", "()Z", "getSearchQuery", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Loading implements com.example.mydairy_app.feature.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String searchQuery = null;
        private final boolean isSearchExpanded = false;
        
        public Loading(@org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSearchQuery() {
            return null;
        }
        
        public final boolean isSearchExpanded() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.home.HomeUiState.Loading copy(@org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\bH\u00c6\u0003J-\u0010\u0012\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\b2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeUiState$Success;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "sections", "", "Lcom/example/mydairy_app/feature/home/HomeSectionUiModel;", "searchQuery", "", "isSearchExpanded", "", "(Ljava/util/List;Ljava/lang/String;Z)V", "()Z", "getSearchQuery", "()Ljava/lang/String;", "getSections", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Success implements com.example.mydairy_app.feature.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> sections = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String searchQuery = null;
        private final boolean isSearchExpanded = false;
        
        public Success(@org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> sections, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> getSections() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSearchQuery() {
            return null;
        }
        
        public final boolean isSearchExpanded() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        public final boolean component3() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.home.HomeUiState.Success copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> sections, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded) {
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