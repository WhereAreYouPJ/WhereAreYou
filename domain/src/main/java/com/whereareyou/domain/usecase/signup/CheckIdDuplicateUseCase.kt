package com.whereareyou.domain.usecase.signup

import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult

class CheckIdDuplicateUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        id: String
    ): NetworkResult<String> {
        return repository.checkIdDuplicate(id)
    }
}