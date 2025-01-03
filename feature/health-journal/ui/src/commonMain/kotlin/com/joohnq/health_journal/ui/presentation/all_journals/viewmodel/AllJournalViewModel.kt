package com.joohnq.health_journal.ui.presentation.all_journals.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

class AllJournalViewModel : ViewModel() {
    private val _state = MutableStateFlow(AllJournalViewModelState())
    val state: StateFlow<AllJournalViewModelState> =
        _state.asStateFlow()

    fun onAction(intent: AllJournalViewModelIntent) {
        when (intent) {
            is AllJournalViewModelIntent.UpdateCurrentDeleteId -> updateCurrentDeleteId(intent.id)
            is AllJournalViewModelIntent.UpdateOpenDeleteDialog -> updateOpenDeleteDialog(intent.openDeleteDialog)
            is AllJournalViewModelIntent.UpdateSelectedDateTime -> updateSelectedDateTime(intent.selectedDateTime)
            is AllJournalViewModelIntent.ResetState -> resetState()
        }
    }

    private fun updateCurrentDeleteId(id: Int) {
        _state.update {
            it.copy(currentDeleteId = id)
        }
    }

    private fun updateOpenDeleteDialog(openDeleteDialog: Boolean) {
        _state.update {
            it.copy(openDeleteDialog = openDeleteDialog)
        }
    }

    private fun updateSelectedDateTime(selectedDateTime: LocalDate) {
        _state.update {
            it.copy(selectedDateTime = selectedDateTime)
        }
    }

    private fun resetState() {
        _state.update {
            AllJournalViewModelState()
        }
    }
}