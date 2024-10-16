package com.joohnq.moodapp.view.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.moodapp.view.constants.Colors
import com.joohnq.moodapp.model.entities.Mood
import com.joohnq.moodapp.view.BasicScreen
import com.joohnq.moodapp.view.components.ButtonWithArrowRight
import com.joohnq.moodapp.view.components.MoodFace
import com.joohnq.moodapp.view.components.MoodRoulette
import com.joohnq.moodapp.view.components.TextStyles
import com.joohnq.moodapp.viewmodel.MoodsViewModel
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.mood_rate_desc
import moodapp.composeapp.generated.resources.mood_rate_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

class MoodRateScreen : BasicScreen() {
    @Composable
    override fun Init() {
        var selectedMood by rememberSaveable(stateSaver = Mood.getSaver()) { mutableStateOf(Mood.Neutral) }
        val moodsViewModel: MoodsViewModel = koinInject()

        OnboardingBaseComponent(
            page = 1,
            title = Res.string.mood_rate_title,
            isContinueButtonVisible = false,
            onBack = { navigator.pop() },
            onContinue = { },
        ) {
            Text(
                stringResource(
                    Res.string.mood_rate_desc,
                    stringResource(selectedMood.text)
                ),
                style = TextStyles.OnboardingScreenMood()
            )
            Spacer(modifier = Modifier.height(24.dp))
            MoodFace(
                modifier = Modifier.size(120.dp),
                mood = selectedMood,
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(all = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            ButtonWithArrowRight(
                modifier = Modifier.size(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.Brown80,
                    contentColor = Colors.White
                ),
                onClick = {
                    moodsViewModel.setCurrentMood(selectedMood)
                    navigator.push(ProfessionalHelpScreen())
                }
            )
        }

        BoxWithConstraints {
            val carouselOffset = maxHeight - (maxWidth / 2) + 60.dp

            Box(
                modifier = Modifier
                    .size(maxWidth)
                    .offset(y = carouselOffset),
                contentAlignment = Alignment.TopCenter
            ) {
                MoodRoulette { selectedMood = it }
            }
        }
    }
}
