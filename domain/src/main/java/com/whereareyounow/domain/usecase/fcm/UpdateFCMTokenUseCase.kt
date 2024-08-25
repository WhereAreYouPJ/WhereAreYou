package com.whereareyounow.domain.usecase.fcm

import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.domain.util.NetworkResult

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