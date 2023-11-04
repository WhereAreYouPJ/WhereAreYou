package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.repository.SignInRepository

class SaveAccessTokenUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        accessToken: String
    ) {
        repository.saveAccessToken(accessToken)
    }
}