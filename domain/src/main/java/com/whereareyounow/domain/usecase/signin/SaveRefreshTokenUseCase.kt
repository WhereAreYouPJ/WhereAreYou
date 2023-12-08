package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.repository.SignInRepository

class SaveRefreshTokenUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(refreshToken: String) {
        repository.saveRefreshToken(refreshToken)
    }
}