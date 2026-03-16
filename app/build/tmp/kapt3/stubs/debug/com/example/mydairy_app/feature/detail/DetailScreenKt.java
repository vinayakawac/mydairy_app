package com.example.mydairy_app.feature.detail;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import com.example.mydairy_app.R;
import com.example.mydairy_app.ui.components.PhotoGridItemUiModel;
import com.example.mydairy_app.ui.theme.MyDiaryDimens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\u001a:\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u000bH\u0003\u001a4\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00030\u000bH\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"PHOTO_GRID_ROWS", "", "DetailContent", "", "state", "Lcom/example/mydairy_app/feature/detail/DetailUiState$Success;", "contentPadding", "Landroidx/compose/foundation/layout/PaddingValues;", "onDelete", "Lkotlin/Function0;", "onPhotoClick", "Lkotlin/Function1;", "", "DetailScreen", "viewModel", "Lcom/example/mydairy_app/feature/detail/DetailViewModel;", "onBack", "onEdit", "", "app_debug"})
public final class DetailScreenKt {
    private static final int PHOTO_GRID_ROWS = 2;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DetailScreen(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.feature.detail.DetailViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onEdit) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DetailContent(com.example.mydairy_app.feature.detail.DetailUiState.Success state, androidx.compose.foundation.layout.PaddingValues contentPadding, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhotoClick) {
    }
}