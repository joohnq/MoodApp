package com.joohnq.health_journal.ui.presentation.health_journal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.joohnq.health_journal.ui.presentation.health_journal.event.HealthJournalEvent
import com.joohnq.health_journal.ui.presentation.health_journal.state.HealthJournalState
import com.joohnq.mood.CustomScreen
import com.joohnq.mood.sharedViewModel
import com.joohnq.mood.ui.presentation.add_journaling_screen.AddJournalingScreen
import com.joohnq.mood.ui.presentation.all_journals.AllJournalScreen
import com.joohnq.mood.viewmodel.HealthJournalViewModel

class HealthJournalScreen : CustomScreen<HealthJournalState>() {
    @Composable
    override fun Screen(): HealthJournalState {
        val healthJournalViewModel: HealthJournalViewModel = sharedViewModel()
        val healthJournalState by healthJournalViewModel.healthJournalState.collectAsState()

        fun onEvent(event: HealthJournalEvent) =
            when (event) {
                HealthJournalEvent.OnGoBack -> onGoBack()
                HealthJournalEvent.OnNavigateToAddHealthJournalScreen ->
                    onNavigate(AddJournalingScreen())

                is HealthJournalEvent.OnClick -> onNavigate(AllJournalScreen(event.localDate))
            }

        return HealthJournalState(
            healthJournal = healthJournalState.healthJournalRecords,
            onEvent = ::onEvent
        )
    }

    @Composable
    override fun UI(state: HealthJournalState) = HealthJournalUI(state)
}