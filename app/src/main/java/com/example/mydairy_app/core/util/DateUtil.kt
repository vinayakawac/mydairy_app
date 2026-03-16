package com.example.mydairy_app.core.util

import java.time.Instant
import java.time.ZoneId

object DateUtil {
    private const val ONE_DAY_IN_DAYS: Long = 1L
    private const val END_OF_DAY_OFFSET_MILLIS: Long = 1L

    fun nowEpochMillis(): Long {
        return System.currentTimeMillis()
    }

    fun startOfDayMillis(epochMillis: Long, zoneId: ZoneId = ZoneId.systemDefault()): Long {
        val localDate = Instant.ofEpochMilli(epochMillis)
            .atZone(zoneId)
            .toLocalDate()

        return localDate
            .atStartOfDay(zoneId)
            .toInstant()
            .toEpochMilli()
    }

    fun endOfDayMillis(epochMillis: Long, zoneId: ZoneId = ZoneId.systemDefault()): Long {
        val nextDayStartMillis = Instant.ofEpochMilli(startOfDayMillis(epochMillis, zoneId))
            .atZone(zoneId)
            .plusDays(ONE_DAY_IN_DAYS)
            .toInstant()
            .toEpochMilli()

        return nextDayStartMillis - END_OF_DAY_OFFSET_MILLIS
    }
}
