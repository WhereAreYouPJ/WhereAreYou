package com.whereareyou.api

import com.whereareyou.domain.entity.apimessage.location.GetUserLocationResponse
import com.whereareyou.domain.entity.apimessage.location.SendUserLocationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LocationApi {

    // 사용자 실시간 위도 경도
    @GET("info")
    suspend fun getUserLocation(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String
    ): Response<GetUserLocationResponse>

    // 사용자 위도 경도
    @POST("info")
    suspend fun sendUserLocation(
        @Header("Authorization") token: String,
        @Body body: SendUserLocationRequest
    ): Response<Boolean>
}