package com.joohnq.sleep_quality.ui.presentation.add_sleep_quality

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.joohnq.domain.entity.Mood
import com.joohnq.mood.components.AddSleepQualityTimePicker
import com.joohnq.mood.components.ContinueButton
import com.joohnq.mood.components.HorizontalSpacer
import com.joohnq.mood.components.MoodFace
import com.joohnq.mood.components.TextRadioButton
import com.joohnq.mood.components.TimePickerCard
import com.joohnq.mood.components.TimePickerDialog
import com.joohnq.mood.components.Title
import com.joohnq.mood.components.TopBar
import com.joohnq.mood.components.VerticalSpacer
import com.joohnq.mood.theme.Colors
import com.joohnq.mood.theme.ComponentColors
import com.joohnq.mood.theme.Dimens
import com.joohnq.mood.theme.PaddingModifier.Companion.paddingHorizontalMedium
import com.joohnq.mood.theme.TextStyles
import com.joohnq.mood.util.helper.DatetimeManager
import com.joohnq.shared.ui.Res
import com.joohnq.shared.ui.end_sleeping_time
import com.joohnq.shared.ui.mood
import com.joohnq.shared.ui.new_sleep_quality
import com.joohnq.shared.ui.sleeping_influences
import com.joohnq.shared.ui.start_sleeping_time
import com.joohnq.sleep_quality.domain.entity.SleepInfluences
import com.joohnq.sleep_quality.ui.presentation.add_sleep_quality.event.AddSleepQualityEvent
import com.joohnq.sleep_quality.ui.presentation.add_sleep_quality.state.AddSleepQualityState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSleepQualityUI(
    state: AddSleepQualityState
) {
    val moods = remember { Mood.getAll() }
    val sleepInfluences = remember { SleepInfluences.getAll() }
    val startTimePickerState = rememberTimePickerState(
        initialHour = state.addSleepQualityViewModelState.startHour,
        initialMinute = state.addSleepQualityViewModelState.startMinute,
        is24Hour = true,
    )
    val endTimePickerState = rememberTimePickerState(
        initialHour = state.addSleepQualityViewModelState.endHour,
        initialMinute = state.addSleepQualityViewModelState.endMinute,
        is24Hour = true,
    )

    if (state.addSleepQualityViewModelState.showStartTimePickerDialog) {
        TimePickerDialog(
            title = Res.string.start_sleeping_time,
            onDismiss = {
                state.onAddAction(
                    AddSleepQualityIntent.UpdateShowStartTimePickerDialog(
                        false
                    )
                )
            },
            onConfirm = {
                state.onAddAction(AddSleepQualityIntent.UpdateShowStartTimePickerDialog(false))
                state.onAddAction(
                    AddSleepQualityIntent.UpdateStartTime(
                        startTimePickerState.hour,
                        startTimePickerState.minute
                    )
                )
            },
        ) {
            AddSleepQualityTimePicker(startTimePickerState)
        }
    }

    if (state.addSleepQualityViewModelState.showEndTimePickerDialog) {
        TimePickerDialog(
            title = Res.string.end_sleeping_time,
            onDismiss = {
                state.onAddAction(AddSleepQualityIntent.UpdateShowStartTimePickerDialog(false))
            },
            onConfirm = {
                state.onAddAction(AddSleepQualityIntent.UpdateShowEndTimePickerDialog(false))
                state.onAddAction(
                    AddSleepQualityIntent.UpdateEndTime(
                        endTimePickerState.hour,
                        endTimePickerState.minute
                    )
                )
            },
        ) {
            AddSleepQualityTimePicker(endTimePickerState)
        }
    }

    Scaffold(
        containerColor = Colors.Brown10,
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(state.snackBarState) }
    ) { padding ->
        Column(
            Modifier.fillMaxSize()
                .padding(padding),
        ) {
            Box(modifier = Modifier.paddingHorizontalMedium()) {
                TopBar(onGoBack = { state.onEvent(AddSleepQualityEvent.OnGoBack) })
            }
            VerticalSpacer(40.dp)
            Text(
                text = stringResource(Res.string.new_sleep_quality),
                style = TextStyles.HeadingSmExtraBold(),
                color = Colors.Brown80,
                textAlign = TextAlign.Center,
                modifier = Modifier.paddingHorizontalMedium()
            )
            VerticalSpacer(32.dp)
            Row(
                modifier = Modifier.fillMaxWidth().paddingHorizontalMedium(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimePickerCard(
                    modifier = Modifier.weight(1f),
                    title = Res.string.start_sleeping_time,
                    hour = DatetimeManager.formatInt(startTimePickerState.hour),
                    minutes = DatetimeManager.formatInt(startTimePickerState.minute),
                    isAfternoon = startTimePickerState.isAfternoon,
                    onClick = {
                        state.onAddAction(
                            AddSleepQualityIntent.UpdateShowStartTimePickerDialog(true)
                        )
                    }
                )
                HorizontalSpacer(20.dp)
                TimePickerCard(
                    modifier = Modifier.weight(1f),
                    title = Res.string.end_sleeping_time,
                    hour = DatetimeManager.formatInt(endTimePickerState.hour),
                    minutes = DatetimeManager.formatInt(endTimePickerState.minute),
                    isAfternoon = endTimePickerState.isAfternoon,
                    onClick = {
                        state.onAddAction(AddSleepQualityIntent.UpdateShowEndTimePickerDialog(true))
                    }
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Title(text = Res.string.mood)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(moods) {
                        MoodFace(
                            modifier = Modifier.size(32.dp),
                            mood = it,
                            backgroundColor = if (state.addSleepQualityViewModelState.mood == it) it.palette.faceBackgroundColor else Colors.Gray30,
                            color = if (state.addSleepQualityViewModelState.mood == it) it.palette.faceColor else Colors.Gray60,
                            onClick = { state.onAddAction(AddSleepQualityIntent.UpdateMood(it)) }
                        )
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Title(text = Res.string.sleeping_influences)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(sleepInfluences) {
                        TextRadioButton(
                            text = stringResource(it.title),
                            selected = state.addSleepQualityViewModelState.selectedSleepInfluences.contains(
                                it
                            ),
                            colors = ComponentColors.RadioButton.TextRadioButtonColors(),
                            shape = Dimens.Shape.Circle,
                            onClick = {
                                state.onAddAction(
                                    AddSleepQualityIntent.UpdateSelectedSleepInfluence(
                                        it
                                    )
                                )
                            }
                        )
                    }
                }
            }
            VerticalSpacer(48.dp)
            if (state.addSleepQualityViewModelState.mood != null)
                Box(modifier = Modifier.paddingHorizontalMedium()) {
                    ContinueButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { state.onEvent(AddSleepQualityEvent.OnAdd) }
                    )
                }
        }
    }
}