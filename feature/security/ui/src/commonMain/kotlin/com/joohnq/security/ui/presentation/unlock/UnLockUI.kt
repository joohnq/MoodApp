package com.joohnq.security.ui.presentation.unlock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.joohnq.security.ui.components.PINCode
import com.joohnq.security.ui.presentation.pin.viewmodel.PINIntent
import com.joohnq.security.ui.presentation.pin.viewmodel.PINState
import com.joohnq.security.ui.presentation.unlock.event.UnLockEvent
import com.joohnq.shared_resources.Res
import com.joohnq.shared_resources.components.ContinueButton
import com.joohnq.shared_resources.components.ErrorInfo
import com.joohnq.shared_resources.components.TopBalloon
import com.joohnq.shared_resources.components.VerticalSpacer
import com.joohnq.shared_resources.theme.Colors
import com.joohnq.shared_resources.theme.PaddingModifier.Companion.paddingHorizontalMedium
import com.joohnq.shared_resources.theme.TextStyles
import com.joohnq.shared_resources.this_password_is_the_same_validation_method
import com.joohnq.shared_resources.type_your_four_digit_pin_to_unlock_the_app
import com.joohnq.shared_resources.use_device_password
import com.joohnq.shared_resources.use_your_authentication_to_securely
import com.joohnq.shared_resources.welcome_back
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnLockUI(
    sheetState: SheetState,
    isError: Exception?,
    showBottomSheet: Boolean,
    pinState: PINState,
    focusRequesters: List<FocusRequester>,
    focusManager: FocusManager,
    keyboardManager: SoftwareKeyboardController?,
    onAction: (PINIntent) -> Unit,
    onEvent: (UnLockEvent) -> Unit,
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = Colors.Brown10,
            onDismissRequest = {
                onEvent(UnLockEvent.UpdateShowBottomSheet(false))
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.paddingHorizontalMedium().padding(bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer(10.dp)
                Text(
                    text = stringResource(Res.string.type_your_four_digit_pin_to_unlock_the_app),
                    style = TextStyles.ParagraphLg(),
                    color = Colors.Brown100Alpha64,
                    textAlign = TextAlign.Center
                )
                VerticalSpacer(10.dp)
                PINCode(
                    code = pinState.code,
                    focusedIndex = pinState.focusedIndex,
                    onNumberChanged = { i, newNumber ->
                        onAction(
                            PINIntent.OnEnterNumber(
                                index = i,
                                number = newNumber
                            )
                        )
                    },
                    onKeyboardBack = { onAction(PINIntent.OnKeyboardBack) },
                    focusRequesters = focusRequesters,
                    focusManager = focusManager,
                    keyboardManager = keyboardManager,
                    onFocusChanged = { i -> onAction(PINIntent.OnChangeFieldFocused(i)) },
                )
                isError?.let {
                    VerticalSpacer(15.dp)
                    ErrorInfo(it.message.toString())
                }
            }
        }
    }

    BoxWithConstraints(modifier = Modifier.background(color = Colors.Brown10)) {
        TopBalloon(
            backgroundColor = Colors.Brown80,
            iconColor = Colors.White
        )
        Scaffold(
            containerColor = Colors.Brown10,
            modifier = Modifier.padding(top = maxWidth / 2 + 56.dp)
        ) { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).paddingHorizontalMedium(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    VerticalSpacer(40.dp)
                    Text(
                        text = stringResource(Res.string.welcome_back),
                        style = TextStyles.HeadingSmExtraBold(),
                        color = Colors.Brown80
                    )
                    VerticalSpacer(10.dp)
                    Text(
                        text = stringResource(Res.string.use_your_authentication_to_securely),
                        style = TextStyles.ParagraphLg(),
                        color = Colors.Brown100Alpha64,
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ContinueButton(
                        text = Res.string.use_device_password,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onEvent(UnLockEvent.OnContinue) }
                    )
                    VerticalSpacer(16.dp)
                    Text(
                        text = stringResource(Res.string.this_password_is_the_same_validation_method),
                        style = TextStyles.TextSmMedium(),
                        color = Colors.Brown100Alpha64,
                        textAlign = TextAlign.Center
                    )
                    VerticalSpacer(10.dp)
                }
            }
        }
    }
}