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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0016\u0010\u0006\u001a\u00020\u0003*\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002\u001a\u001c\u0010\t\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a)\u0010\r\u001a\u00020\u0003*\u00020\u00032\f\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002\u00a2\u0006\u0002\u0010\u0010\"\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0011"}, d2 = {"availableTags", "", "Lcom/example/mydairy_app/feature/home/HomeTagFilterUiModel;", "Lcom/example/mydairy_app/feature/home/HomeUiState;", "getAvailableTags", "(Lcom/example/mydairy_app/feature/home/HomeUiState;)Ljava/util/List;", "withDateFilter", "selectedDateFilterLabel", "", "withSearch", "searchQuery", "isSearchExpanded", "", "withTagFilters", "selectedTagId", "", "(Lcom/example/mydairy_app/feature/home/HomeUiState;Ljava/util/List;Ljava/lang/Long;)Lcom/example/mydairy_app/feature/home/HomeUiState;", "app_debug"})
public final class HomeViewModelKt {
    
    private static final com.example.mydairy_app.feature.home.HomeUiState withSearch(com.example.mydairy_app.feature.home.HomeUiState $this$withSearch, java.lang.String searchQuery, boolean isSearchExpanded) {
        return null;
    }
    
    private static final com.example.mydairy_app.feature.home.HomeUiState withTagFilters(com.example.mydairy_app.feature.home.HomeUiState $this$withTagFilters, java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> availableTags, java.lang.Long selectedTagId) {
        return null;
    }
    
    private static final com.example.mydairy_app.feature.home.HomeUiState withDateFilter(com.example.mydairy_app.feature.home.HomeUiState $this$withDateFilter, java.lang.String selectedDateFilterLabel) {
        return null;
    }
    
    private static final java.util.List<com.example.mydairy_app.feature.home.HomeTagFilterUiModel> getAvailableTags(com.example.mydairy_app.feature.home.HomeUiState $this$availableTags) {
        return null;
    }
}