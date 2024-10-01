package com.whereareyounow.domain.usecase.fcm

import com.whereareyounow.domain.repository.FCMRepository

class UpdateFCMTokenUseCase(
    private val repository: FCMRepository
) {
//    suspend operator fun invoke(
//        token: String,
//        body: com.whereareyounow.domain.request.fcm.UpdateFCMTokenRequest
//    ): NetworkResult<Unit> {
//        return repository.updateFCMToken(token, body)
//    }
}