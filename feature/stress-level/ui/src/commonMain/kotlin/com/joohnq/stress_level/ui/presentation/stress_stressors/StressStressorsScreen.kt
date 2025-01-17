package com.joohnq.stress_level.ui.presentation.stress_stressors

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.joohnq.core.ui.CustomScreen
import com.joohnq.core.ui.sharedViewModel
import com.joohnq.stress_level.domain.entity.StressLevelRecord
import com.joohnq.stress_level.domain.mapper.containOther
import com.joohnq.stress_level.ui.mapper.toDomain
import com.joohnq.stress_level.ui.presentation.add_stress_level.viewmodel.AddStressLevelIntent
import com.joohnq.stress_level.ui.presentation.add_stress_level.viewmodel.AddStressLevelViewModel
import com.joohnq.stress_level.ui.presentation.stress_stressors.event.StressStressorsEvent
import com.joohnq.stress_level.ui.presentation.stress_stressors.state.StressStressorsState
import com.joohnq.stress_level.ui.viewmodel.StressLevelIntent
import com.joohnq.stress_level.ui.viewmodel.StressLevelSideEffect
import com.joohnq.stress_level.ui.viewmodel.StressLevelViewModel
import kotlinx.coroutines.launch

class StressStressorsScreen(
    private val onGoBack: () -> Unit,
    private val onNavigateBackToStressLevel: () -> Unit,
) : CustomScreen<StressStressorsState>() {
    @Composable
    override fun Screen(): StressStressorsState {
        val stressLevelViewModel: StressLevelViewModel = sharedViewModel()
        val addStressLevelViewModel: AddStressLevelViewModel = sharedViewModel()
        val snackBarState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val addStressLevelState by addStressLevelViewModel.state.collectAsState()

        fun containsOther(): Boolean =
            addStressLevelState.stressors.toDomain().containOther()

        fun onError(error: Throwable) {
            scope.launch { snackBarState.showSnackbar(error.message.toString()) }
        }

        fun onEvent(event: StressStressorsEvent) =
            when (event) {
                is StressStressorsEvent.GoBack -> onGoBack()
                is StressStressorsEvent.Continue -> {
                    try {
                        if (containsOther() && addStressLevelState.otherValue.isEmpty()) throw Exception(
                            "Please type your other stressor"
                        )

                        stressLevelViewModel.onAction(
                            StressLevelIntent.AddStressLevelRecord(
                                StressLevelRecord(
                                    stressLevel = addStressLevelState.stressLevel.toDomain(),
                                    stressors = addStressLevelState.stressors.toDomain()
                                )
                            )
                        )
                    } catch (e: Exception) {
                        addStressLevelViewModel.onAction(
                            AddStressLevelIntent.UpdateAddingOtherValueError(e.message.toString())
                        )
                    }
                }
            }

        LaunchedEffect(addStressLevelState.stressors) {
            if (containsOther()) {
                addStressLevelViewModel.onAction(AddStressLevelIntent.UpdateAddingOtherValue(""))
            }
        }

        LaunchedEffect(stressLevelViewModel) {
            scope.launch {
                stressLevelViewModel.sideEffect.collect { event ->
                    when (event) {
                        is StressLevelSideEffect.StressLevelAdded -> {
                            onNavigateBackToStressLevel()
                            stressLevelViewModel.onAction(StressLevelIntent.GetStressLevelRecords)
                        }

                        is StressLevelSideEffect.ShowError -> onError(event.error)
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                addStressLevelViewModel.onAction(AddStressLevelIntent.ResetState)
            }
        }

        return StressStressorsState(
            snackBarState = snackBarState,
            addStressLevelViewModelState = addStressLevelState,
            onEvent = ::onEvent,
            onAddAction = addStressLevelViewModel::onAction,
        )
    }

    @Composable
    override fun UI(state: StressStressorsState) = StressStressorsUI(state)
}
