package com.whereareyounow.api

import com.whereareyounow.data.BuildConfig
import com.whereareyounow.domain.entity.location.SearchLocationResult
import com.whereareyounow.domain.entity.schedule.LocationInformation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchLocationApi {

    @GET("v1/search/local.json")
    suspend fun searchLocationAddress(
        @Header("X-Naver-Client-Id") clientId: String = BuildConfig.CLIENT_ID,
        @Header("X-Naver-Client-Secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("query") query: String,
        @Query("display") display: Int = 5
    ): Response<SearchLocationResult>
}