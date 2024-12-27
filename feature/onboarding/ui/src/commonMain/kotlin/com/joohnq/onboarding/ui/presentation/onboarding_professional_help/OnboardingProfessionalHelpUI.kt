package com.joohnq.onboarding.ui.presentation.onboarding_professional_help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.domain.entity.ProfessionalHelp
import com.joohnq.mood.components.TextRadioButton
import com.joohnq.mood.theme.ComponentColors
import com.joohnq.mood.theme.Dimens
import com.joohnq.mood.theme.Drawables
import com.joohnq.onboarding.ui.presentation.OnboardingBaseComponent
import com.joohnq.onboarding.ui.presentation.onboarding_professional_help.event.OnboardingProfessionalHelpEvent
import com.joohnq.onboarding.ui.presentation.onboarding_professional_help.state.OnboardingProfessionalHelpState
import com.joohnq.onboarding.ui.viewmodel.OnboardingViewModelIntent
import com.joohnq.shared.ui.Res
import com.joohnq.shared.ui.sought_professional_help_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingProfessionalHelpUI(
    state: OnboardingProfessionalHelpState,
) {
    val options = rememberSaveable { ProfessionalHelp.getAll() }

    OnboardingBaseComponent(
        page = 2,
        image = Drawables.Images.OnboardingSoughtProfessionalHelp,
        title = Res.string.sought_professional_help_title,
        isContinueButtonVisible = state.selectedOption != null,
        onGoBack = { state.onEvent(OnboardingProfessionalHelpEvent.OnGoBack) },
        onContinue = { state.onEvent(OnboardingProfessionalHelpEvent.OnNavigateToOnboardingPhysicalSymptomsScreen) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            options.forEach { option ->
                TextRadioButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(option.text),
                    selected = state.selectedOption == option,
                    shape = Dimens.Shape.Circle,
                    colors = ComponentColors.RadioButton.TextRadioButtonColors(),
                    onClick = { state.onAction(OnboardingViewModelIntent.UpdateUserSoughtHelp(option)) }
                )
            }
        }
    }
}