package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.repository.SignInRepository

class SaveAccessTokenUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        accessToken: String
    ) {
        repository.saveAccessToken(accessToken)
    }
}