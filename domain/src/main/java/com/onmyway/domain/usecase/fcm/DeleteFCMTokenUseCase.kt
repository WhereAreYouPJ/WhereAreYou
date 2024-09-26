package com.onmyway.domain.usecase.fcm

import com.onmyway.domain.repository.FCMRepository

class DeleteFCMTokenUseCase(
    private val repository: FCMRepository
) {
//    suspend operator fun invoke(
//        token: String,
//        body: com.whereareyounow.domain.request.fcm.DeleteFCMTokenRequest
//    ): NetworkResult<Unit> {
//        return repository.deleteFCMToken(token, body)
//    }
}