package com.joohnq.domain.use_case.user

import com.joohnq.domain.entity.User
import com.joohnq.domain.repository.UserRepository

class UpdatePhysicalSymptomsUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User): Boolean = repository.updateUser(user)
}