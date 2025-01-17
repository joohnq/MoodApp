package com.joohnq.freud_score.ui.presentation.freud_score

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.joohnq.core.ui.sharedViewModel
import com.joohnq.freud_score.ui.presentation.freud_score.event.FreudScoreEvent
import com.joohnq.freud_score.ui.presentation.freud_score.state.FreudScoreState
import com.joohnq.freud_score.ui.viewmodel.FreudScoreViewModel
import com.joohnq.mood.ui.viewmodel.StatsState
import com.joohnq.mood.ui.viewmodel.StatsViewModel

@Composable
fun FreudScoreScreen(
    onGoBack: () -> Unit,
    onNavigateMood: (Int) -> Unit,
    onNavigateAddStat: () -> Unit,
) {
    val statsViewModel: StatsViewModel = sharedViewModel()
    val statsState: StatsState by statsViewModel.state.collectAsState()
    val freudScoreViewModel: FreudScoreViewModel = sharedViewModel()
    val freudScoreState by freudScoreViewModel.state.collectAsState()

    fun onEvent(event: FreudScoreEvent) =
        when (event) {
            is FreudScoreEvent.GoBack -> onGoBack()
            is FreudScoreEvent.NavigateToMoodScreen -> onNavigateMood(event.statsRecord.id)
            is FreudScoreEvent.Add -> onNavigateAddStat()
        }

    FreudScoreUI(
        FreudScoreState(
            freudScore = freudScoreState.freudScore!!,
            statsRecords = statsState.statsRecords,
            onEvent = ::onEvent,
        )
    )
}
