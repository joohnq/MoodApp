package com.joohnq.user.ui.viewmodel.user

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohnq.core.ui.entity.UiState
import com.joohnq.core.ui.mapper.onFailure
import com.joohnq.core.ui.mapper.onSuccess
import com.joohnq.core.ui.mapper.toUiState
import com.joohnq.domain.entity.User
import com.joohnq.domain.use_case.user.AddUserUseCase
import com.joohnq.domain.use_case.user.GetUserUseCase
import com.joohnq.domain.use_case.user.UpdateUserImageBitmapUseCase
import com.joohnq.domain.use_case.user.UpdateUserImageDrawableUseCase
import com.joohnq.domain.use_case.user.UpdateUserNameUseCase
import com.joohnq.domain.use_case.user.UpdateUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val addUserUseCase: AddUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val updateUserImageBitmapUseCase: UpdateUserImageBitmapUseCase,
    private val updateUserImageDrawableUseCase: UpdateUserImageDrawableUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<UserViewModelState> =
        MutableStateFlow(UserViewModelState())
    val state: StateFlow<UserViewModelState> = _state

    private val _sideEffect = Channel<UserSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onAction(intent: UserViewModelIntent) {
        when (intent) {
            is UserViewModelIntent.GetUser -> getUser()
            is UserViewModelIntent.UpdateUser -> updateUser(intent.user)
            is UserViewModelIntent.UpdateUserImageBitmap -> updateUserImageBitmap(intent.image)
            is UserViewModelIntent.UpdateUserName -> updateUserName(intent.name)
            UserViewModelIntent.ResetUpdatingStatus -> changeUpdatingStatus(UiState.Idle)
            is UserViewModelIntent.UpdateUserImageDrawable -> updateUserImageDrawable(intent.i)
            UserViewModelIntent.InitUser -> addUser()
        }
    }

    private fun addUser() = viewModelScope.launch {
        changeAddingStatus(UiState.Loading)
        val res = addUserUseCase(User()).toUiState()
        changeAddingStatus(res)
    }

    private fun updateUser(user: User) = viewModelScope.launch {
        changeUpdatingStatus(UiState.Loading)
        val res = updateUserUseCase(user).toUiState()
        changeUpdatingStatus(res)
    }

    private fun getUser() = viewModelScope.launch {
        changeUserStatus(UiState.Loading)
        val res = getUserUseCase().toUiState()
        changeUserStatus(res)
    }

    private fun updateUserImageBitmap(image: ImageBitmap) = viewModelScope.launch {
        val res = updateUserImageBitmapUseCase(image).toUiState()
        res.onSuccess {
            _sideEffect.send(UserSideEffect.AvatarSavedSuccess)
        }.onFailure {
            _sideEffect.send(UserSideEffect.ShowError(it))
        }
    }

    private fun updateUserImageDrawable(i: Int) = viewModelScope.launch {
        val res = updateUserImageDrawableUseCase(i).toUiState()

        res.onSuccess {
            _sideEffect.send(UserSideEffect.AvatarSavedSuccess)
        }.onFailure {
            _sideEffect.send(UserSideEffect.ShowError(it))
        }
    }

    private fun updateUserName(name: String) = viewModelScope.launch {
        val res = updateUserNameUseCase(name).toUiState()
        res.onSuccess {
            _sideEffect.send(UserSideEffect.UserNameUpdatedSuccess)
        }.onFailure {
            _sideEffect.send(UserSideEffect.ShowError(it))
        }
    }

    private fun changeAddingStatus(status: UiState<Boolean>) {
        _state.update { it.copy(adding = status) }
    }

    private fun changeUpdatingStatus(status: UiState<Boolean>) {
        _state.update { it.copy(updating = status) }
    }

    private fun changeUserStatus(status: UiState<User>) {
        _state.update { it.copy(user = status) }
    }
}