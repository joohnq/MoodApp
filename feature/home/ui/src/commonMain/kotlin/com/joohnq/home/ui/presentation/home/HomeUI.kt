package com.joohnq.home.ui.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.joohnq.core.ui.mapper.getValueOrNull
import com.joohnq.core.ui.mapper.onFold
import com.joohnq.home.ui.components.HomeTopBar
import com.joohnq.home.ui.components.MentalHealthMetrics
import com.joohnq.home.ui.components.MindfulTracker
import com.joohnq.home.ui.presentation.home.state.HomeState
import com.joohnq.mood.ui.mapper.toResource
import com.joohnq.moodapp.presentation.loading.LoadingUI
import com.joohnq.shared_resources.Res
import com.joohnq.shared_resources.components.Title
import com.joohnq.shared_resources.mental_health_metrics
import com.joohnq.shared_resources.mindful_tracker
import com.joohnq.shared_resources.theme.Colors
import com.joohnq.shared_resources.theme.Drawables
import com.joohnq.sleep_quality.ui.mapper.toResource
import com.joohnq.stress_level.ui.mapper.toResource

@Composable
fun HomeUI(
    state: HomeState,
) {
    onFold(
        state.statsRecord,
        state.user,
        state.stressLevel,
        state.healthJournal,
        state.sleepQuality,
        onLoading = { LoadingUI() },
        onAllSuccess = {
            val user = state.user.getValueOrNull()!!

            Scaffold(
                containerColor = Colors.Brown10,
                modifier = Modifier.fillMaxSize()
            ) { pad ->
                val padding = PaddingValues(
                    top = pad.calculateTopPadding(),
                    bottom = pad.calculateBottomPadding() + 80.dp,
                    start = pad.calculateStartPadding(LayoutDirection.Ltr),
                    end = pad.calculateEndPadding(LayoutDirection.Rtl)
                )
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                        .padding(bottom = padding.calculateBottomPadding())
                ) {
                    HomeTopBar(
                        modifier = Modifier.padding(
                            top = padding.calculateTopPadding(),
                        ),
                        userName = user.name,
                        images = Drawables.Avatar.avatars,
                        image = user.image,
                        imageType = user.imageType,
                        date = state.today
                    )
                    Title(Res.string.mental_health_metrics)
                    MentalHealthMetrics(
                        freudScore = state.freudScore,
                        statsRecord = state.statsRecord.getValueOrNull().first(),
                        healthJournal = state.healthJournal.getValueOrNull(),
                        onEvent = state.onEvent
                    )
                    Title(Res.string.mindful_tracker)
                    MindfulTracker(
                        sleepQuality = state.sleepQuality.getValueOrNull()
                            .first().sleepQuality.toResource(),
                        stressLevel = state.stressLevel.getValueOrNull()
                            .first().stressLevel.toResource(),
                        moodTracker = state.statsRecord.getValueOrNull().take(3).reversed()
                            .map { statsRecord -> statsRecord.mood.toResource() },
                        onAction = state.onEvent
                    )
                }
            }
        }
    )
}