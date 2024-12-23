package com.joohnq.moodapp.ui.presentation.add_stats

import androidx.lifecycle.ViewModel
import com.joohnq.moodapp.domain.Mood
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddStatState(
    val mood: Mood = Mood.Depressed,
    val description: String = "",
)

sealed class AddStatIntent {
    data class UpdateAddingStatsRecordMood(val mood: Mood) : AddStatIntent()
    data class UpdateAddingStatsRecordDescription(val description: String) : AddStatIntent()
    data object ResetState : AddStatIntent()
}

class AddStatViewModel : ViewModel() {
    private val _addStatState = MutableStateFlow(AddStatState())
    val addStatState: StateFlow<AddStatState> = _addStatState.asStateFlow()

    fun onAction(intent: AddStatIntent) {
        when (intent) {
            is AddStatIntent.UpdateAddingStatsRecordMood -> updateAddingStatsRecordMood(intent.mood)
            is AddStatIntent.UpdateAddingStatsRecordDescription -> updateAddingStatsRecordDescription(
                intent.description
            )

            AddStatIntent.ResetState -> resetState()
        }
    }

    private fun updateAddingStatsRecordMood(mood: Mood) {
        _addStatState.update { it.copy(mood = mood) }
    }

    private fun updateAddingStatsRecordDescription(description: String) {
        _addStatState.update { it.copy(description = description) }
    }

    private fun resetState() {
        _addStatState.update { AddStatState() }
    }
}