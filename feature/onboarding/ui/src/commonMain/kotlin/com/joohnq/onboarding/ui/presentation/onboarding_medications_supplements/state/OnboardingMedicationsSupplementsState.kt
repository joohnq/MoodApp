package com.joohnq.onboarding.ui.presentation.onboarding_medications_supplements.state

import com.joohnq.domain.entity.MedicationsSupplements
import com.joohnq.onboarding.ui.event.OnboardingEvent
import com.joohnq.onboarding.ui.presentation.onboarding_medications_supplements.event.OnboardingMedicationsSupplementsEvent
import com.joohnq.onboarding.ui.viewmodel.OnboardingViewModelIntent

data class OnboardingMedicationsSupplementsState(
    val selectedOption: MedicationsSupplements?,
    val onEvent: (OnboardingEvent) -> Unit = {},
    val onAction: (OnboardingViewModelIntent) -> Unit = {},
)