package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.apimessage.fcm.DeleteFCMTokenRequest
import com.whereareyounow.domain.entity.apimessage.fcm.UpdateFCMTokenRequest
import com.whereareyounow.domain.util.NetworkResult

interface FCMRepository {

    // FCM 토큰 저장 및 갱신
    suspend fun updateFCMToken(
        token: String,
        body: UpdateFCMTokenRequest
    ): NetworkResult<Unit>

    // FCM 토큰 삭제(로그아웃)
    suspend fun deleteFCMToken(
        token: String,
        body: DeleteFCMTokenRequest
    ): NetworkResult<Unit>
}