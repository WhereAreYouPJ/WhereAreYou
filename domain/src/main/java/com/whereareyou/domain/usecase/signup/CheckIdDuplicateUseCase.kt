package com.whereareyou.domain.usecase.signup

import com.whereareyou.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult

class CheckIdDuplicateUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        id: String
    ): NetworkResult<CheckIdDuplicateResponse> {
        return repository.checkIdDuplicate(id)
    }
}