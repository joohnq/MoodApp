package com.joohnq.stress_level.domain.entity

import com.joohnq.shared.domain.DatetimeProvider
import kotlinx.datetime.LocalDateTime

data class StressLevelRecord(
    val id: Int = 0,
    val stressLevel: StressLevel = StressLevel.Three,
    val stressors: List<Stressor> = emptyList(),
    val date: LocalDateTime = DatetimeProvider.getCurrentDateTime(),
)