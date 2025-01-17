package com.joohnq.mood.domain.entity

import com.joohnq.core.ui.DatetimeProvider
import kotlinx.datetime.LocalDateTime

data class StatsRecord(
    val id: Int = -1,
    val mood: Mood = Mood.Neutral,
    val description: String = "",
    val createdAt: LocalDateTime = DatetimeProvider.getCurrentDateTime(),
)
