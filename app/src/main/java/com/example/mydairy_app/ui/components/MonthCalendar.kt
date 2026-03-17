package com.example.mydairy_app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mydairy_app.R
import com.example.mydairy_app.ui.theme.MyDiaryDimens
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
fun MonthCalendar(
    displayedMonth: YearMonth,
    datesWithEntries: Set<LocalDate>,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    val dimens = MyDiaryDimens.current
    val locale = Locale.getDefault()
    val firstDayOfWeek = WeekFields.of(locale).firstDayOfWeek
    val weekdayOrder = orderedWeekDays(firstDayOfWeek)
    val leadingEmptyCells = leadingEmptyCells(displayedMonth, firstDayOfWeek)
    val daysInMonth = displayedMonth.lengthOfMonth()
    val totalCells = leadingEmptyCells + daysInMonth
    val rowCount = (totalCells + DAYS_IN_WEEK - 1) / DAYS_IN_WEEK

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.calendar_previous_month),
                modifier = Modifier.clickable(onClick = onPreviousMonth),
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(id = R.string.calendar_next_month),
                modifier = Modifier.clickable(onClick = onNextMonth),
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        ) {
            weekdayOrder.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                )
            }
        }

        repeat(rowCount) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
            ) {
                repeat(DAYS_IN_WEEK) { dayIndex ->
                    val cellIndex = rowIndex * DAYS_IN_WEEK + dayIndex
                    val dayOfMonth = cellIndex - leadingEmptyCells + 1

                    if (dayOfMonth in 1..daysInMonth) {
                        val currentDate = displayedMonth.atDay(dayOfMonth)
                        DayCell(
                            dayOfMonth = dayOfMonth,
                            hasEntries = datesWithEntries.contains(currentDate),
                            onClick = {
                                onDateSelected(currentDate)
                            },
                            modifier = Modifier.weight(1f),
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(DAY_CELL_HEIGHT),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCell(
    dayOfMonth: Int,
    hasEntries: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
): Unit {
    Box(
        modifier = modifier
            .height(DAY_CELL_HEIGHT)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            Text(text = dayOfMonth.toString())
            if (hasEntries) {
                Canvas(modifier = Modifier.size(DOT_SIZE)) {
                    drawCircle(
                        color = MaterialTheme.colorScheme.primary,
                        radius = size.minDimension / 2f,
                        center = Offset(size.width / 2f, size.height / 2f),
                    )
                }
            } else {
                Box(modifier = Modifier.size(DOT_SIZE))
            }
        }
    }
}

private fun orderedWeekDays(firstDayOfWeek: DayOfWeek): List<DayOfWeek> {
    return (0 until DAYS_IN_WEEK).map { offset ->
        firstDayOfWeek.plus(offset.toLong())
    }
}

private fun leadingEmptyCells(month: YearMonth, firstDayOfWeek: DayOfWeek): Int {
    val monthStart = month.atDay(1).dayOfWeek
    return (monthStart.value - firstDayOfWeek.value + DAYS_IN_WEEK) % DAYS_IN_WEEK
}

private const val DAYS_IN_WEEK: Int = 7
private val DAY_CELL_HEIGHT = 56.dp
private val DOT_SIZE = 6.dp
