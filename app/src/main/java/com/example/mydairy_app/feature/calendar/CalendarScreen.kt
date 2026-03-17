package com.example.mydairy_app.feature.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.components.MonthCalendar
import com.example.mydairy_app.ui.theme.MyDiaryDimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSelectDate: (Long) -> Unit,
): Unit {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dimens = MyDiaryDimens.current

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is CalendarUiEvent.NavigateToDate -> onSelectDate(event.dateStartMillis)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.calendar_title)) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(id = R.string.go_back))
                    }
                },
            )
        },
    ) { innerPadding ->
        when (val state = uiState) {
            CalendarUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is CalendarUiState.Error -> {
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
                        Text(text = stringResource(id = R.string.calendar_error_title))
                        Button(onClick = viewModel::onRetryLoad) {
                            Text(text = stringResource(id = R.string.calendar_retry))
                        }
                    }
                }
            }

            is CalendarUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(dimens.screenPadding),
                    verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
                ) {
                    Text(text = state.monthLabel)

                    MonthCalendar(
                        displayedMonth = state.displayedMonth,
                        datesWithEntries = state.datesWithEntries,
                        onPreviousMonth = viewModel::onPreviousMonth,
                        onNextMonth = viewModel::onNextMonth,
                        onDateSelected = viewModel::onDateSelected,
                    )

                    if (state.datesWithEntries.isEmpty()) {
                        Text(text = stringResource(id = R.string.calendar_empty_month))
                    }
                }
            }
        }
    }
}
