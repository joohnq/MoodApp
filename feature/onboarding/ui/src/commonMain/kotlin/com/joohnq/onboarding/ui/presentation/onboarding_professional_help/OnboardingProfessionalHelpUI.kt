package com.joohnq.onboarding.ui.presentation.onboarding_professional_help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.onboarding.ui.event.OnboardingEvent
import com.joohnq.onboarding.ui.presentation.OnboardingBaseComponent
import com.joohnq.onboarding.ui.presentation.onboarding_professional_help.state.OnboardingProfessionalHelpState
import com.joohnq.onboarding.ui.viewmodel.OnboardingViewModelIntent
import com.joohnq.shared_resources.Res
import com.joohnq.shared_resources.components.TextRadioButton
import com.joohnq.shared_resources.sought_professional_help_title
import com.joohnq.shared_resources.theme.ComponentColors
import com.joohnq.shared_resources.theme.Dimens
import com.joohnq.shared_resources.theme.Drawables
import com.joohnq.user.ui.mapper.getAllProfessionalHelpResource
import com.joohnq.user.ui.resource.ProfessionalHelpResource

@Composable
fun OnboardingProfessionalHelpUI(
    state: OnboardingProfessionalHelpState,
) {
    val options = rememberSaveable { getAllProfessionalHelpResource() }

    OnboardingBaseComponent(
        page = 2,
        image = Drawables.Images.OnboardingSoughtProfessionalHelp,
        title = Res.string.sought_professional_help_title,
        isContinueButtonVisible = state.selectedOption != null,
        onGoBack = { state.onEvent(OnboardingEvent.OnGoBack) },
        onContinue = { state.onEvent(OnboardingEvent.OnNavigateToNext) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            options.forEach { option: ProfessionalHelpResource ->
                TextRadioButton(
                    modifier = Modifier.weight(1f),
                    text = option.text,
                    selected = state.selectedOption == option,
                    shape = Dimens.Shape.Circle,
                    colors = ComponentColors.RadioButton.TextRadioButtonColors(),
                    onClick = { state.onAction(OnboardingViewModelIntent.UpdateUserSoughtHelp(option)) }
                )
            }
        }
    }
}