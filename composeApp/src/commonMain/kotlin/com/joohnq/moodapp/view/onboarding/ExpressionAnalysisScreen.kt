package com.joohnq.moodapp.view.onboarding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.joohnq.moodapp.constants.TestConstants
import com.joohnq.moodapp.view.components.ExpressionAnalysisTextField
import com.joohnq.moodapp.view.components.TextStyles
import com.joohnq.moodapp.view.routes.onNavigateToGetUserNameScreen
import com.joohnq.moodapp.viewmodel.MoodsViewModel
import com.joohnq.moodapp.viewmodel.UserPreferenceViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.expression_analysis_desc
import moodapp.composeapp.generated.resources.expression_analysis_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExpressionAnalysisScreen(
    navigation: NavController = rememberNavController(),
    moodsViewModel: MoodsViewModel = koinViewModel(),
    userPreferencesViewModel: UserPreferenceViewModel = koinViewModel()
) {
    var desc by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val ioDispatcher: CoroutineDispatcher = koinInject()

    OnboardingBaseComponent(
        page = 7,
        title = Res.string.expression_analysis_title,
        onBack = navigation::popBackStack,
        isContinueButtonVisible = desc.isNotEmpty(),
        onContinue = { onSomethingWentWrong ->
            moodsViewModel.setCurrentMoodDescription(desc)
            scope.launch(ioDispatcher) {
                val res = moodsViewModel.insertCurrentMood()
                if (!res) onSomethingWentWrong()
                moodsViewModel.resetCurrentMood()
                val res2 = userPreferencesViewModel.setSkipOnboardingScreen()
                if (!res2) onSomethingWentWrong()
                withContext(Dispatchers.Main) {
                    navigation.onNavigateToGetUserNameScreen()
                }
            }
        },
    ) {
        Text(
            stringResource(Res.string.expression_analysis_desc),
            style = TextStyles.ExpressionAnalysisDesc()
        )
        ExpressionAnalysisTextField(modifier = Modifier.testTag(TestConstants.TEXT_INPUT),desc) { desc = it }
    }
}
