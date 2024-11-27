package com.joohnq.moodapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.moodapp.domain.Mood
import com.joohnq.moodapp.domain.SleepQuality
import com.joohnq.moodapp.domain.StressLevel
import com.joohnq.moodapp.ui.presentation.home.event.HomeEvent
import com.joohnq.moodapp.ui.presentation.journaling.event.JournalingEvent
import com.joohnq.moodapp.ui.theme.Colors
import com.joohnq.moodapp.ui.theme.Dimens
import com.joohnq.moodapp.ui.theme.Drawables
import com.joohnq.moodapp.ui.theme.TextStyles
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.mindful_journal
import moodapp.composeapp.generated.resources.mood_tracker
import moodapp.composeapp.generated.resources.stress_level

@Composable fun MindfulTracker(
    sleepQuality: SleepQuality,
    stressLevel: StressLevel,
    moodTracker: List<Mood>,
    onAction: (HomeEvent) -> Unit
) {
    MindfulTrackerCardRow(icon = Drawables.Icons.HospitalBed,
        color = sleepQuality.palette.color,
        backgroundColor = sleepQuality.palette.backgroundColor2,
        title = sleepQuality.firstText,
        subtitle = sleepQuality.secondText,
        content = {
            CircularProgressWithText(
                modifier = Modifier.size(56.dp),
                color = sleepQuality.palette.color,
                backgroundColor = sleepQuality.palette.backgroundColor2,
                progress = { sleepQuality.level * 0.2f },
                text = sleepQuality.level.toString(),
                textStyle = TextStyles.TextXsExtraBold(),
                textColor = Colors.Brown80
            )
        },
        onClick = { onAction(HomeEvent.OnNavigateToSleepQuality) }
    )
    VerticalSpacer(16.dp)
    MindfulTrackerCardRow(icon = Drawables.Icons.DocumentHealth,
        color = Colors.Orange40,
        backgroundColor = Colors.Orange10,
        title = Res.string.mindful_journal,
        subtitle = Res.string.mindful_journal,
        content = { modifier ->
            LazyVerticalGrid(
                modifier = modifier.wrapContentSize(unbounded = true).size(60.dp),
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(16, key = { it }) { i ->
                    val background = when (i) {
                        5, 6, 7, 8, 9 -> Colors.Orange40
                        2, 3, 10, 11 -> Colors.Orange20
                        else -> Colors.Orange10
                    }

                    Box(
                        modifier = Modifier.size(12.dp).background(
                            color = background, shape = Dimens.Shape.ExtraExtraSmall
                        ),
                    )
                }
            }
        },
        onClick = { onAction(HomeEvent.OnNavigateToMindfulJournal) }
    )
    VerticalSpacer(16.dp)
    MindfulTrackerCardColumn(icon = Drawables.Icons.Head,
        color = stressLevel.palette.color,
        backgroundColor = stressLevel.palette.backgroundColor,
        title = Res.string.stress_level,
        subtitle = stressLevel.subtitle,
        content = {
            StressLevelIndicator(stressLevel)
        },
        onClick = { onAction(HomeEvent.OnNavigateToStressLevel) }
    )
    VerticalSpacer(16.dp)
    MindfulTrackerCardColumn(icon = Drawables.Icons.HappyFace,
        color = Colors.Brown80,
        backgroundColor = Colors.Brown10,
        title = Res.string.mood_tracker,
        content = {
            MoodTrackerRow(moodTracker)
        },
        onClick = { onAction(HomeEvent.OnNavigateToMood) }
    )
    VerticalSpacer(30.dp)
}