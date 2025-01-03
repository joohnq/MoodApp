package com.joohnq.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface ScreenDimensionsInterface {
    val statusBarHeight: Int
    val osType: OSType
    @Composable fun deviceHeight(): Dp
}

enum class OSType {
    IOS, ANDROID
}

expect class ScreenDimensions : ScreenDimensionsInterface {
    override val statusBarHeight: Int
    override val osType: OSType
    @Composable override fun deviceHeight(): Dp
}
