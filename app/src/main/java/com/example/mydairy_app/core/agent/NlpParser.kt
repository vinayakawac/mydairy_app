package com.example.mydairy_app.core.agent

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale

object NlpParser {

    private val createMarkers: List<String> = listOf(
        "create entry",
        "add entry",
        "new entry",
        "make an entry",
        "make entry",
    )

    private val updateMarkers: List<String> = listOf(
        "update entry",
        "edit entry",
        "modify entry",
    )

    private val deleteLastMarkers: List<String> = listOf(
        "delete last entry",
        "remove last entry",
        "delete last",
        "remove last",
    )

    private val deleteTitleMarkers: List<String> = listOf(
        "delete entry titled ",
        "remove entry titled ",
        "delete titled ",
        "remove titled ",
    )

    private val searchMarkers: List<String> = listOf(
        "search for ",
        "find entries about ",
        "find ",
    )

    private val tagMarkers: List<String> = listOf(
        "show entries tagged ",
        "filter by tag ",
        "show tag ",
    )

    private val dateMarkers: List<String> = listOf(
        "show entries from ",
        "show entries on ",
        "entries from ",
        "entries on ",
    )

    private val titleMarkers: List<String> = listOf(
        "titled ",
        "called ",
    )

    private val bodyMarkers: List<String> = listOf(
        "saying ",
        "about ",
        "with body ",
    )

    private val listTagMarkers: List<String> = listOf(
        "list my tags",
        "list tags",
        "what tags do i have",
        "show my tags",
    )

    fun parse(input: String): ParsedIntent {
        val normalizedInput = input.trim()
        if (normalizedInput.isEmpty()) {
            return ParsedIntent.Unknown(input = input)
        }

        val lower = normalizedInput.lowercase(Locale.getDefault())

        if (listTagMarkers.any(lower::contains)) {
            return ParsedIntent.ListTags
        }

        val deleteTitle = extractAfterFirstMarker(
            originalInput = normalizedInput,
            loweredInput = lower,
            markers = deleteTitleMarkers,
        )
        if (deleteTitle != null) {
            return ParsedIntent.DeleteTitled(title = deleteTitle)
        }

        if (deleteLastMarkers.any(lower::contains)) {
            return ParsedIntent.DeleteLast
        }

        val searchQuery = extractAfterFirstMarker(
            originalInput = normalizedInput,
            loweredInput = lower,
            markers = searchMarkers,
        )
        if (searchQuery != null) {
            return ParsedIntent.Search(query = searchQuery)
        }

        val tagName = extractAfterFirstMarker(
            originalInput = normalizedInput,
            loweredInput = lower,
            markers = tagMarkers,
        )
        if (tagName != null) {
            return ParsedIntent.ShowTag(tagName = tagName)
        }

        val dateText = extractAfterFirstMarker(
            originalInput = normalizedInput,
            loweredInput = lower,
            markers = dateMarkers,
        )
        if (dateText != null) {
            val parsedDateMillis = parseDateToMillis(dateText)
            if (parsedDateMillis != null) {
                return ParsedIntent.ShowDate(dateMillis = parsedDateMillis)
            }
        }

        if (updateMarkers.any(lower::contains)) {
            val targetTitle = extractAfterFirstMarker(
                originalInput = normalizedInput,
                loweredInput = lower,
                markers = titleMarkers,
            )
            val body = extractAfterFirstMarker(
                originalInput = normalizedInput,
                loweredInput = lower,
                markers = bodyMarkers,
            ).orEmpty()

            if (targetTitle != null || body.isNotBlank()) {
                return ParsedIntent.UpdateEntry(
                    targetTitle = targetTitle,
                    body = body,
                )
            }
        }

        if (createMarkers.any(lower::contains)) {
            val title = extractAfterFirstMarker(
                originalInput = normalizedInput,
                loweredInput = lower,
                markers = titleMarkers,
            )
            val body = extractAfterFirstMarker(
                originalInput = normalizedInput,
                loweredInput = lower,
                markers = bodyMarkers,
            ) ?: normalizedInput

            return ParsedIntent.AddEntry(
                title = title,
                body = body,
            )
        }

        return ParsedIntent.Unknown(input = normalizedInput)
    }

    private fun extractAfterFirstMarker(
        originalInput: String,
        loweredInput: String,
        markers: List<String>,
    ): String? {
        for (marker in markers) {
            val markerIndex = loweredInput.indexOf(marker)
            if (markerIndex >= 0) {
                val extracted = originalInput.substring(markerIndex + marker.length)
                    .trim()
                    .trim('"')
                if (extracted.isNotEmpty()) {
                    return extracted
                }
            }
        }

        return null
    }

    private fun parseDateToMillis(rawDateText: String): Long? {
        val locale = Locale.getDefault()
        val zoneId = ZoneId.systemDefault()
        val now = LocalDate.now(zoneId)
        val normalizedDateText = rawDateText.trim()

        val specialDate = when (normalizedDateText.lowercase(locale)) {
            "today" -> now
            "yesterday" -> now.minusDays(1)
            "tomorrow" -> now.plusDays(1)
            else -> null
        }
        if (specialDate != null) {
            return specialDate.atStartOfDay(zoneId).toInstant().toEpochMilli()
        }

        runCatching {
            LocalDate.parse(normalizedDateText)
        }.getOrNull()?.let { parsedDate ->
            return parsedDate.atStartOfDay(zoneId).toInstant().toEpochMilli()
        }

        val formatterWithYear = listOf(
            createMonthDayFormatter(
                pattern = "MMMM d",
                currentYear = now.year,
                locale = locale,
            ),
            createMonthDayFormatter(
                pattern = "MMM d",
                currentYear = now.year,
                locale = locale,
            ),
        )

        for (formatter in formatterWithYear) {
            val parsedDate = runCatching {
                LocalDate.parse(normalizedDateText, formatter)
            }.getOrNull()

            if (parsedDate != null) {
                return parsedDate.atStartOfDay(zoneId).toInstant().toEpochMilli()
            }
        }

        return null
    }

    private fun createMonthDayFormatter(
        pattern: String,
        currentYear: Int,
        locale: Locale,
    ): DateTimeFormatter {
        return DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern(pattern)
            .parseDefaulting(ChronoField.YEAR, currentYear.toLong())
            .toFormatter(locale)
    }
}
