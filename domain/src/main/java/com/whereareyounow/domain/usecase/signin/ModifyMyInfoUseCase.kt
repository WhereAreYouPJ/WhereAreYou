package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult
import java.io.File

class ModifyMyInfoUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String,
        image: File?,
        userName: String,
        userId: String
    ): NetworkResult<Unit> {
        return repository.modifyMyInfo(token, memberId, image, userName, userId)
    }
}