package com.joohnq.home.ui.presentation.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.joohnq.freud_score.ui.viewmodel.FreudScoreViewModel
import com.joohnq.freud_score.ui.viewmodel.FreudScoreViewModelIntent
import com.joohnq.health_journal.ui.viewmodel.HealthJournalIntent
import com.joohnq.health_journal.ui.viewmodel.HealthJournalViewModel
import com.joohnq.home.ui.presentation.home.event.HomeEvent
import com.joohnq.home.ui.presentation.home.state.HomeState
import com.joohnq.mood.ui.viewmodel.StatsIntent
import com.joohnq.mood.ui.viewmodel.StatsViewModel
import com.joohnq.shared.domain.IDatetimeProvider
import com.joohnq.shared.ui.CustomTab
import com.joohnq.shared.ui.Res
import com.joohnq.shared.ui.home
import com.joohnq.shared.ui.sharedViewModel
import com.joohnq.shared.ui.state.UiState
import com.joohnq.shared.ui.state.UiState.Companion.getValue
import com.joohnq.shared.ui.theme.Drawables
import com.joohnq.sleep_quality.ui.viewmodel.SleepQualityIntent
import com.joohnq.sleep_quality.ui.viewmodel.SleepQualityViewModel
import com.joohnq.stress_level.ui.viewmodel.StressLevelIntent
import com.joohnq.stress_level.ui.viewmodel.StressLevelViewModel
import com.joohnq.user.ui.viewmodel.user.UserViewModel
import com.joohnq.user.ui.viewmodel.user.UserViewModelIntent
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

class HomeScreen : CustomTab<HomeState>() {
    @Composable
    override fun Screen(): HomeState {
        val snackBarHostState = remember { SnackbarHostState() }
        val statsViewModel: StatsViewModel = sharedViewModel()
        val userViewModel: UserViewModel = sharedViewModel()
        val sleepQualityViewModel: SleepQualityViewModel = sharedViewModel()
        val stressLevelViewModel: StressLevelViewModel = sharedViewModel()
        val healthJournalViewModel: HealthJournalViewModel = sharedViewModel()
        val freudScoreViewModel: FreudScoreViewModel = sharedViewModel()
        val scope = rememberCoroutineScope()
        val dateTimeProvider: IDatetimeProvider = koinInject()
        val today = dateTimeProvider.formatDate()
        val userState by userViewModel.state.collectAsState()
        val freudScoreState by freudScoreViewModel.state.collectAsState()
        val statsState by statsViewModel.state.collectAsState()
        val sleepQualityState by sleepQualityViewModel.state.collectAsState()
        val stressLevelState by stressLevelViewModel.state.collectAsState()
        val healthJournalState by healthJournalViewModel.state.collectAsState()

        fun onEvent(event: HomeEvent) =
            when (event) {
                HomeEvent.OnNavigateToFreudScore -> {}
//                    onNavigate(FreudScoreScreen())

                HomeEvent.OnNavigateToMood -> {}
//                    onNavigate(MoodScreen())

                HomeEvent.OnNavigateToHealthJournal -> {}
//                    onNavigate(HealthJournalScreen())

                HomeEvent.OnNavigateToMindfulJournal -> {}

                HomeEvent.OnNavigateToSleepQuality -> {}
//                    onNavigate(SleepQualityScreen())

                HomeEvent.OnNavigateToStressLevel -> {}
//                    onNavigate(StressLevelScreen())
            }

        SideEffect {
            statsViewModel.onAction(StatsIntent.GetStatsRecords)
            userViewModel.onAction(UserViewModelIntent.GetUser)
            stressLevelViewModel.onAction(StressLevelIntent.GetStressLevelRecords)
            sleepQualityViewModel.onAction(SleepQualityIntent.GetSleepQualityRecords)
            healthJournalViewModel.onAction(HealthJournalIntent.GetHealthJournals)
        }

        LaunchedEffect(statsState.statsRecords) {
            freudScoreViewModel.onAction(FreudScoreViewModelIntent.GetFreudScore(statsState.statsRecords.getValue()))
        }

        LaunchedEffect(
            statsState.statsRecords,
            userState.user,
            stressLevelState.stressLevelRecords,
            sleepQualityState.sleepQualityRecords,
            healthJournalState.healthJournalRecords
        ) {
            UiState.onAnyError(
                statsState.statsRecords,
                userState.user,
                stressLevelState.stressLevelRecords,
                sleepQualityState.sleepQualityRecords,
                sleepQualityState.sleepQualityRecords
            ) {
                scope.launch { snackBarHostState.showSnackbar(it) }
            }
        }

        return HomeState(
            today = today,
            userName = userState.user,
            statsRecord = statsState.statsRecords,
            freudScore = freudScoreState.freudScore,
            healthJournal = healthJournalState.healthJournalRecords,
            sleepQuality = sleepQualityState.sleepQualityRecords,
            stressLevel = stressLevelState.stressLevelRecords,
            onEvent = ::onEvent
        )
    }

    override val options: TabOptions
        @Composable
        get() =
            TabOptions(
                icon = painterResource(Drawables.Icons.Home),
                title = stringResource(Res.string.home),
                index = 0u
            )

    @Composable
    override fun UI(state: HomeState) = HomeUI(state = state)
}
