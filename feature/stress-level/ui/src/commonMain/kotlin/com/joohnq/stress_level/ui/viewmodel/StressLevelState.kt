package com.joohnq.stress_level.ui.viewmodel

import com.joohnq.core.ui.entity.UiState
import com.joohnq.stress_level.domain.entity.StressLevelRecord

data class StressLevelState(
    val stressLevelRecords: UiState<List<StressLevelRecord>> = UiState.Idle,
)