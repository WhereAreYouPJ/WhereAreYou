package com.whereareyou.domain.usecase.signup

import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult

class CheckEmailDuplicateUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        email: String
    ): NetworkResult<String> {
        return repository.checkEmailDuplicate(email)
    }
}