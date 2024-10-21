package com.joohnq.moodapp.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.moodapp.model.entities.ProfessionalHelp
import com.joohnq.moodapp.view.components.ProfessionalHelpRadioButton
import com.joohnq.moodapp.view.constants.Drawables
import com.joohnq.moodapp.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.sought_professional_help_title
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object ProfessionalHelpScreenObject

@Composable
fun ProfessionalHelpScreen(
    onGoBack: () -> Unit,
    onNavigateToPhysicalSymptoms: () -> Unit
) {
    var isContinueButtonVisible by remember { mutableStateOf(false) }
    val userViewModel: UserViewModel = koinViewModel()
    var selectedOption by rememberSaveable(stateSaver = ProfessionalHelp.getSaver()) {
        mutableStateOf(null)
    }
    val options = remember { ProfessionalHelp.getAll() }
    val scope = rememberCoroutineScope()
    val ioDispatcher: CoroutineDispatcher = koinInject()

    LaunchedEffect(selectedOption) {
        isContinueButtonVisible = selectedOption != null
    }

    OnboardingBaseComponent(
        page = 2,
        image = Drawables.Images.OnboardingSoughtProfessionalHelp,
        title = Res.string.sought_professional_help_title,
        isContinueButtonVisible = isContinueButtonVisible,
        onBack = onGoBack,
        onContinue = { onSomethingWentWrong ->
            scope.launch(ioDispatcher) {
                val res = userViewModel.setUserSoughtHelp(selectedOption?.value ?: false)
                if (!res) {
                    onSomethingWentWrong()
                    return@launch
                }

                onNavigateToPhysicalSymptoms()
            }
        },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            options.forEach { option ->
                ProfessionalHelpRadioButton(
                    modifier = Modifier.weight(1f),
                    option = option,
                    selected = selectedOption == option,
                ) { selectedOption = option }
            }
        }
    }
}