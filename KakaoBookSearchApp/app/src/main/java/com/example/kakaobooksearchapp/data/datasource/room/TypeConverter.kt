package com.example.kakaobooksearchapp.data.datasource.room

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StringListTypeConverter {

    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        return Json.encodeToString(stringList)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        if (value.isBlank()) {
            return emptyList()
        }
        return Json.decodeFromString(value)
    }
}