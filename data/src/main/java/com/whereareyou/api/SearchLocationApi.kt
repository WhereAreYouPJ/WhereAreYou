package com.whereareyou.api

import com.whereareyou.data.BuildConfig
import com.whereareyou.apimessage.schedule.GetLocationAddressResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchLocationApi {

    @GET("v1/search/local.json")
    suspend fun getLocationAddress(
        @Header("X-Naver-Client-Id") clientId: String = BuildConfig.CLIENT_ID,
        @Header("X-Naver-Client-Secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("query") query: String,
        @Query("display") display: Int = 5
    ): Response<GetLocationAddressResponse>
}