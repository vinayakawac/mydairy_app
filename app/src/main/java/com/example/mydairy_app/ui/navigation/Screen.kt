package com.example.mydairy_app.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home?$DATE_FILTER_ARG={$DATE_FILTER_ARG}") {
        const val BASE_ROUTE: String = "home"
        const val DATE_FILTER_ARG: String = "dateFilter"
        const val NO_DATE_FILTER: Long = -1L

        fun createRoute(dateFilterMillis: Long? = null): String {
            return dateFilterMillis?.let { "$BASE_ROUTE?$DATE_FILTER_ARG=$it" } ?: BASE_ROUTE
        }
    }
    data object Calendar : Screen("calendar")
    data object TagManager : Screen("tags")
    data object Settings : Screen("settings")

    data object Detail : Screen("detail/{entryId}") {
        const val ENTRY_ID_ARG: String = "entryId"

        fun createRoute(entryId: Long): String = "detail/$entryId"
    }

    data object Editor : Screen("editor?entryId={entryId}") {
        const val BASE_ROUTE: String = "editor"
        const val ENTRY_ID_ARG: String = "entryId"
        const val NEW_ENTRY_ID: Long = -1L

        fun createRoute(entryId: Long?): String {
            return entryId?.let { "$BASE_ROUTE?$ENTRY_ID_ARG=$it" } ?: BASE_ROUTE
        }
    }

    companion object {
        const val SAMPLE_ENTRY_ID: Long = 1L
    }
}
