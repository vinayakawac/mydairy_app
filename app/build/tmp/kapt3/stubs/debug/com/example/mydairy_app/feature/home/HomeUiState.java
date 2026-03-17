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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeUiState;", "", "Error", "Loading", "Success", "Lcom/example/mydairy_app/feature/home/HomeUiState$Error;", "Lcom/example/mydairy_app/feature/home/HomeUiState$Loading;", "Lcom/example/mydairy_app/feature/home/HomeUiState$Success;", "app_debug"})
public abstract interface HomeUiState {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0017\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003JT\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010!J\u0013\u0010\"\u001a\u00020\u00072\b\u0010#\u001a\u0004\u0018\u00010$H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0005H\u00d6\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006("}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeUiState$Error;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "cause", "", "searchQuery", "", "isSearchExpanded", "", "availableTags", "", "Lcom/example/mydairy_app/feature/home/HomeTagFilterUiModel;", "selectedTagId", "", "selectedDateFilterLabel", "(Ljava/lang/Throwable;Ljava/lang/String;ZLjava/util/List;Ljava/lang/Long;Ljava/lang/String;)V", "getAvailableTags", "()Ljava/util/List;", "getCause", "()Ljava/lang/Throwable;", "()Z", "getSearchQuery", "()Ljava/lang/String;", "getSelectedDateFilterLabel", "getSelectedTagId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/Throwable;Ljava/lang/String;ZLjava/util/List;Ljava/lang/Long;Ljava/lang/String;)Lcom/example/mydairy_app/feature/home/HomeUiState$Error;", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error implements com.example.mydairy_app.feature.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.Throwable cause = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String searchQuery = null;
        private final boolean isSearchExpanded = false;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long selectedTagId = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String selectedDateFilterLabel = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable cause, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags, @org.jetbrains.annotations.Nullable()
        java.lang.Long selectedTagId, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedDateFilterLabel) {
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
        public final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> getAvailableTags() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getSelectedTagId() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSelectedDateFilterLabel() {
            return null;
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
        public final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.home.HomeUiState.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable cause, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags, @org.jetbrains.annotations.Nullable()
        java.lang.Long selectedTagId, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedDateFilterLabel) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0014\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JJ\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0015\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006#"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeUiState$Loading;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "searchQuery", "", "isSearchExpanded", "", "availableTags", "", "Lcom/example/mydairy_app/feature/home/HomeTagFilterUiModel;", "selectedTagId", "", "selectedDateFilterLabel", "(Ljava/lang/String;ZLjava/util/List;Ljava/lang/Long;Ljava/lang/String;)V", "getAvailableTags", "()Ljava/util/List;", "()Z", "getSearchQuery", "()Ljava/lang/String;", "getSelectedDateFilterLabel", "getSelectedTagId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/String;ZLjava/util/List;Ljava/lang/Long;Ljava/lang/String;)Lcom/example/mydairy_app/feature/home/HomeUiState$Loading;", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Loading implements com.example.mydairy_app.feature.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String searchQuery = null;
        private final boolean isSearchExpanded = false;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long selectedTagId = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String selectedDateFilterLabel = null;
        
        public Loading(@org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags, @org.jetbrains.annotations.Nullable()
        java.lang.Long selectedTagId, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedDateFilterLabel) {
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
        public final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> getAvailableTags() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getSelectedTagId() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSelectedDateFilterLabel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.home.HomeUiState.Loading copy(@org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags, @org.jetbrains.annotations.Nullable()
        java.lang.Long selectedTagId, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedDateFilterLabel) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0016\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u000eJ\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u0003H\u00c6\u0003J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003JZ\u0010\u001f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010 J\u0013\u0010!\u001a\u00020\b2\b\u0010\"\u001a\u0004\u0018\u00010#H\u00d6\u0003J\t\u0010$\u001a\u00020%H\u00d6\u0001J\t\u0010&\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\'"}, d2 = {"Lcom/example/mydairy_app/feature/home/HomeUiState$Success;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "sections", "", "Lcom/example/mydairy_app/feature/home/HomeSectionUiModel;", "searchQuery", "", "isSearchExpanded", "", "availableTags", "Lcom/example/mydairy_app/feature/home/HomeTagFilterUiModel;", "selectedTagId", "", "selectedDateFilterLabel", "(Ljava/util/List;Ljava/lang/String;ZLjava/util/List;Ljava/lang/Long;Ljava/lang/String;)V", "getAvailableTags", "()Ljava/util/List;", "()Z", "getSearchQuery", "()Ljava/lang/String;", "getSections", "getSelectedDateFilterLabel", "getSelectedTagId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/util/List;Ljava/lang/String;ZLjava/util/List;Ljava/lang/Long;Ljava/lang/String;)Lcom/example/mydairy_app/feature/home/HomeUiState$Success;", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Success implements com.example.mydairy_app.feature.home.HomeUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> sections = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String searchQuery = null;
        private final boolean isSearchExpanded = false;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long selectedTagId = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String selectedDateFilterLabel = null;
        
        public Success(@org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> sections, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags, @org.jetbrains.annotations.Nullable()
        java.lang.Long selectedTagId, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedDateFilterLabel) {
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
        public final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> getAvailableTags() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getSelectedTagId() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSelectedDateFilterLabel() {
            return null;
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
        public final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.mydairy_app.feature.home.HomeUiState.Success copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> sections, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery, boolean isSearchExpanded, @org.jetbrains.annotations.NotNull()
        java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags, @org.jetbrains.annotations.Nullable()
        java.lang.Long selectedTagId, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedDateFilterLabel) {
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