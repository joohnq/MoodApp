package com.joohnq.health_journal.ui.presentation.health_journal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.joohnq.core.ui.CustomScreen
import com.joohnq.core.ui.sharedViewModel
import com.joohnq.health_journal.ui.presentation.health_journal.event.HealthJournalEvent
import com.joohnq.health_journal.ui.presentation.health_journal.state.HealthJournalState
import com.joohnq.health_journal.ui.viewmodel.HealthJournalViewModel
import kotlinx.datetime.LocalDate

class HealthJournalScreen(
    private val onNavigateAddHealthJournal: () -> Unit,
    private val onNavigateAllJournals: (LocalDate) -> Unit,
    private val onGoBack: () -> Unit,
) : CustomScreen<HealthJournalState>() {
    @Composable
    override fun Screen(): HealthJournalState {
        val healthJournalViewModel: HealthJournalViewModel = sharedViewModel()
        val healthJournalState by healthJournalViewModel.state.collectAsState()

        fun onEvent(event: HealthJournalEvent) =
            when (event) {
                HealthJournalEvent.OnGoBack -> onGoBack()
                HealthJournalEvent.OnNavigateToAddHealthJournalScreen -> onNavigateAddHealthJournal()
                is HealthJournalEvent.OnClick -> onNavigateAllJournals(event.localDate)
            }

        return HealthJournalState(
            healthJournal = healthJournalState.healthJournalRecords,
            onEvent = ::onEvent
        )
    }

    @Composable
    override fun UI(state: HealthJournalState) = HealthJournalUI(state)
}
