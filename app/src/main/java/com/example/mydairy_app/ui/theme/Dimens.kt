package com.example.mydairy_app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimens(
    val screenPadding: Dp,
    val sectionSpacing: Dp,
    val itemSpacing: Dp,
)

private val DefaultAppDimens = AppDimens(
    screenPadding = 16.dp,
    sectionSpacing = 12.dp,
    itemSpacing = 8.dp,
)

val LocalAppDimens = staticCompositionLocalOf { DefaultAppDimens }

object MyDiaryDimens {
    val default: AppDimens = DefaultAppDimens

    val current: AppDimens
        @Composable
        get() = LocalAppDimens.current
}
