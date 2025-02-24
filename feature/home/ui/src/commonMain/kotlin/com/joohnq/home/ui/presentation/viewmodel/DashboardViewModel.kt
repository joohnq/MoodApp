package com.joohnq.home.ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.domain.mapper.anyError
import com.joohnq.domain.mapper.onSuccess
import com.joohnq.freud_score.ui.viewmodel.FreudScoreIntent
import com.joohnq.freud_score.ui.viewmodel.FreudScoreState
import com.joohnq.freud_score.ui.viewmodel.FreudScoreViewModel
import com.joohnq.mood.ui.viewmodel.MoodIntent
import com.joohnq.mood.ui.viewmodel.MoodState
import com.joohnq.mood.ui.viewmodel.MoodViewModel
import com.joohnq.self_journal.ui.viewmodel.SelfJournalIntent
import com.joohnq.self_journal.ui.viewmodel.SelfJournalState
import com.joohnq.self_journal.ui.viewmodel.SelfJournalViewModel
import com.joohnq.sleep_quality.ui.viewmodel.SleepQualityIntent
import com.joohnq.sleep_quality.ui.viewmodel.SleepQualityState
import com.joohnq.sleep_quality.ui.viewmodel.SleepQualityViewModel
import com.joohnq.stress_level.ui.viewmodel.StressLevelIntent
import com.joohnq.stress_level.ui.viewmodel.StressLevelState
import com.joohnq.stress_level.ui.viewmodel.StressLevelViewModel
import com.joohnq.user.ui.viewmodel.UserIntent
import com.joohnq.user.ui.viewmodel.UserState
import com.joohnq.user.ui.viewmodel.UserViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val userViewModel: UserViewModel,
    private val moodViewModel: MoodViewModel,
    private val freudScoreViewModel: FreudScoreViewModel,
    private val selfJournalViewModel: SelfJournalViewModel,
    private val sleepQualityViewModel: SleepQualityViewModel,
    private val stressLevelViewModel: StressLevelViewModel
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    private val _sideEffect = Channel<DashboardSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onAction(event: DashboardIntent) {
        when (event) {
            DashboardIntent.Get -> {
                moodViewModel.onAction(MoodIntent.GetAll)
                userViewModel.onAction(UserIntent.GetUser)
                stressLevelViewModel.onAction(StressLevelIntent.GetAll)
                sleepQualityViewModel.onAction(SleepQualityIntent.GetAll)
                selfJournalViewModel.onAction(SelfJournalIntent.GetAll)
            }
        }
    }

    init {
        combine(
            userViewModel.state,
            moodViewModel.state,
            freudScoreViewModel.state,
            selfJournalViewModel.state,
            sleepQualityViewModel.state,
            stressLevelViewModel.state
        ) { states ->
            val userState = states[0] as UserState
            val statsState = states[1] as MoodState
            val freudState = states[2] as FreudScoreState
            val healthState = states[3] as SelfJournalState
            val sleepState = states[4] as SleepQualityState
            val stressState = states[5] as StressLevelState

            statsState.records.onSuccess { records ->
                freudScoreViewModel.onAction(
                    FreudScoreIntent.GetFreudScore(records)
                )
            }

            listOf(
                statsState.records,
                userState.user,
                stressState.records,
                sleepState.records,
                healthState.records,
            ).anyError(
                block = { error ->
                    viewModelScope.launch {
                        _sideEffect.send(DashboardSideEffect.ShowError(error))
                    }
                }
            )

            DashboardState(
                user = userState.user,
                moodRecords = statsState.records,
                freudScore = freudState.freudScore,
                selfJournalRecords = healthState.records,
                sleepQualityRecords = sleepState.records,
                stressLevelRecords = stressState.records
            )
        }.onEach { newState ->
            _state.value = newState
        }.launchIn(viewModelScope)
    }
}