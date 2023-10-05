package com.whereareyou.api

import com.whereareyou.model.GetDailyScheduleResponse
import com.whereareyou.model.GetMonthlyScheduleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WhereAreYouApi {
    @GET("schedule/month")
    suspend fun getMonthlySchedule(
        @Query("memberId") memberId: String,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<GetMonthlyScheduleResponse>

    @GET("schedule/date")
    suspend fun getDailySchedule(
        @Query("memberId") memberId: String,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("date") date: Int
    ): Response<GetDailyScheduleResponse>
}