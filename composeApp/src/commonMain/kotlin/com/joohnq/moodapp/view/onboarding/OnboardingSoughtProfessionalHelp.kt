package com.joohnq.moodapp.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.joohnq.moodapp.Colors
import com.joohnq.moodapp.Drawables
import com.joohnq.moodapp.view.components.ButtonWithArrowRight
import com.joohnq.moodapp.view.components.CustomTextStyle
import com.joohnq.moodapp.view.components.TextRadioButton
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.continue_word
import moodapp.composeapp.generated.resources.indeterminate
import moodapp.composeapp.generated.resources.no
import moodapp.composeapp.generated.resources.sought_professional_help_title
import moodapp.composeapp.generated.resources.yes
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

sealed class OnboardingSoughtProfessionalHelpOptions(open val text: StringResource) {
    data object Yes : OnboardingSoughtProfessionalHelpOptions(Res.string.yes)
    data object No : OnboardingSoughtProfessionalHelpOptions(Res.string.no)
    data object Indeterminate :
        OnboardingSoughtProfessionalHelpOptions(Res.string.indeterminate)
}

class OnboardingSoughtProfessionalHelp : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var isContinueButtonVisible by remember { mutableStateOf(false) }
        var selectedOption by remember {
            mutableStateOf<OnboardingSoughtProfessionalHelpOptions>(
                OnboardingSoughtProfessionalHelpOptions.Indeterminate
            )
        }
        val options = remember {
            listOf(
                OnboardingSoughtProfessionalHelpOptions.Yes,
                OnboardingSoughtProfessionalHelpOptions.No
            )
        }

        LaunchedEffect(selectedOption) {
            isContinueButtonVisible =
                selectedOption != OnboardingSoughtProfessionalHelpOptions.Indeterminate
        }
        Scaffold(
            containerColor = Colors.Brown10,
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            Column(
                modifier = Modifier.padding(padding).padding(horizontal = 16.dp).fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                OnboardingTopBar(2)
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    painter = painterResource(Drawables.Images.OnboardingSoughtProfessionalHelp),
                    contentDescription = null,
                    modifier = Modifier.widthIn(max = 300.dp).fillMaxWidth().aspectRatio(1f)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    stringResource(Res.string.sought_professional_help_title),
                    style = CustomTextStyle.TextStyleOnboardingScreenTitle()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    options.forEach { option ->
                        TextRadioButton(
                            modifier = Modifier.weight(1f),
                            text = stringResource(option.text),
                            selected = selectedOption == option,
                            selectedBackground = Colors.Green50,
                            selectedContent = Colors.White,
                            unSelectedContent = Colors.Brown80,
                            unSelectedBackground = Colors.White,
                            shape = CircleShape,
                            onClick = { selectedOption = option }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (isContinueButtonVisible)
                    ButtonWithArrowRight(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.continue_word),
                        onClick = { navigator.push(OnboardingExperiencingPhysicalSymptoms()) })
            }
        }
    }
}