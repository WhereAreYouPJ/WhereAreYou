package com.whereareyounow.api

import com.whereareyounow.domain.request.fcm.DeleteFCMTokenRequest
import com.whereareyounow.domain.request.fcm.UpdateFCMTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface FCMApi {

    // FCM 토큰 저장 및 갱신
    @POST("fcm")
    suspend fun updateFCMToken(
        @Header("Authorization") token: String,
        @Body body: UpdateFCMTokenRequest
    ): Response<Unit>

    // FCM 토큰 삭제
    @DELETE("fcm")
    suspend fun deleteFCMToken(
        @Header("Authorization") token: String,
        @Body body: DeleteFCMTokenRequest
    ): Response<Unit>
}