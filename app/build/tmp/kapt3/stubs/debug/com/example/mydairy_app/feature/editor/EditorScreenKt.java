package com.example.mydairy_app.feature.editor;

import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.os.Build;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import com.example.mydairy_app.R;
import com.example.mydairy_app.ui.components.PhotoGridItemUiModel;
import com.example.mydairy_app.ui.theme.MyDiaryDimens;
import java.time.Instant;
import java.time.ZoneId;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u00a8\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u000fH\u0003\u001a/\u0010\u0016\u001a\u00020\u00062\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u000fH\u0007\u00a2\u0006\u0002\u0010\u001c\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082D\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"BODY_FIELD_MIN_LINES", "", "IMAGE_MIME_TYPE", "", "PHOTO_GRID_ROWS", "EditorContent", "", "state", "Lcom/example/mydairy_app/feature/editor/EditorUiState$Editing;", "contentPadding", "Landroidx/compose/foundation/layout/PaddingValues;", "onTitleChanged", "Lkotlin/Function1;", "onBodyChanged", "onPickDate", "Lkotlin/Function0;", "onPickTime", "onAddGallery", "onAddCamera", "onOpenTagSheet", "onRemovePhoto", "onSave", "EditorScreen", "viewModel", "Lcom/example/mydairy_app/feature/editor/EditorViewModel;", "entryId", "", "onBack", "(Lcom/example/mydairy_app/feature/editor/EditorViewModel;Ljava/lang/Long;Lkotlin/jvm/functions/Function0;)V", "app_debug"})
public final class EditorScreenKt {
    private static final int BODY_FIELD_MIN_LINES = 5;
    private static final int PHOTO_GRID_ROWS = 2;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String IMAGE_MIME_TYPE = "image/*";
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void EditorScreen(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.feature.editor.EditorViewModel viewModel, @org.jetbrains.annotations.Nullable()
    java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EditorContent(com.example.mydairy_app.feature.editor.EditorUiState.Editing state, androidx.compose.foundation.layout.PaddingValues contentPadding, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTitleChanged, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onBodyChanged, kotlin.jvm.functions.Function0<kotlin.Unit> onPickDate, kotlin.jvm.functions.Function0<kotlin.Unit> onPickTime, kotlin.jvm.functions.Function0<kotlin.Unit> onAddGallery, kotlin.jvm.functions.Function0<kotlin.Unit> onAddCamera, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenTagSheet, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemovePhoto, kotlin.jvm.functions.Function0<kotlin.Unit> onSave) {
    }
}