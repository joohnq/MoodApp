package com.joohnq.user.ui.viewmodel

import com.joohnq.domain.entity.UserPreferences
import com.joohnq.mood.state.UiState

data class UserPreferenceViewModelState(
    val userPreferences: UiState<UserPreferences> = UiState.Idle,
    val adding: UiState<Boolean> = UiState.Idle,
    val updating: UiState<Boolean> = UiState.Idle
)