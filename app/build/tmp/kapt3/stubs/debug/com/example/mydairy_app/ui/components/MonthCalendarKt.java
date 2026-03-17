package com.example.mydairy_app.ui.components;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import com.example.mydairy_app.R;
import com.example.mydairy_app.ui.theme.MyDiaryDimens;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u001a0\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0003\u001aX\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00070\u00182\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u001a\u0018\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001e2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0004\"\u0010\u0010\u0005\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0004\u00a8\u0006\u001f"}, d2 = {"DAYS_IN_WEEK", "", "DAY_CELL_HEIGHT", "Landroidx/compose/ui/unit/Dp;", "F", "DOT_SIZE", "DayCell", "", "dayOfMonth", "hasEntries", "", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "MonthCalendar", "displayedMonth", "Ljava/time/YearMonth;", "datesWithEntries", "", "Ljava/time/LocalDate;", "onPreviousMonth", "onNextMonth", "onDateSelected", "Lkotlin/Function1;", "leadingEmptyCells", "month", "firstDayOfWeek", "Ljava/time/DayOfWeek;", "orderedWeekDays", "", "app_debug"})
public final class MonthCalendarKt {
    private static final int DAYS_IN_WEEK = 7;
    private static final float DAY_CELL_HEIGHT = 0.0F;
    private static final float DOT_SIZE = 0.0F;
    
    @androidx.compose.runtime.Composable()
    public static final void MonthCalendar(@org.jetbrains.annotations.NotNull()
    java.time.YearMonth displayedMonth, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.time.LocalDate> datesWithEntries, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPreviousMonth, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNextMonth, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.time.LocalDate, kotlin.Unit> onDateSelected, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DayCell(int dayOfMonth, boolean hasEntries, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.util.List<java.time.DayOfWeek> orderedWeekDays(java.time.DayOfWeek firstDayOfWeek) {
        return null;
    }
    
    private static final int leadingEmptyCells(java.time.YearMonth month, java.time.DayOfWeek firstDayOfWeek) {
        return 0;
    }
}