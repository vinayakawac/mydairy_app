package com.example.mydairy_app.feature.home;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import com.example.mydairy_app.R;
import com.example.mydairy_app.ui.theme.MyDiaryDimens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a\u0012\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a4\u0010\f\u001a\u00020\t2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\t0\u00112\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a \u0010\u0013\u001a\u00020\t2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00152\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001af\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\u00182\u0014\u0010\u0019\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0012\u0004\u0012\u00020\t0\u00112\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\t0\u00112\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u00152\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\u00152\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u0015H\u0007\u001a\u0012\u0010\u001d\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a\u0012\u0010\u001e\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a<\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u00052\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\u00112\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\t0\u00152\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0005*\u00020\u00028BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006#"}, d2 = {"isSearchExpanded", "", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "(Lcom/example/mydairy_app/feature/home/HomeUiState;)Z", "searchQuery", "", "getSearchQuery", "(Lcom/example/mydairy_app/feature/home/HomeUiState;)Ljava/lang/String;", "EmptyState", "", "modifier", "Landroidx/compose/ui/Modifier;", "EntrySectionList", "sections", "", "Lcom/example/mydairy_app/feature/home/HomeSectionUiModel;", "onOpenDetail", "Lkotlin/Function1;", "", "ErrorState", "onRetry", "Lkotlin/Function0;", "HomeScreen", "viewModel", "Lcom/example/mydairy_app/feature/home/HomeViewModel;", "onOpenEditor", "onOpenCalendar", "onOpenTagManager", "onOpenSettings", "LoadingState", "SearchEmptyState", "SearchField", "query", "onQueryChange", "onClear", "app_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.feature.home.HomeViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onOpenEditor, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onOpenDetail, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenCalendar, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenTagManager, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenSettings) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SearchField(java.lang.String query, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onQueryChange, kotlin.jvm.functions.Function0<kotlin.Unit> onClear, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EntrySectionList(java.util.List<com.example.mydairy_app.feature.home.HomeSectionUiModel> sections, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onOpenDetail, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LoadingState(androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyState(androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SearchEmptyState(androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorState(kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.lang.String getSearchQuery(com.example.mydairy_app.feature.home.HomeUiState $this$searchQuery) {
        return null;
    }
    
    private static final boolean isSearchExpanded(com.example.mydairy_app.feature.home.HomeUiState $this$isSearchExpanded) {
        return false;
    }
}