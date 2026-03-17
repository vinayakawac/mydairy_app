package com.example.mydairy_app.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBack: () -> Unit,
): Unit {
    val context = LocalContext.current
    val dimens = MyDiaryDimens.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel, context) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is SettingsUiEvent.ShowMessage -> {
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
                title = { Text(text = stringResource(id = R.string.settings_title)) },
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
            SettingsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is SettingsUiState.Error -> {
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
                        Text(text = stringResource(id = R.string.settings_error_title))
                        Button(onClick = viewModel::onRetryLoad) {
                            Text(text = stringResource(id = R.string.settings_retry))
                        }
                    }
                }
            }

            is SettingsUiState.Success -> {
                SettingsContent(
                    darkModeOverride = state.darkModeOverride,
                    onSystemSelected = viewModel::onSystemThemeSelected,
                    onLightSelected = viewModel::onLightThemeSelected,
                    onDarkSelected = viewModel::onDarkThemeSelected,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(dimens.screenPadding),
                )
            }
        }
    }
}

@Composable
private fun SettingsContent(
    darkModeOverride: Boolean?,
    onSystemSelected: () -> Unit,
    onLightSelected: () -> Unit,
    onDarkSelected: () -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
    ) {
        Text(text = stringResource(id = R.string.settings_theme_section_title))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        ) {
            FilterChip(
                selected = darkModeOverride == null,
                onClick = onSystemSelected,
                label = {
                    Text(text = stringResource(id = R.string.settings_theme_system))
                },
            )

            FilterChip(
                selected = darkModeOverride == false,
                onClick = onLightSelected,
                label = {
                    Text(text = stringResource(id = R.string.settings_theme_light))
                },
            )

            FilterChip(
                selected = darkModeOverride == true,
                onClick = onDarkSelected,
                label = {
                    Text(text = stringResource(id = R.string.settings_theme_dark))
                },
            )
        }
    }
}
