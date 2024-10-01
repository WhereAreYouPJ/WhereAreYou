package com.whereareyounow.repository

import com.whereareyounow.api.FCMApi
import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.util.NetworkResultHandler

class FCMRepositoryImpl(
    private val api: FCMApi
) : FCMRepository, NetworkResultHandler {

//    /**
//     * FCM 토큰 저장 및 갱신
//     * [FCMRepository.updateFCMToken]
//     */
//    override suspend fun updateFCMToken(
//        token: String,
//        body: com.whereareyounow.domain.request.fcm.UpdateFCMTokenRequest
//    ): NetworkResult<Unit> {
//        return handleResult { api.updateFCMToken(token, body) }
//    }
//
//    /**
//     * FCM 토큰 삭제(로그아웃)
//     * [FCMRepository.deleteFCMToken]
//     */
//    override suspend fun deleteFCMToken(
//        token: String,
//        body: com.whereareyounow.domain.request.fcm.DeleteFCMTokenRequest
//    ): NetworkResult<Unit> {
//        return handleResult { api.deleteFCMToken(token, body) }
//    }
}