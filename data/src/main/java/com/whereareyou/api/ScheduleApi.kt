package com.whereareyou.api

import com.whereareyou.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyou.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyou.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyou.apimessage.schedule.SendNewScheduleRequest
import com.whereareyou.apimessage.schedule.SendNewScheduleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleApi {
    @GET("schedule/month")
    suspend fun getMonthlySchedule(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<GetMonthlyScheduleResponse>

    @GET("schedule/date")
    suspend fun getDailyBriefSchedule(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("date") date: Int
    ): Response<GetDailyBriefScheduleResponse>

    @GET("schedule/details")
    suspend fun getDetailSchedule(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String,
        @Query("scheduleId") scheduleId: String
    ): Response<GetDetailScheduleResponse>

    @POST("schedule")
    suspend fun addNewSchedule(
        @Header("Authorization") token: String,
        @Body body: SendNewScheduleRequest
    ): Response<SendNewScheduleResponse>
}