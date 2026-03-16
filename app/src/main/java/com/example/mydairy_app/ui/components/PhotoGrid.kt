package com.example.mydairy_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.theme.MyDiaryDimens

data class PhotoGridItemUiModel(
    val key: String,
    val source: String,
)

@Composable
fun PhotoGrid(
    photos: List<PhotoGridItemUiModel>,
    onRemovePhoto: (String) -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = dimens.photoGridCellMinSize),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        items(
            items = photos,
            key = PhotoGridItemUiModel::key,
        ) { photo ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                ) {
                    AsyncImage(
                        model = photo.source,
                        contentDescription = stringResource(id = R.string.entry_photo_content_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimens.photoGridCellMinSize),
                    )
                    Button(
                        onClick = { onRemovePhoto(photo.key) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(text = stringResource(id = R.string.editor_remove_photo))
                    }
                }
            }
        }
    }
}
