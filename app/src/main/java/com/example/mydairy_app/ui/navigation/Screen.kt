package com.example.mydairy_app.ui.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    data object Home : Screen("home?dateFilter={dateFilter}&searchQuery={searchQuery}&tagName={tagName}") {
        const val BASE_ROUTE: String = "home"
        const val DATE_FILTER_ARG: String = "dateFilter"
        const val SEARCH_QUERY_ARG: String = "searchQuery"
        const val TAG_NAME_ARG: String = "tagName"
        const val NO_DATE_FILTER: Long = -1L
        const val EMPTY_QUERY: String = ""

        fun createRoute(
            dateFilterMillis: Long? = null,
            searchQuery: String? = null,
            tagName: String? = null,
        ): String {
            val params = mutableListOf<String>()
            dateFilterMillis?.let { millis ->
                params += "$DATE_FILTER_ARG=$millis"
            }
            searchQuery
                ?.trim()
                ?.takeIf(String::isNotEmpty)
                ?.let { query ->
                    params += "$SEARCH_QUERY_ARG=${encodeRouteParam(query)}"
                }
            tagName
                ?.trim()
                ?.takeIf(String::isNotEmpty)
                ?.let { value ->
                    params += "$TAG_NAME_ARG=${encodeRouteParam(value)}"
                }

            return if (params.isEmpty()) {
                BASE_ROUTE
            } else {
                val query = params.joinToString(separator = "&")
                "$BASE_ROUTE?$query"
            }
        }
    }
    data object Calendar : Screen("calendar")
    data object TagManager : Screen("tags")
    data object Settings : Screen("settings")
    data object Agent : Screen("agent")

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

private fun encodeRouteParam(value: String): String {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}
