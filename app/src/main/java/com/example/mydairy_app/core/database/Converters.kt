package com.example.mydairy_app.core.database

import androidx.room.TypeConverter
import java.time.Instant
import org.json.JSONArray

class Converters {
    @TypeConverter
    fun fromEpochMillis(value: Long?): Instant? {
        return value?.let(Instant::ofEpochMilli)
    }

    @TypeConverter
    fun toEpochMillis(value: Instant?): Long? {
        return value?.toEpochMilli()
    }

    @TypeConverter
    fun fromPhotoPaths(value: String?): List<String> {
        if (value.isNullOrBlank()) {
            return emptyList()
        }

        val jsonArray = JSONArray(value)
        return List(jsonArray.length()) { index ->
            jsonArray.optString(index)
        }
    }

    @TypeConverter
    fun toPhotoPaths(paths: List<String>?): String {
        val jsonArray = JSONArray()
        paths.orEmpty().forEach(jsonArray::put)
        return jsonArray.toString()
    }
}
