package com.joohnq.moodapp.view.screens.onboarding

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joohnq.moodapp.entities.PhysicalSymptoms
import com.joohnq.moodapp.sharedViewModel
import com.joohnq.moodapp.view.components.IconAndTextRadioButtonHorizontal
import com.joohnq.moodapp.view.components.VerticalSpacer
import com.joohnq.moodapp.view.routes.onNavigateToOnboardingSleepQuality
import com.joohnq.moodapp.view.ui.Colors
import com.joohnq.moodapp.view.ui.ComponentColors
import com.joohnq.moodapp.view.ui.Dimens
import com.joohnq.moodapp.view.ui.TextStyles
import com.joohnq.moodapp.viewmodel.OnboardingViewModel
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.experiencing_physical_symptoms_title
import moodapp.composeapp.generated.resources.select_one_answer
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingPhysicalSymptomsScreenUI(
    snackBarState: SnackbarHostState = remember { SnackbarHostState() },
    selectedOption: PhysicalSymptoms?,
    setSelectedOption: (PhysicalSymptoms) -> Unit = {},
    onGoBack: () -> Unit = {},
    onAction: () -> Unit = {},
) {
    val options: List<PhysicalSymptoms> = remember { PhysicalSymptoms.getAll() }

    OnboardingBaseComponent(
        page = 3,
        snackBarState = snackBarState,
        title = Res.string.experiencing_physical_symptoms_title,
        isContinueButtonVisible = selectedOption != null,
        onGoBack = onGoBack,
        onContinue = onAction,
    ) {
        Text(
            text = stringResource(Res.string.select_one_answer),
            style = TextStyles.ParagraphMd(),
            textAlign = TextAlign.Center,
            color = Colors.Brown100Alpha64
        )
        VerticalSpacer(40.dp)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            options.forEach { option: PhysicalSymptoms ->
                IconAndTextRadioButtonHorizontal(
                    modifier = Modifier.fillMaxWidth(),
                    paddingValues = PaddingValues(all = 16.dp),
                    text = stringResource(option.text),
                    icon = option.icon.copy(modifier = Modifier.size(Dimens.Icon)),
                    selected = selectedOption == option,
                    colors = ComponentColors.RadioButton.TextRadioButtonColors(),
                    shape = Dimens.Shape.Medium,
                    textStyle = TextStyles.TextLgExtraBold(),
                    onClick = { setSelectedOption(option) }
                )
            }
        }
    }
}

@Composable
fun OnboardingPhysicalSymptomsScreen(
    onboardingViewModel: OnboardingViewModel = sharedViewModel(),
    navigation: NavController,
) {
    val onboardingState by onboardingViewModel.onboardingState.collectAsState()
    val snackBarState = remember { SnackbarHostState() }

    OnboardingPhysicalSymptomsScreenUI(
        snackBarState = snackBarState,
        selectedOption = onboardingState.physicalSymptoms,
        setSelectedOption = onboardingViewModel::updateUserPhysicalSymptoms,
        onGoBack = navigation::popBackStack,
        onAction = navigation::onNavigateToOnboardingSleepQuality
    )
}

@Preview
@Composable
fun OnboardingPhysicalSymptomsScreenPreview() {
    OnboardingPhysicalSymptomsScreenUI(
        selectedOption = PhysicalSymptoms.No,
    )
}