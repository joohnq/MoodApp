package com.joohnq.moodapp.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.moodapp.Colors
import com.joohnq.moodapp.view.components.ButtonWithArrowOpen
import com.joohnq.moodapp.view.components.CustomTextStyle
import com.joohnq.moodapp.view.components.TextWithBackground
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.assessments
import moodapp.composeapp.generated.resources.page_of
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingTopBar(page: Int){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ButtonWithArrowOpen {}
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                stringResource(Res.string.assessments),
                style = CustomTextStyle.TextStyleOnboardingScreenSession()
            )
        }
        TextWithBackground(
            stringResource(Res.string.page_of, page, 7),
            borderColor = Colors.Transparent,
            backgroundColor = Colors.Brown20,
            textColor = Colors.Brown60
        )
    }
}