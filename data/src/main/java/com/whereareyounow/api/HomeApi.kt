package com.whereareyounow.api

import com.whereareyounow.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    // 홈 화면 공지사항 사진
    @GET("admin/image")
    suspend fun getHomeImageList(
        @Query("memberSeq") memberSeq: Int
    ): Response<ResponseWrapper<List<String>>>
}