package com.joohnq.welcome.ui.screens

import androidx.compose.runtime.Composable
import com.joohnq.shared_resources.theme.Colors
import com.joohnq.shared_resources.theme.Drawables
import com.joohnq.shared_resources.Res
import com.joohnq.shared_resources.health_state_first_title
import com.joohnq.shared_resources.health_state_second_title
import com.joohnq.shared_resources.health_state_span
import com.joohnq.shared_resources.one
import org.jetbrains.compose.resources.stringResource

@Composable
fun HealthStateScreen(onNext: () -> Unit) {
    MockScreen(
        image = Drawables.Images.WelcomeHealthStateImage,
        step = stringResource(Res.string.one),
        index = 1,
        backgroundColor = Colors.Green30,
        firstTitle = stringResource(Res.string.health_state_first_title),
        secondTitle = stringResource(Res.string.health_state_second_title),
        span = stringResource(Res.string.health_state_span),
        spanColor = Colors.Green50,
        onNext = onNext
    )
}