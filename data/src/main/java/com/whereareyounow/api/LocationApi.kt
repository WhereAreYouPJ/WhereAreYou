package com.whereareyounow.api

import com.whereareyounow.domain.entity.UserLocation
import com.whereareyounow.domain.entity.location.LocationFavoriteInfo
import com.whereareyounow.domain.request.location.DeleteFavoriteLocationRequest
import com.whereareyounow.domain.request.location.SendUserLocationRequest
import com.whereareyounow.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface LocationApi {

    // 사용자 위치 정보 조회
    @GET("coordinate")
    suspend fun getUserLocation(
        @Query("memberSeq") memberSeq: Int
    ): Response<ResponseWrapper<UserLocation>>

    // 사용자 위치 정보 전송
    @POST("coordinate")
    suspend fun sendUserLocation(
        @Body body: SendUserLocationRequest
    ): Response<ResponseWrapper<String>>

    // 위치 즐겨찾기 조회
    @GET("location")
    suspend fun getLocationFaborite(
        @Query("memberSeq") memberSeq : Int
    ) : Response<ResponseWrapper<List<LocationFavoriteInfo>>>

    @HTTP(method = "DELETE", path = "location", hasBody = true)
    suspend fun deleteFavoriteLocation(
        @Body deleteFavoriteLocationRequest : DeleteFavoriteLocationRequest
    ) : Response<ResponseWrapper<List<LocationFavoriteInfo>>>



}