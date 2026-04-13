package com.example.mydairy_app.core.agent

sealed interface ParsedIntent {
    data class AddEntry(
        val title: String?,
        val body: String,
    ) : ParsedIntent

    data class UpdateEntry(
        val targetTitle: String?,
        val body: String,
    ) : ParsedIntent

    data object DeleteLast : ParsedIntent

    data class DeleteTitled(
        val title: String,
    ) : ParsedIntent

    data class Search(
        val query: String,
    ) : ParsedIntent

    data class ShowTag(
        val tagName: String,
    ) : ParsedIntent

    data class ShowDate(
        val dateMillis: Long,
    ) : ParsedIntent

    data object ListTags : ParsedIntent

    data class Unknown(
        val input: String,
    ) : ParsedIntent
}
