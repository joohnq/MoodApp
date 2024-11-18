package com.joohnq.moodapp.model.converters

import androidx.room.TypeConverter
import com.joohnq.moodapp.entities.SleepInfluences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SleepInfluencesConverter {
    @TypeConverter
    fun fromSleepInfluences(value: List<SleepInfluences>): String =
        Json.encodeToString(value.map { it.id })

    @TypeConverter
    fun toStressorsList(value: String): List<SleepInfluences> =
        Json.decodeFromString<List<Int>>(value).map(SleepInfluences::toValue)
}