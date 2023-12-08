package com.whereareyounow.domain.usecase.signup

import com.whereareyounow.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyounow.domain.repository.SignUpRepository
import com.whereareyounow.domain.util.NetworkResult

class CheckIdDuplicateUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        userId: String
    ): NetworkResult<CheckIdDuplicateResponse> {
        return repository.checkIdDuplicate(userId)
    }
}