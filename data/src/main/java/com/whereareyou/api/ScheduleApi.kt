package com.whereareyou.api

import com.whereareyou.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyou.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.EndScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyou.domain.entity.apimessage.schedule.ModifyScheduleRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ScheduleApi {
    // 월별 일정 정보
    @GET("schedule/month")
    suspend fun getMonthlySchedule(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<GetMonthlyScheduleResponse>

    // 일별 간략 정보
    @GET("schedule/date")
    suspend fun getDailyBriefSchedule(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("date") date: Int
    ): Response<GetDailyBriefScheduleResponse>

    // 일별 일정 상세 정보
    @GET("schedule/details")
    suspend fun getDetailSchedule(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String,
        @Query("scheduleId") scheduleId: String
    ): Response<GetDetailScheduleResponse>

    // 일정 추가
    @POST("schedule")
    suspend fun addNewSchedule(
        @Header("Authorization") token: String,
        @Body body: AddNewScheduleRequest
    ): Response<AddNewScheduleResponse>

    // 일정 내용 수정
    @PUT("schedule")
    suspend fun modifySchedule(
        @Header("Authorization") token: String,
        @Body body: ModifyScheduleRequest
    ): Response<Unit>

    // 일정 멤버 수정
    @PUT("memberschedule")
    suspend fun modifyScheduleMember(
        @Header("Authorization") token: String,
        @Body body: ModifyScheduleMemberRequest
    ): Response<Unit>

    // 일정 삭제
    @DELETE("schedule")
    suspend fun deleteSchedule(
        @Header("Authorization") token: String,
        @Body body: DeleteScheduleRequest
    ): Response<Unit>

    // 일정 수락
    @PUT("schedule/accept")
    suspend fun acceptSchedule(
        @Header("Authorization") token: String,
        @Body body: AcceptScheduleRequest
    ): Response<Boolean>

    // 일정 종료
    @PUT("schedule/closed")
    suspend fun endSchedule(
        @Header("Authorization") token: String,
        @Body body: EndScheduleRequest
    ): Response<Boolean>

    // 도착 여부
    @POST("schedule/arrived")
    suspend fun checkArrival(
        @Header("Authorization") token: String,
        @Body body: CheckArrivalRequest
    ): Response<Boolean>
}