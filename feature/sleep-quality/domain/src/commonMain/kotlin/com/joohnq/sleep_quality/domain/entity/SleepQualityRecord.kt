package com.joohnq.sleep_quality.domain.entity

import com.joohnq.core.ui.DatetimeProvider
import kotlinx.datetime.LocalDateTime

data class SleepQualityRecord(
    val id: Int = -1,
    val sleepQuality: SleepQuality = SleepQuality.Fair,
    val startSleeping: String = "--:--",
    val endSleeping: String = "--:--",
    val sleepInfluences: List<SleepInfluences> = emptyList(),
    val createdAt: LocalDateTime = DatetimeProvider.getCurrentDateTime(),
)