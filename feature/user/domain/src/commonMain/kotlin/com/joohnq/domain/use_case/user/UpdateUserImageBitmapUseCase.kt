package com.joohnq.domain.use_case.user

import androidx.compose.ui.graphics.ImageBitmap
import com.joohnq.core.ui.toByteArray
import com.joohnq.domain.entity.ImageType
import com.joohnq.domain.repository.UserRepository
import com.joohnq.storage.domain.FileStorage

class UpdateUserImageBitmapUseCase(
    private val fileStorage: FileStorage,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(image: ImageBitmap): Result<Boolean> {
        val value = fileStorage.saveImage(
            directory = "avatar",
            fileName = "avatar.png",
            data = image.toByteArray()
        )

        return userRepository.updateUserImage(
            image = value,
            imageType = ImageType.DEVICE
        )
    }
}