package com.joohnq.onboarding.ui.presentation.onboarding_sleep_quality

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.joohnq.mood.CustomScreen
import com.joohnq.mood.sharedViewModel
import com.joohnq.onboarding.ui.presentation.onboarding_medications_supplements.OnboardingMedicationsSupplementsScreen
import com.joohnq.onboarding.ui.presentation.onboarding_sleep_quality.event.OnboardingSleepQualityEvent
import com.joohnq.onboarding.ui.presentation.onboarding_sleep_quality.state.OnboardingSleepQualityState
import com.joohnq.onboarding.ui.viewmodel.OnboardingViewModel

class OnboardingSleepQualityScreen : CustomScreen<OnboardingSleepQualityState>() {
    @Composable
    override fun Screen(): OnboardingSleepQualityState {
        val onboardingViewModel: OnboardingViewModel = sharedViewModel()
        val onboardingState by onboardingViewModel.state.collectAsState()

        fun onEvent(event: OnboardingSleepQualityEvent) =
            when (event) {
                OnboardingSleepQualityEvent.OnNavigateToOnboardingMedicationSupplementsScreen ->
                    onNavigate(OnboardingMedicationsSupplementsScreen())

                OnboardingSleepQualityEvent.OnGoBack -> onGoBack()
            }

        return OnboardingSleepQualityState(
            sliderValue = onboardingState.sliderValue,
            selectedSleepQuality = onboardingState.sleepQuality,
            onAction = onboardingViewModel::onAction,
            onEvent = ::onEvent
        )
    }

    @Composable
    override fun UI(state: OnboardingSleepQualityState) = OnboardingSleepQualityUI(state)

    object OnboardingSleepQualityTestTag {
        const val SLEEP_QUALITY_SLIDER = "SLEEP_QUALITY_SLIDER"
    }
}