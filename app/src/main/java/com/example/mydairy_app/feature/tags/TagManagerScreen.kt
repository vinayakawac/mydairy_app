package com.example.mydairy_app.feature.tags

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.theme.MyDiaryDimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagManagerScreen(
    viewModel: TagManagerViewModel = hiltViewModel(),
    onBack: () -> Unit,
): Unit {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val dimens = MyDiaryDimens.current
    var newTagName by rememberSaveable { mutableStateOf(EMPTY_TEXT) }
    var renameTarget by remember { mutableStateOf<TagManagerTagUiModel?>(null) }
    var renameValue by rememberSaveable { mutableStateOf(EMPTY_TEXT) }
    var deleteTarget by remember { mutableStateOf<TagManagerTagUiModel?>(null) }

    LaunchedEffect(viewModel, context) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is TagManagerUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.messageRes),
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.tags_title)) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(id = R.string.go_back))
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        when (val state = uiState) {
            TagManagerUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is TagManagerUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(dimens.screenPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                    ) {
                        Text(text = stringResource(id = R.string.tags_error_title))
                        Button(onClick = viewModel::onRetryLoad) {
                            Text(text = stringResource(id = R.string.tags_retry))
                        }
                    }
                }
            }

            is TagManagerUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(dimens.screenPadding),
                    verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
                ) {
                    item {
                        Text(text = stringResource(id = R.string.tags_add_section_title))
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                        ) {
                            OutlinedTextField(
                                value = newTagName,
                                onValueChange = { value -> newTagName = value },
                                label = {
                                    Text(text = stringResource(id = R.string.tags_name_label))
                                },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                            Button(
                                onClick = {
                                    viewModel.onCreateTag(newTagName)
                                    newTagName = EMPTY_TEXT
                                },
                            ) {
                                Text(text = stringResource(id = R.string.tags_add_action))
                            }
                        }
                    }

                    item {
                        Text(text = stringResource(id = R.string.tags_list_section_title))
                    }

                    if (state.tags.isEmpty()) {
                        item {
                            Text(text = stringResource(id = R.string.tags_empty_description))
                        }
                    } else {
                        items(state.tags, key = { tag -> tag.id }) { tag ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(text = tag.name)
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
                                ) {
                                    TextButton(onClick = {
                                        renameTarget = tag
                                        renameValue = tag.name
                                    }) {
                                        Text(text = stringResource(id = R.string.tags_rename_action))
                                    }
                                    TextButton(onClick = {
                                        deleteTarget = tag
                                    }) {
                                        Text(text = stringResource(id = R.string.tags_delete_action))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val renameTag = renameTarget
    if (renameTag != null) {
        AlertDialog(
            onDismissRequest = {
                renameTarget = null
            },
            title = {
                Text(text = stringResource(id = R.string.tags_rename_title))
            },
            text = {
                OutlinedTextField(
                    value = renameValue,
                    onValueChange = { value -> renameValue = value },
                    label = {
                        Text(text = stringResource(id = R.string.tags_name_label))
                    },
                    singleLine = true,
                )
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.onRenameTag(
                        tagId = renameTag.id,
                        rawName = renameValue,
                    )
                    renameTarget = null
                }) {
                    Text(text = stringResource(id = R.string.tags_rename_confirm))
                }
            },
            dismissButton = {
                Button(onClick = {
                    renameTarget = null
                }) {
                    Text(text = stringResource(id = R.string.tags_delete_cancel))
                }
            },
        )
    }

    val deletingTag = deleteTarget
    if (deletingTag != null) {
        AlertDialog(
            onDismissRequest = {
                deleteTarget = null
            },
            title = {
                Text(text = stringResource(id = R.string.tags_delete_title))
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.tags_delete_message,
                        deletingTag.name,
                    ),
                )
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.onDeleteTag(deletingTag.id)
                    deleteTarget = null
                }) {
                    Text(text = stringResource(id = R.string.tags_delete_confirm))
                }
            },
            dismissButton = {
                Button(onClick = {
                    deleteTarget = null
                }) {
                    Text(text = stringResource(id = R.string.tags_delete_cancel))
                }
            },
        )
    }
}

private const val EMPTY_TEXT: String = ""
