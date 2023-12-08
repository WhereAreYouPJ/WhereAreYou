package com.whereareyounow.domain.usecase.signup

import com.whereareyounow.domain.entity.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyounow.domain.repository.SignUpRepository
import com.whereareyounow.domain.util.NetworkResult

class CheckEmailDuplicateUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        email: String
    ): NetworkResult<CheckEmailDuplicateResponse> {
        return repository.checkEmailDuplicate(email)
    }
}