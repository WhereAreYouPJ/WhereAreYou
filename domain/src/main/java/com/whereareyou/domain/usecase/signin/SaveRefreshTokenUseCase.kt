package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.repository.SignInRepository

class SaveRefreshTokenUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(refreshToken: String) {
        repository.saveRefreshToken(refreshToken)
    }
}