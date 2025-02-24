package com.joohnq.auth.ui.presentation.avatar

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import com.joohnq.auth.ui.components.AlertMessageDialog
import com.joohnq.auth.ui.components.ImageSourceOptionDialog
import com.joohnq.auth.ui.presentation.avatar.event.AvatarEvent
import com.joohnq.auth.ui.presentation.avatar.viewmodel.AvatarIntent
import com.joohnq.auth.ui.presentation.avatar.viewmodel.AvatarViewModel
import com.joohnq.permission.*
import com.joohnq.shared_resources.*
import com.joohnq.shared_resources.remember.rememberAvatars
import com.joohnq.shared_resources.remember.rememberSnackBarState
import com.joohnq.ui.sharedViewModel
import com.joohnq.user.ui.viewmodel.UserIntent
import com.joohnq.user.ui.viewmodel.UserSideEffect
import com.joohnq.user.ui.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun AvatarScreen(
    onNavigateToUserName: () -> Unit,
) {
    val snackBarState = rememberSnackBarState()
    val avatars = rememberAvatars()
    val pagerState = rememberPagerState(pageCount = { avatars.size })
    val avatarViewModel: AvatarViewModel = sharedViewModel()
    val avatarState by avatarViewModel.state.collectAsState()
    val userViewModel: UserViewModel = sharedViewModel()
    val scope = rememberCoroutineScope()
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager =
        createPermissionsManager(object : PermissionCallback {
            override fun onPermissionStatus(
                permissionType: PermissionType,
                status: PermissionStatus,
            ) {
                when (status) {
                    PermissionStatus.GRANTED -> {
                        when (permissionType) {
                            PermissionType.CAMERA -> launchCamera = true
                            PermissionType.GALLERY -> launchGallery = true
                        }
                    }

                    else -> {
                        permissionRationalDialog = true
                    }
                }
            }
        })
    val cameraManager = rememberCameraManager {
        scope.launch {
            avatarViewModel.onAction(AvatarIntent.UpdateImageBitmap(it?.toImageBitmap()))
        }
    }
    val galleryManager = rememberGalleryManager {
        scope.launch {
            avatarViewModel.onAction(AvatarIntent.UpdateImageBitmap(it?.toImageBitmap()))
        }
    }
    if (imageSourceOptionDialog) {
        ImageSourceOptionDialog(onDismissRequest = {
            imageSourceOptionDialog = false
        }, onGalleryRequest = {
            imageSourceOptionDialog = false
            launchGallery = true
        }, onCameraRequest = {
            imageSourceOptionDialog = false
            launchCamera = true
        })
    }
    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery = false
    }
    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        launchCamera = false
    }
    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }
    if (permissionRationalDialog) {
        AlertMessageDialog(
            title = Res.string.permission_required,
            message = Res.string.to_set_your_profile_picture,
            positiveButtonText = Res.string.settings,
            negativeButtonText = Res.string.cancel,
            onPositiveClick = {
                permissionRationalDialog = false
                launchSetting = true
            },
            onNegativeClick = {
                permissionRationalDialog = false
            }
        )
    }

    fun onError(error: String) {
        scope.launch {
            snackBarState.showSnackbar(error)
        }
    }

    fun onEvent(event: AvatarEvent) {
        when (event) {
            AvatarEvent.OnPickAvatar -> {
                imageSourceOptionDialog = true
            }

            AvatarEvent.OnContinue -> {
                val action = if (avatarState.imageBitmap == null)
                    UserIntent.UpdateUserImageDrawable(avatarState.selectedDrawableIndex)
                else
                    UserIntent.UpdateUserImageBitmap(avatarState.imageBitmap!!)

                userViewModel.onAction(action)
            }
        }
    }

    LaunchedEffect(userViewModel) {
        userViewModel.sideEffect.collect { event ->
            when (event) {
                is UserSideEffect.AvatarSavedSuccess -> {
                    onNavigateToUserName()
                }

                is UserSideEffect.ShowError -> onError(event.error)
                else -> {}
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            avatarViewModel.onAction(AvatarIntent.UpdateImageDrawableIndex(page))
        }
    }

    AvatarUI(
        snackBarState = snackBarState,
        state = avatarState,
        avatars = avatars,
        onEvent = ::onEvent,
    )
}
