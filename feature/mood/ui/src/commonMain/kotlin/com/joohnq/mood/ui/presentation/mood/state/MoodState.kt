package com.joohnq.mood.ui.presentation.mood.state

import com.joohnq.domain.entity.StatsRecord
import com.joohnq.mood.state.UiState
import com.joohnq.mood.ui.presentation.mood.event.MoodEvent

data class MoodState(
    val statsRecord: StatsRecord?,
    val statsRecords: UiState<List<StatsRecord>>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val onEvent: (MoodEvent) -> Unit = {},
)