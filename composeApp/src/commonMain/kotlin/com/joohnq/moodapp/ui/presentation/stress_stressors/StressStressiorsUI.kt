package com.joohnq.moodapp.ui.presentation.stress_stressors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.joohnq.moodapp.domain.Stressor
import com.joohnq.moodapp.ui.components.ContinueButton
import com.joohnq.moodapp.ui.components.StressStressorCircle
import com.joohnq.moodapp.ui.components.TextFieldWithLabelAndDoubleBorder
import com.joohnq.moodapp.ui.components.TopBar
import com.joohnq.moodapp.ui.components.VerticalSpacer
import com.joohnq.moodapp.ui.presentation.add_stress_level.AddStressLevelIntent
import com.joohnq.moodapp.ui.presentation.stress_stressors.event.StressStressorsEvent
import com.joohnq.moodapp.ui.presentation.stress_stressors.state.StressStressorsState
import com.joohnq.moodapp.ui.theme.Colors
import com.joohnq.moodapp.ui.theme.ComponentColors
import com.joohnq.moodapp.ui.theme.PaddingModifier.Companion.paddingHorizontalMedium
import com.joohnq.moodapp.ui.theme.TextStyles
import com.joohnq.moodapp.util.constants.TestConstants
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.add_stress_level
import moodapp.composeapp.generated.resources.enter_your_stressor
import moodapp.composeapp.generated.resources.other
import moodapp.composeapp.generated.resources.select_stressors
import org.jetbrains.compose.resources.stringResource

@Composable
fun StressStressorsUI(
    state: StressStressorsState,
) {
    val stressors = remember { Stressor.getAll() }
    val canContinue by derivedStateOf { state.addStressLevelViewModelState.stressors.isNotEmpty() }
    val containOtherInStressors by derivedStateOf { state.addStressLevelViewModelState.stressors.any { it::class == Stressor.Other::class } }

    Scaffold(
        snackbarHost = { SnackbarHost(state.snackBarState) },
        containerColor = Colors.Brown10,
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .paddingHorizontalMedium()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                text = Res.string.add_stress_level,
                onGoBack = { state.onEvent(StressStressorsEvent.OnGoBack) }
            )
            VerticalSpacer(60.dp)
            Text(
                text = stringResource(Res.string.select_stressors),
                style = TextStyles.HeadingSmExtraBold(),
                color = Colors.Brown80,
                textAlign = TextAlign.Center
            )
            VerticalSpacer(24.dp)
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 100.dp),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                content = {
                    items(stressors) { stressor ->
                        StressStressorCircle(
                            stressStressor = stressor,
                            selected = state.addStressLevelViewModelState.stressors.contains(
                                stressor
                            ),
                            onClick = {
                                state.onAddAction(
                                    AddStressLevelIntent.UpdateAddingStressors(
                                        stressor
                                    )
                                )
                            }
                        )
                    }
                },
            )
            if (containOtherInStressors)
                TextFieldWithLabelAndDoubleBorder(
                    label = Res.string.other,
                    placeholder = Res.string.enter_your_stressor,
                    text = state.addStressLevelViewModelState.otherValue,
                    errorText = state.addStressLevelViewModelState.otherValueError,
                    focusedBorderColor = Colors.Green50Alpha25,
                    colors = ComponentColors.TextField.MainTextFieldColors(),
                    onValueChange = {
                        state.onAddAction(
                            AddStressLevelIntent.UpdateAddingOtherValue(it)
                        )
                    },
                )
            VerticalSpacer(24.dp)
            if (canContinue)
                ContinueButton(
                    modifier = Modifier.fillMaxWidth()
                        .testTag(TestConstants.CONTINUE_BUTTON),
                    onClick = { state.onEvent(StressStressorsEvent.OnContinue) }
                )
        }
    }
}