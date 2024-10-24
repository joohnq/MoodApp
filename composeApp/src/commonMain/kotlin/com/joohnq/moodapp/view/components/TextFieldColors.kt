package com.joohnq.moodapp.view.components

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

fun TextFieldColors(
    indicatorColor: Color = Color.Unspecified,
    textColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    errorColor: Color = Color.Unspecified,
    cursorColor: Color = Color.Unspecified,
    leadingIconColor: Color = Color.Unspecified,
    trailingIconColor: Color = Color.Unspecified,
    labelColor: Color = Color.Unspecified,
    placeholderColor: Color,
    supportingTextColor: Color = Color.Unspecified,
    prefixColor: Color = Color.Unspecified,
    suffixColor: Color = Color.Unspecified,
): TextFieldColors =
    TextFieldColors(
        focusedIndicatorColor = indicatorColor,
        unfocusedTextColor = textColor,
        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        focusedTextColor = textColor,
        disabledTextColor = textColor,
        errorTextColor = errorColor,
        disabledContainerColor = containerColor,
        errorContainerColor = errorColor,
        cursorColor = cursorColor,
        errorCursorColor = errorColor,
        textSelectionColors = TextSelectionColors(
            handleColor = indicatorColor,
            backgroundColor = containerColor
        ),
        unfocusedIndicatorColor = indicatorColor,
        disabledIndicatorColor = indicatorColor,
        errorIndicatorColor = errorColor,
        focusedLeadingIconColor = leadingIconColor,
        unfocusedLeadingIconColor = leadingIconColor,
        disabledLeadingIconColor = leadingIconColor,
        errorLeadingIconColor = errorColor,
        focusedTrailingIconColor = trailingIconColor,
        unfocusedTrailingIconColor = trailingIconColor,
        disabledTrailingIconColor = trailingIconColor,
        errorTrailingIconColor = errorColor,
        focusedLabelColor = labelColor,
        unfocusedLabelColor = labelColor,
        disabledLabelColor = labelColor,
        errorLabelColor = errorColor,
        focusedPlaceholderColor = placeholderColor,
        unfocusedPlaceholderColor = placeholderColor,
        disabledPlaceholderColor = placeholderColor,
        errorPlaceholderColor = errorColor,
        focusedSupportingTextColor = supportingTextColor,
        unfocusedSupportingTextColor = supportingTextColor,
        disabledSupportingTextColor = supportingTextColor,
        errorSupportingTextColor = errorColor,
        focusedPrefixColor = prefixColor,
        unfocusedPrefixColor = prefixColor,
        disabledPrefixColor = prefixColor,
        errorPrefixColor = errorColor,
        focusedSuffixColor = suffixColor,
        unfocusedSuffixColor = suffixColor,
        disabledSuffixColor = suffixColor,
        errorSuffixColor = errorColor,
    )

@Composable
fun TextFieldColors(
    textColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    errorColor: Color = Color.Unspecified,
    cursorColor: Color = Color.Unspecified,
    leadingIconColor: Color = Color.Unspecified,
    trailingIconColor: Color = Color.Unspecified,
    labelColor: Color = Color.Unspecified,
    placeholderColor: Color,
    supportingTextColor: Color = Color.Unspecified,
    prefixColor: Color = Color.Unspecified,
    focusedBorderColor: Color = Color.Unspecified,
    unfocusedBorderColor: Color = Color.Unspecified,
    errorBorderColor: Color = Color.Unspecified,
): TextFieldColors =
    OutlinedTextFieldDefaults.colors(
        unfocusedTextColor = textColor,
        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        focusedTextColor = textColor,
        disabledTextColor = textColor,
        errorTextColor = errorColor,
        disabledContainerColor = containerColor,
        errorContainerColor = errorColor,
        cursorColor = cursorColor,
        errorCursorColor = errorColor,
        focusedLeadingIconColor = leadingIconColor,
        unfocusedLeadingIconColor = leadingIconColor,
        disabledLeadingIconColor = leadingIconColor,
        errorLeadingIconColor = errorColor,
        focusedTrailingIconColor = trailingIconColor,
        unfocusedTrailingIconColor = trailingIconColor,
        disabledTrailingIconColor = trailingIconColor,
        errorTrailingIconColor = errorColor,
        focusedLabelColor = labelColor,
        unfocusedLabelColor = labelColor,
        disabledLabelColor = labelColor,
        errorLabelColor = errorColor,
        focusedPlaceholderColor = placeholderColor,
        unfocusedPlaceholderColor = placeholderColor,
        disabledPlaceholderColor = placeholderColor,
        errorPlaceholderColor = errorColor,
        focusedSupportingTextColor = supportingTextColor,
        unfocusedSupportingTextColor = supportingTextColor,
        disabledSupportingTextColor = supportingTextColor,
        errorSupportingTextColor = errorColor,
        focusedPrefixColor = prefixColor,
        unfocusedPrefixColor = prefixColor,
        disabledPrefixColor = prefixColor,
        errorPrefixColor = errorColor,
        disabledBorderColor = unfocusedBorderColor,
        errorBorderColor = errorBorderColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor
    )
