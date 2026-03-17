package com.example.mydairy_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.theme.MyDiaryDimens
import java.io.File

@Composable
fun EntryCard(
    title: String?,
    bodyPreview: String,
    createdAtLabel: String,
    tags: List<String>,
    firstPhotoPath: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.screenPadding),
            horizontalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
            verticalAlignment = Alignment.Top,
        ) {
            if (firstPhotoPath != null) {
                AsyncImage(
                    model = File(firstPhotoPath),
                    contentDescription = stringResource(id = R.string.entry_photo_content_description),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                    error = painterResource(id = android.R.drawable.ic_menu_report_image),
                    modifier = Modifier.size(dimens.entryPhotoSize),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
            ) {
                Text(
                    text = title?.takeIf(String::isNotBlank)
                        ?: stringResource(id = R.string.entry_untitled_title),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = createdAtLabel,
                    style = MaterialTheme.typography.labelMedium,
                )

                Text(
                    text = bodyPreview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

                if (tags.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                    ) {
                        items(items = tags, key = { tag -> tag }) { tag ->
                            TagChip(label = tag)
                        }
                    }
                }
            }
        }
    }
}
