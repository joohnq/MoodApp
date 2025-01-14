package com.joohnq.auth.ui.presentation.avatar.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

sealed class AvatarViewModelIntent {
    data class UpdateImageBitmap(val imageBitmap: ImageBitmap? = null) : AvatarViewModelIntent()
    data class UpdateImageDrawableIndex(val i: Int) : AvatarViewModelIntent()
}

data class AvatarViewModelState(
    val imageBitmap: ImageBitmap? = null,
    val selectedDrawableIndex: Int = 0,
)

class AvatarViewModel : ViewModel() {
    private val _state: MutableStateFlow<AvatarViewModelState> =
        MutableStateFlow(AvatarViewModelState())
    val state: StateFlow<AvatarViewModelState> = _state

    fun onAction(intent: AvatarViewModelIntent) {
        when (intent) {
            is AvatarViewModelIntent.UpdateImageBitmap -> updateImageBitmap(intent.imageBitmap)
            is AvatarViewModelIntent.UpdateImageDrawableIndex -> updateImageDrawableIndex(intent.i)
        }
    }

    private fun updateImageBitmap(imageBitmap: ImageBitmap?) {
        _state.update {
            it.copy(imageBitmap = imageBitmap)
        }
    }

    private fun updateImageDrawableIndex(i: Int) {
        _state.update {
            it.copy(selectedDrawableIndex = i)
        }
    }
}