package com.example.mydairy_app.feature.home;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import com.example.mydairy_app.R;
import com.example.mydairy_app.ui.theme.MyDiaryDimens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000V\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\u001a(\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\n2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0003\u001a\u0012\u0010\u001a\u001a\u00020\u00142\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0003\u001a4\u0010\u001b\u001a\u00020\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00012\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00140\u001f2\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0003\u001a \u0010 \u001a\u00020\u00142\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00140\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0003\u001af\u0010\"\u001a\u00020\u00142\b\b\u0002\u0010#\u001a\u00020$2\u0014\u0010%\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u00140\u001f2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00140\u001f2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00140\u00172\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00140\u00172\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00140\u0017H\u0007\u001a\u0012\u0010)\u001a\u00020\u00142\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0003\u001a\u0012\u0010*\u001a\u00020\u00142\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0003\u001a<\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\n2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00140\u001f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0003\u001aS\u0010.\u001a\u00020\u00142\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0014\u00100\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u00140\u001f2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00140\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0003\u00a2\u0006\u0002\u00102\"\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\u00038BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\b\"\u0018\u0010\t\u001a\u00020\n*\u00020\u00038BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u001a\u0010\r\u001a\u0004\u0018\u00010\n*\u00020\u00038BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\f\"\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u00038BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u00063"}, d2 = {"availableTags", "", "Lcom/example/mydairy_app/feature/home/HomeTagFilterUiModel;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "getAvailableTags", "(Lcom/example/mydairy_app/feature/home/HomeUiState;)Ljava/util/List;", "isSearchExpanded", "", "(Lcom/example/mydairy_app/feature/home/HomeUiState;)Z", "searchQuery", "", "getSearchQuery", "(Lcom/example/mydairy_app/feature/home/HomeUiState;)Ljava/lang/String;", "selectedDateFilterLabel", "getSelectedDateFilterLabel", "selectedTagId", "", "getSelectedTagId", "(Lcom/example/mydairy_app/feature/home/HomeUiState;)Ljava/lang/Long;", "DateFilterBar", "", "dateLabel", "onClear", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "EmptyState", "EntrySectionList", "sections", "Lcom/example/mydairy_app/feature/home/HomeSectionUiModel;", "onOpenDetail", "Lkotlin/Function1;", "ErrorState", "onRetry", "HomeScreen", "viewModel", "Lcom/example/mydairy_app/feature/home/HomeViewModel;", "onOpenEditor", "onOpenCalendar", "onOpenTagManager", "onOpenSettings", "LoadingState", "SearchEmptyState", "SearchField", "query", "onQueryChange", "TagFiltersRow", "tags", "onSelectTag", "onSelectAll", "(Ljava/util/List;Ljava/lang/Long;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;)V", "app_debug"})
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
    private static final void DateFilterBar(java.lang.String dateLabel, kotlin.jvm.functions.Function0<kotlin.Unit> onClear, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TagFiltersRow(java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> tags, java.lang.Long selectedTagId, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSelectTag, kotlin.jvm.functions.Function0<kotlin.Unit> onSelectAll, androidx.compose.ui.Modifier modifier) {
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
    
    private static final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> getAvailableTags(com.example.mydairy_app.feature.home.HomeUiState $this$availableTags) {
        return null;
    }
    
    private static final java.lang.Long getSelectedTagId(com.example.mydairy_app.feature.home.HomeUiState $this$selectedTagId) {
        return null;
    }
    
    private static final java.lang.String getSelectedDateFilterLabel(com.example.mydairy_app.feature.home.HomeUiState $this$selectedDateFilterLabel) {
        return null;
    }
}