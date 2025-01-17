package com.joohnq.health_journal.ui.presentation.add_journaling_screen.state

import androidx.compose.material3.SnackbarHostState
import com.joohnq.health_journal.ui.presentation.add_journaling_screen.event.AddJournalingEvent
import com.joohnq.health_journal.ui.presentation.add_journaling_screen.viewmodel.AddingJournalingViewModelIntent
import com.joohnq.health_journal.ui.viewmodel.HealthJournalIntent
import com.joohnq.mood.ui.resource.MoodResource

data class AddJournalingState(
    val snackBarState: SnackbarHostState,
    val selectedMood: MoodResource?,
    val title: String,
    val titleError: String?,
    val desc: String,
    val onAddingAction: (AddingJournalingViewModelIntent) -> Unit = {},
    val onAction: (HealthJournalIntent) -> Unit = {},
    val onEvent: (AddJournalingEvent) -> Unit = {},
)