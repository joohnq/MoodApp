package com.joohnq.shared_resources.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.joohnq.shared_resources.Res
import com.joohnq.shared_resources.cancel
import com.joohnq.shared_resources.ok
import com.joohnq.shared_resources.theme.Colors
import com.joohnq.shared_resources.theme.TextStyles
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimePickerDialog(
    title: StringResource,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            color = Colors.White,
            modifier =
                Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = Colors.White
                    ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = stringResource(title),
                    style = TextStyles.TextSmSemiBold().copy(color = Colors.Brown80)
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = stringResource(Res.string.cancel),
                            style = TextStyles.TextMdSemiBold().copy(color = Colors.Brown80)
                        )
                    }
                    TextButton(onClick = onConfirm) {
                        Text(
                            text = stringResource(Res.string.ok),
                            style = TextStyles.TextMdSemiBold().copy(color = Colors.Brown80)
                        )
                    }
                }
            }
        }
    }
}