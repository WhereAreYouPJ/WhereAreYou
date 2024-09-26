package com.onmyway.api

import com.onmyway.domain.entity.location.LocationFaboriteInfo
import com.onmyway.domain.request.location.GetUserLocationRequest
import com.onmyway.domain.request.location.SendUserLocationRequest
import com.onmyway.domain.request.location.UserLocation
import com.onmyway.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

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

    // 위치 즐겨찾기 조회
    @GET("location")
    suspend fun getLocationFaborite(
        @Query("memberSeq ") memberSeq : Int
    ) : Response<ResponseWrapper<LocationFaboriteInfo>>



}