package com.whereareyounow.api

import com.whereareyounow.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyounow.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetScheduleInvitationResponse
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleDetailsRequest
import com.whereareyounow.domain.entity.apimessage.schedule.RefuseOrQuitScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ResetCalendarRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
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
    suspend fun modifyScheduleDetails(
        @Header("Authorization") token: String,
        @Body body: ModifyScheduleDetailsRequest
    ): Response<Unit>

    // 일정 멤버 수정
    @PUT("memberschedule")
    suspend fun modifyScheduleMember(
        @Header("Authorization") token: String,
        @Body body: ModifyScheduleMemberRequest
    ): Response<Unit>

    // 일정 삭제
    @HTTP(method = "DELETE", path = "schedule", hasBody = true)
    suspend fun deleteSchedule(
        @Header("Authorization") token: String,
        @Body body: DeleteScheduleRequest
    ): Response<Unit>

    // 일정 수락
    @PUT("memberschedule/accept")
    suspend fun acceptSchedule(
        @Header("Authorization") token: String,
        @Body body: AcceptScheduleRequest
    ): Response<Boolean>

    // 도착 여부
    @PUT("schedule/arrived")
    suspend fun checkArrival(
        @Header("Authorization") token: String,
        @Body body: CheckArrivalRequest
    ): Response<Boolean>

    // 일정 거절 또는 나가기
    @HTTP(method = "DELETE", path = "memberschedule/refuse", hasBody = true)
    suspend fun refuseOrQuitSchedule(
        @Header("Authorization") token: String,
        @Body body: RefuseOrQuitScheduleRequest
    ): Response<Unit>

    // 일정 초대 목록
    @GET("memberschedule/invite")
    suspend fun getScheduleInvitation(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String
    ): Response<GetScheduleInvitationResponse>

    @HTTP(method = "DELETE", path = "schedule/reset", hasBody = true)
    suspend fun resetCalendar(
        @Header("Authorization") token: String,
        @Body body: ResetCalendarRequest
    ): Response<Boolean>
}