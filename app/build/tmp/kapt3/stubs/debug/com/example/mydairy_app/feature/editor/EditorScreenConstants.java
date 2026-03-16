package com.example.mydairy_app.feature.editor;

import android.app.TimePickerDialog;
import android.net.Uri;
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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/example/mydairy_app/feature/editor/EditorScreenConstants;", "", "()V", "BODY_FIELD_MIN_LINES", "", "IMAGE_MIME_TYPE", "", "PHOTO_GRID_ROWS", "app_debug"})
final class EditorScreenConstants {
    public static final int BODY_FIELD_MIN_LINES = 5;
    public static final int PHOTO_GRID_ROWS = 2;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String IMAGE_MIME_TYPE = "image/*";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.mydairy_app.feature.editor.EditorScreenConstants INSTANCE = null;
    
    private EditorScreenConstants() {
        super();
    }
}