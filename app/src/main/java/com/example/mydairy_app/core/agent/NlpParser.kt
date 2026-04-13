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
        "create diary",
        "add diary",
        "add dairy",
        "write diary",
        "write dairy",
        "today diary",
        "todays diary",
        "today dairy",
        "todays dairy",
        "diary entry",
        "new entry",
        "make an entry",
        "make entry",
    )

    private val updateMarkers: List<String> = listOf(
        "update entry",
        "update diary",
        "update dairy",
        "edit diary",
        "edit dairy",
        "modify diary",
        "change diary",
        "change entry",
        "edit entry",
        "modify entry",
        "edit ",
        "update ",
        "modify ",
        "change ",
    )

    private val deleteLastMarkers: List<String> = listOf(
        "delete last entry",
        "remove last entry",
        "delete latest entry",
        "delete last diary",
        "remove last diary",
        "delete recent diary",
        "delete last",
        "remove last",
    )

    private val deleteTitleMarkers: List<String> = listOf(
        "delete entry titled ",
        "remove entry titled ",
        "delete diary titled ",
        "remove diary titled ",
        "delete dairy titled ",
        "remove dairy titled ",
        "delete titled ",
        "remove titled ",
    )

    private val deleteDateMarkers: List<String> = listOf(
        "delete diary from ",
        "delete dairy from ",
        "remove diary from ",
        "remove dairy from ",
        "delete entries from ",
        "remove entries from ",
        "delete diary on ",
        "delete dairy on ",
        "remove diary on ",
        "remove dairy on ",
        "delete entry on ",
        "remove entry on ",
    )

    private val searchMarkers: List<String> = listOf(
        "search for ",
        "search ",
        "find entries with ",
        "find entries about ",
        "find ",
    )

    private val tagMarkers: List<String> = listOf(
        "show entries tagged ",
        "show entries with tag ",
        "filter by tag ",
        "show tag ",
    )

    private val dateMarkers: List<String> = listOf(
        "show entries from ",
        "show entries on ",
        "entries from ",
        "entries on ",
        "show today",
        "show todays",
        "show today's",
    )

    private val titleMarkers: List<String> = listOf(
        "titled ",
        "called ",
        "title ",
    )

    private val bodyMarkers: List<String> = listOf(
        "saying ",
        "about ",
        "with body ",
        "to this ",
        "to ",
    )

    private val listTagMarkers: List<String> = listOf(
        "list my tags",
        "list tags",
        "what tags do i have",
        "show my tags",
    )

    private val helpMarkers: List<String> = listOf(
        "help",
        "what can you do",
        "what can u do",
        "how to use",
        "commands",
        "what should i say",
    )

    private val smallTalkPhrases: Set<String> = setOf(
        "hi",
        "hello",
        "hey",
        "ok",
        "okay",
        "done",
        "thanks",
        "thank you",
        "good work",
        "cool",
        "nice",
    )

    private val commandLeadingWords: Set<String> = setOf(
        "add",
        "create",
        "make",
        "write",
        "save",
        "update",
        "edit",
        "modify",
        "change",
        "delete",
        "remove",
        "search",
        "find",
        "show",
        "filter",
        "list",
        "help",
    )

    private val createCommandRegex: Regex = Regex(
        pattern = "^(add|create|make|write|save)\\s+(today'?s\\s+)?(diary|dairy|entry|note)\\s*(.*)$",
        option = RegexOption.IGNORE_CASE,
    )

    private val diaryHeadingRegex: Regex = Regex(
        pattern = "^(today'?s\\s+)?(diary|dairy|entry|note)\\s*[:\\-]?\\s*(.*)$",
        option = RegexOption.IGNORE_CASE,
    )

    private val ordinalSuffixRegex: Regex = Regex("(?<=\\d)(st|nd|rd|th)\\b", RegexOption.IGNORE_CASE)
    private val isoDateRegex: Regex = Regex("\\b\\d{4}-\\d{1,2}-\\d{1,2}\\b")

    private val fillerWordsForDate: Set<String> = setOf(
        "my",
        "the",
        "entry",
        "entries",
        "diary",
        "dairy",
        "note",
        "for",
        "on",
        "from",
        "dated",
        "date",
        "of",
        "at",
        "to",
        "this",
    )

    private const val MIN_FALLBACK_WORDS: Int = 4

    fun parse(input: String): ParsedIntent {
        val normalizedInput = input.trim()
        if (normalizedInput.isEmpty()) {
            return ParsedIntent.Unknown(input = input)
        }

        val locale = Locale.getDefault()
        val lowerRaw = normalizedInput.lowercase(locale)
        val lowerNormalized = normalizeForMatching(normalizedInput)

        if (isHelpIntent(lowerNormalized)) {
            return ParsedIntent.Help
        }

        if (isSmallTalkIntent(lowerNormalized)) {
            return ParsedIntent.SmallTalk
        }

        if (listTagMarkers.any(lowerNormalized::contains)) {
            return ParsedIntent.ListTags
        }

        val deleteTitle = extractAfterFirstMarker(
            originalInput = normalizedInput,
            loweredInput = lowerRaw,
            markers = deleteTitleMarkers,
        )
        if (deleteTitle != null) {
            return ParsedIntent.DeleteTitled(title = deleteTitle)
        }

        val deleteByDateIntent = parseDeleteByDateIntent(
            originalInput = normalizedInput,
            loweredRawInput = lowerRaw,
            loweredNormalizedInput = lowerNormalized,
        )
        if (deleteByDateIntent != null) {
            return deleteByDateIntent
        }

        if (deleteLastMarkers.any(lowerNormalized::contains)) {
            return ParsedIntent.DeleteLast
        }

        val searchQuery = extractAfterFirstMarker(
            originalInput = normalizedInput,
            loweredInput = lowerRaw,
            markers = searchMarkers,
        )
        if (searchQuery != null) {
            return ParsedIntent.Search(query = searchQuery)
        }

        val tagName = extractAfterFirstMarker(
            originalInput = normalizedInput,
            loweredInput = lowerRaw,
            markers = tagMarkers,
        )
        if (tagName != null) {
            return ParsedIntent.ShowTag(tagName = tagName)
        }

        val dateText = extractAfterFirstMarker(
            originalInput = normalizedInput,
            loweredInput = lowerRaw,
            markers = dateMarkers,
        )
        if (dateText != null) {
            val parsedDateMillis = parseDateToMillis(dateText)
            if (parsedDateMillis != null) {
                return ParsedIntent.ShowDate(dateMillis = parsedDateMillis)
            }
        }

        val updateIntent = parseUpdateIntent(
            originalInput = normalizedInput,
            loweredRawInput = lowerRaw,
            loweredNormalizedInput = lowerNormalized,
        )
        if (updateIntent != null) {
            return updateIntent
        }

        val createIntent = parseCreateIntent(
            originalInput = normalizedInput,
            loweredRawInput = lowerRaw,
            loweredNormalizedInput = lowerNormalized,
        )
        if (createIntent != null) {
            return createIntent
        }

        if (shouldFallbackToAddEntry(loweredNormalizedInput = lowerNormalized)) {
            return ParsedIntent.AddEntry(
                title = null,
                body = normalizedInput,
            )
        }

        return ParsedIntent.Unknown(input = normalizedInput)
    }

    private fun parseUpdateIntent(
        originalInput: String,
        loweredRawInput: String,
        loweredNormalizedInput: String,
    ): ParsedIntent.UpdateEntry? {
        val hasUpdateSignal = updateMarkers.any { marker ->
            loweredNormalizedInput.contains(marker.trim())
        }
        if (!hasUpdateSignal) {
            return null
        }

        val targetTitle = extractTitle(
            originalInput = originalInput,
            loweredInput = loweredRawInput,
        )

        val targetDateMillis = extractCommandDateMillis(
            originalInput = originalInput,
            loweredInput = loweredRawInput,
            commandMarkers = updateMarkers,
        )

        val explicitBody = extractAfterFirstMarker(
            originalInput = originalInput,
            loweredInput = loweredRawInput,
            markers = bodyMarkers,
        )

        val inferredBody = inferUpdateBody(
            originalInput = originalInput,
            loweredInput = loweredRawInput,
        )

        val body = (explicitBody ?: inferredBody).trim()

        if (targetTitle == null && targetDateMillis == null && body.isEmpty()) {
            return null
        }

        return ParsedIntent.UpdateEntry(
            targetTitle = targetTitle,
            targetDateMillis = targetDateMillis,
            body = body,
        )
    }

    private fun parseDeleteByDateIntent(
        originalInput: String,
        loweredRawInput: String,
        loweredNormalizedInput: String,
    ): ParsedIntent.DeleteByDate? {
        if (!loweredNormalizedInput.contains("delete") && !loweredNormalizedInput.contains("remove")) {
            return null
        }

        if (deleteLastMarkers.any(loweredNormalizedInput::contains)) {
            return null
        }

        val hasDeleteByDateMarker = deleteDateMarkers.any { marker ->
            loweredRawInput.contains(marker)
        }

        val genericDeleteDate = extractCommandDateMillis(
            originalInput = originalInput,
            loweredInput = loweredRawInput,
            commandMarkers = listOf("delete ", "remove "),
        )

        val looksLikeDeleteToday = loweredNormalizedInput.contains("delete today") ||
            loweredNormalizedInput.contains("delete todays") ||
            loweredNormalizedInput.contains("remove today") ||
            loweredNormalizedInput.contains("remove todays")

        if (!hasDeleteByDateMarker && !looksLikeDeleteToday && genericDeleteDate == null) {
            return null
        }

        val dateMillis = genericDeleteDate ?: extractCommandDateMillis(
            originalInput = originalInput,
            loweredInput = loweredRawInput,
            commandMarkers = deleteDateMarkers + listOf("delete ", "remove "),
        ) ?: return null

        return ParsedIntent.DeleteByDate(dateMillis = dateMillis)
    }

    private fun parseCreateIntent(
        originalInput: String,
        loweredRawInput: String,
        loweredNormalizedInput: String,
    ): ParsedIntent.AddEntry? {
        val createRegexBody = createCommandRegex
            .matchEntire(originalInput)
            ?.groupValues
            ?.getOrNull(4)
            ?.trim()
            .orEmpty()

        val headingRegexBody = diaryHeadingRegex
            .matchEntire(originalInput)
            ?.groupValues
            ?.getOrNull(3)
            ?.trim()
            .orEmpty()

        val hasCreateSignal = createMarkers.any(loweredNormalizedInput::contains) ||
            createRegexBody.isNotBlank() ||
            headingRegexBody.isNotBlank()

        if (!hasCreateSignal) {
            return null
        }

        val title = extractTitle(
            originalInput = originalInput,
            loweredInput = loweredRawInput,
        )

        val explicitBody = extractAfterFirstMarker(
            originalInput = originalInput,
            loweredInput = loweredRawInput,
            markers = bodyMarkers,
        )

        val body = when {
            explicitBody != null -> explicitBody
            createRegexBody.isNotBlank() -> createRegexBody
            headingRegexBody.isNotBlank() -> headingRegexBody
            else -> stripKnownCreatePrefix(originalInput).ifBlank { originalInput }
        }.trim()

        return ParsedIntent.AddEntry(
            title = title,
            body = body.ifBlank { originalInput },
        )
    }

    private fun stripKnownCreatePrefix(originalInput: String): String {
        val lowered = originalInput.lowercase(Locale.getDefault())
        for (marker in createMarkers) {
            if (lowered.startsWith(marker)) {
                return originalInput.substring(marker.length).trim()
            }
        }

        return originalInput
    }

    private fun extractTitle(
        originalInput: String,
        loweredInput: String,
    ): String? {
        for (marker in titleMarkers) {
            val markerIndex = loweredInput.indexOf(marker)
            if (markerIndex < 0) {
                continue
            }

            val titleAndMaybeBody = originalInput
                .substring(markerIndex + marker.length)
                .trim()

            if (titleAndMaybeBody.isEmpty()) {
                continue
            }

            val loweredTail = titleAndMaybeBody.lowercase(Locale.getDefault())
            var splitIndex = -1

            for (bodyMarker in bodyMarkers) {
                val markerTailIndex = loweredTail.indexOf(bodyMarker)
                if (markerTailIndex > 0 && (splitIndex < 0 || markerTailIndex < splitIndex)) {
                    splitIndex = markerTailIndex
                }
            }

            val extractedTitle = if (splitIndex > 0) {
                titleAndMaybeBody.substring(0, splitIndex)
            } else {
                titleAndMaybeBody
            }
                .trim()
                .trim('"')

            if (extractedTitle.isNotEmpty()) {
                return extractedTitle
            }
        }

        return null
    }

    private fun inferUpdateBody(
        originalInput: String,
        loweredInput: String,
    ): String {
        var remainder = extractAfterFirstMarker(
            originalInput = originalInput,
            loweredInput = loweredInput,
            markers = updateMarkers,
        ) ?: return ""

        val lowerLocale = Locale.getDefault()

        // Remove heading-like prefixes before body content.
        val headingRegex = Regex(
            pattern = "^(my\\s+)?((today'?s|today|yesterday|tomorrow)\\s+)?(diary|dairy|entry|note)?\\s*",
            option = RegexOption.IGNORE_CASE,
        )
        remainder = headingRegex.replace(remainder, "").trim()

        remainder = stripLeadingDateCandidate(remainder)

        remainder = remainder
            .replace(Regex("^to\\s+this\\s+", RegexOption.IGNORE_CASE), "")
            .replace(Regex("^to\\s+", RegexOption.IGNORE_CASE), "")
            .trim()

        if (smallTalkPhrases.contains(remainder.lowercase(lowerLocale))) {
            return ""
        }

        return remainder
    }

    private fun stripLeadingDateCandidate(text: String): String {
        if (text.isBlank()) {
            return text
        }

        val words = text.split(" ").filter(String::isNotEmpty)
        if (words.isEmpty()) {
            return text
        }

        val maxWindow = minOf(words.size, 4)
        for (size in maxWindow downTo 1) {
            val candidate = words.take(size).joinToString(separator = " ")
            if (parseDateToMillis(candidate) != null) {
                return words.drop(size).joinToString(separator = " ").trim()
            }
        }

        return text
    }

    private fun extractCommandDateMillis(
        originalInput: String,
        loweredInput: String,
        commandMarkers: List<String>,
    ): Long? {
        val header = extractCommandHeader(
            originalInput = originalInput,
            loweredInput = loweredInput,
            commandMarkers = commandMarkers,
        ) ?: return null

        return extractDateFromText(header)
    }

    private fun extractCommandHeader(
        originalInput: String,
        loweredInput: String,
        commandMarkers: List<String>,
    ): String? {
        var earliestMarkerIndex = Int.MAX_VALUE
        var earliestMarker: String? = null

        for (marker in commandMarkers) {
            val markerIndex = loweredInput.indexOf(marker)
            if (markerIndex >= 0 && markerIndex < earliestMarkerIndex) {
                earliestMarkerIndex = markerIndex
                earliestMarker = marker
            }
        }

        val marker = earliestMarker ?: return null
        var header = originalInput
            .substring(earliestMarkerIndex + marker.length)
            .trim()

        if (header.isEmpty()) {
            return null
        }

        val loweredHeader = header.lowercase(Locale.getDefault())
        var splitIndex = -1
        for (bodyMarker in bodyMarkers) {
            val markerIndex = loweredHeader.indexOf(bodyMarker)
            if (markerIndex > 0 && (splitIndex < 0 || markerIndex < splitIndex)) {
                splitIndex = markerIndex
            }
        }

        if (splitIndex > 0) {
            header = header.substring(0, splitIndex).trim()
        }

        return header.ifEmpty { null }
    }

    private fun extractDateFromText(rawText: String): Long? {
        val locale = Locale.getDefault()
        val cleanedText = rawText
            .trim()
            .replace("[^\\p{L}\\p{N}\\s'./-]".toRegex(), " ")
            .replace("\\s+".toRegex(), " ")

        val loweredText = cleanedText.lowercase(locale)
        if (loweredText.contains("today")) {
            return parseDateToMillis("today")
        }
        if (loweredText.contains("yesterday")) {
            return parseDateToMillis("yesterday")
        }
        if (loweredText.contains("tomorrow")) {
            return parseDateToMillis("tomorrow")
        }

        val isoDate = isoDateRegex.find(cleanedText)?.value
        if (isoDate != null) {
            return parseDateToMillis(isoDate)
        }

        val meaningfulWords = cleanedText
            .split(" ")
            .filter(String::isNotEmpty)
            .filterNot { word -> fillerWordsForDate.contains(word.lowercase(locale)) }

        if (meaningfulWords.isEmpty()) {
            return null
        }

        val maxWindow = minOf(meaningfulWords.size, 4)
        for (windowSize in maxWindow downTo 1) {
            for (startIndex in 0..(meaningfulWords.size - windowSize)) {
                val candidate = meaningfulWords
                    .subList(startIndex, startIndex + windowSize)
                    .joinToString(separator = " ")
                val parsedDate = parseDateToMillis(candidate)
                if (parsedDate != null) {
                    return parsedDate
                }
            }
        }

        return null
    }

    private fun shouldFallbackToAddEntry(loweredNormalizedInput: String): Boolean {
        if (loweredNormalizedInput.endsWith("?")) {
            return false
        }

        val words = loweredNormalizedInput
            .split(" ")
            .filter(String::isNotEmpty)

        if (words.size < MIN_FALLBACK_WORDS) {
            return false
        }

        val firstWord = words.firstOrNull() ?: return false
        if (commandLeadingWords.contains(firstWord)) {
            return false
        }

        return true
    }

    private fun isHelpIntent(loweredNormalizedInput: String): Boolean {
        if (loweredNormalizedInput == "?") {
            return true
        }

        return helpMarkers.any(loweredNormalizedInput::contains)
    }

    private fun isSmallTalkIntent(loweredNormalizedInput: String): Boolean {
        if (smallTalkPhrases.contains(loweredNormalizedInput)) {
            return true
        }

        return loweredNormalizedInput.startsWith("hi ") ||
            loweredNormalizedInput.startsWith("hello ") ||
            loweredNormalizedInput.startsWith("hey ")
    }

    private fun normalizeForMatching(input: String): String {
        return input
            .lowercase(Locale.getDefault())
            .replace("[^\\p{L}\\p{N}\\s'?]".toRegex(), " ")
            .replace("\\s+".toRegex(), " ")
            .trim()
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
        val normalizedDateText = rawDateText
            .trim()
            .replace(",", " ")
            .replace(ordinalSuffixRegex, "")
            .replace("\\s+".toRegex(), " ")

        val lowerText = normalizedDateText.lowercase(locale)
        val specialDate = when {
            lowerText == "today" || lowerText == "todays" || lowerText == "today's" -> now
            lowerText == "yesterday" || lowerText == "yesterdays" || lowerText == "yesterday's" -> now.minusDays(1)
            lowerText == "tomorrow" || lowerText == "tomorrows" || lowerText == "tomorrow's" -> now.plusDays(1)
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

        val numericFormatters = listOf(
            DateTimeFormatter.ofPattern("yyyy-M-d", locale),
            DateTimeFormatter.ofPattern("d/M/yyyy", locale),
            DateTimeFormatter.ofPattern("M/d/yyyy", locale),
            DateTimeFormatter.ofPattern("d-M-yyyy", locale),
            DateTimeFormatter.ofPattern("M-d-yyyy", locale),
            DateTimeFormatter.ofPattern("d.M.yyyy", locale),
            DateTimeFormatter.ofPattern("M.d.yyyy", locale),
        )

        for (formatter in numericFormatters) {
            val parsedDate = runCatching {
                LocalDate.parse(normalizedDateText, formatter)
            }.getOrNull()

            if (parsedDate != null) {
                return parsedDate.atStartOfDay(zoneId).toInstant().toEpochMilli()
            }
        }

        val yearlessFormatters = listOf(
            createMonthDayFormatter(pattern = "MMMM d", currentYear = now.year, locale = locale),
            createMonthDayFormatter(pattern = "MMM d", currentYear = now.year, locale = locale),
            createMonthDayFormatter(pattern = "d MMMM", currentYear = now.year, locale = locale),
            createMonthDayFormatter(pattern = "d MMM", currentYear = now.year, locale = locale),
        )

        for (formatter in yearlessFormatters) {
            val parsedDate = runCatching {
                LocalDate.parse(normalizedDateText, formatter)
            }.getOrNull()

            if (parsedDate != null) {
                return parsedDate.atStartOfDay(zoneId).toInstant().toEpochMilli()
            }
        }

        val datedFormatters = listOf(
            DateTimeFormatter.ofPattern("MMMM d yyyy", locale),
            DateTimeFormatter.ofPattern("MMM d yyyy", locale),
            DateTimeFormatter.ofPattern("d MMMM yyyy", locale),
            DateTimeFormatter.ofPattern("d MMM yyyy", locale),
        )

        for (formatter in datedFormatters) {
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
