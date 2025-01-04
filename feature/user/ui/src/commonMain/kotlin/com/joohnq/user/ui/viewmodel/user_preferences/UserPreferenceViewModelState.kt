package com.joohnq.user.ui.viewmodel.user_preferences

import com.joohnq.domain.entity.UserPreferences
import com.joohnq.shared.ui.state.UiState

data class UserPreferenceViewModelState(
    val userPreferences: UiState<UserPreferences> = UiState.Idle,
    val adding: UiState<Boolean> = UiState.Idle,
    val updating: UiState<Boolean> = UiState.Idle,
)