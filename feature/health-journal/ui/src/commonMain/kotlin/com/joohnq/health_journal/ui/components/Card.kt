package com.joohnq.health_journal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joohnq.health_journal.domain.entity.HealthJournalRecord
import com.joohnq.mood.components.TextEllipsis
import com.joohnq.mood.components.TextWithBackground
import com.joohnq.mood.theme.Colors
import com.joohnq.mood.theme.ComponentColors
import com.joohnq.mood.theme.Dimens
import com.joohnq.mood.theme.TextStyles
import com.joohnq.mood.ui.MoodResource.Companion.toResource
import com.joohnq.mood.ui.components.MoodFace
import com.joohnq.shared.ui.Res
import com.joohnq.shared.ui.mood_show
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HealthJournalCard(journal: HealthJournalRecord, onClick: () -> Unit) {
    val mood = journal.mood
    val resource = mood.toResource()
    val palette = resource.palette
    Card(
        modifier = Modifier.width(220.dp).height(250.dp),
        colors = ComponentColors.Card.MainCardColors(),
        shape = Dimens.Shape.Large,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.size(48.dp).background(
                        color = palette.color,
                        shape = Dimens.Shape.Small
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    MoodFace(
                        mood = mood.toResource(),
                        modifier = Modifier.size(24.dp),
                        backgroundColor = Colors.White,
                        color = palette.color
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextWithBackground(
                    text = stringResource(
                        Res.string.mood_show,
                        stringResource(resource.text)
                    ).uppercase(),
                    backgroundColor = palette.backgroundColor,
                    textColor = palette.color
                )
                TextEllipsis(
                    text = journal.title,
                    style = TextStyles.TextLgBold(),
                    color = Colors.Brown80
                )
                TextEllipsis(
                    text = journal.description,
                    style = TextStyles.TextSmSemiBold(),
                    color = Colors.Brown100Alpha64
                )
            }
        }
    }
}

@Composable
fun HealthJournalStatsCard(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    title: String,
    color: Color,
    backgroundColor: Color,
    desc: String
) {
    Card(
        modifier = modifier,
        colors = ComponentColors.Card.MainCardColors(),
        shape = Dimens.Shape.Large
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier.size(48.dp)
                    .background(color = backgroundColor, shape = Dimens.Shape.Small),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = desc,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyles.TextMdExtraBold(),
                    color = Colors.Brown80
                )
                Text(
                    text = desc,
                    style = TextStyles.TextSmSemiBold(),
                    color = Colors.Brown100Alpha64
                )
            }
        }
    }
}
