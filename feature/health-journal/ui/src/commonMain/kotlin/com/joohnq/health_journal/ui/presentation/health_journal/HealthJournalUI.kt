package com.joohnq.health_journal.ui.presentation.health_journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohnq.health_journal.ui.presentation.health_journal.event.HealthJournalEvent
import com.joohnq.health_journal.ui.presentation.health_journal.state.HealthJournalState
import com.joohnq.mood.components.VerticalSpacer
import com.joohnq.mood.state.UiState.Companion.foldComposable
import com.joohnq.mood.util.helper.DatetimeManager
import com.joohnq.mood.ui.components.HealthJournalComponentColorful
import com.joohnq.mood.ui.components.SharedPanelComponent
import com.joohnq.mood.ui.presentation.loading.LoadingUI
import com.joohnq.mood.ui.theme.Colors
import com.joohnq.mood.ui.theme.Drawables
import com.joohnq.mood.ui.theme.PaddingModifier.Companion.paddingHorizontalMedium
import com.joohnq.mood.ui.theme.TextStyles
import moodapp.composeapp.generated.resources.Res
import moodapp.composeapp.generated.resources.health_journal
import moodapp.composeapp.generated.resources.journal_history
import moodapp.composeapp.generated.resources.journals_this_year
import org.jetbrains.compose.resources.stringResource

@Composable
fun HealthJournalUI(
    state: HealthJournalState
) {
    state.healthJournal.foldComposable(
        onLoading = { LoadingUI() },
        onSuccess = { healthJournals ->
            val dayPerYear =
                remember { DatetimeManager.getHealthJournalsInYear(healthJournals) }
            SharedPanelComponent(
                isDark = false,
                onGoBack = { state.onEvent(HealthJournalEvent.OnGoBack) },
                backgroundColor = Colors.Brown60,
                backgroundImage = Drawables.Images.HealthJournalBackground,
                panelTitle = Res.string.health_journal,
                bodyTitle = Res.string.journal_history,
                color = Colors.Brown50,
                onAdd = { state.onEvent(HealthJournalEvent.OnNavigateToAddHealthJournalScreen) },
                panelContent = {
                    Column(
                        modifier = Modifier.paddingHorizontalMedium()
                            .padding(top = it.calculateTopPadding()).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = dayPerYear,
                            style = TextStyles.Heading2xlExtraBold(),
                            color = Colors.White
                        )
                        VerticalSpacer(10.dp)
                        Text(
                            text = stringResource(Res.string.journals_this_year),
                            style = TextStyles.TextXlSemiBold(),
                            color = Colors.White
                        )
                    }
                },
                content = {
                    item {
                        VerticalSpacer(10.dp)
                        HealthJournalComponentColorful(
                            healthJournals = healthJournals,
                            onClick = { state.onEvent(HealthJournalEvent.OnClick(it)) }
                        )
                    }
                }
            )
        }
    )
}