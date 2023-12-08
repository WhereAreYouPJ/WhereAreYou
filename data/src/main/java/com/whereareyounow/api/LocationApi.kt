package com.whereareyounow.api

import com.whereareyounow.domain.entity.apimessage.location.GetUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.UserLocation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LocationApi {

    // 사용자 실시간 위도 경도
    @POST("info/realTime")
    suspend fun getUserLocation(
        @Header("Authorization") token: String,
        @Body body: GetUserLocationRequest
    ): Response<List<UserLocation>>

    // 사용자 위도 경도
    @POST("info")
    suspend fun sendUserLocation(
        @Header("Authorization") token: String,
        @Body body: SendUserLocationRequest
    ): Response<Boolean>
}