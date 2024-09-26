package com.onmyway.api

import com.onmyway.domain.request.schedule.AcceptScheduleRequestRequest
import com.onmyway.domain.request.schedule.CreateNewScheduleRequest
import com.onmyway.domain.request.schedule.DeleteScheduleRequest
import com.onmyway.domain.request.schedule.ModifyScheduleInfoRequest
import com.onmyway.domain.entity.schedule.DailyScheduleInfo
import com.onmyway.domain.entity.schedule.DetailScheduleInfo
import com.onmyway.domain.entity.schedule.MonthlySchedule
import com.onmyway.domain.entity.schedule.ScheduleDDay
import com.onmyway.domain.entity.schedule.ScheduleListItem
import com.onmyway.domain.entity.schedule.ScheduleSeq
import com.onmyway.domain.request.schedule.RefuseScheduleInvitationRequest
import com.onmyway.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ScheduleApi {

    // 일정 상세 정보
    @GET("schedule")
    suspend fun getDetailSchedule(
        @Query("scheduleSeq") scheduleSeq: Int,
        @Query("memberSeq") memberSeq: Int,
    ): Response<ResponseWrapper<DetailScheduleInfo>>

    // 일정 내용 수정
    @PUT("schedule")
    suspend fun modifyScheduleInfo(
        @Body body: ModifyScheduleInfoRequest
    ): Response<ResponseWrapper<ScheduleSeq>>

    // 일정 생성
    @POST("schedule")
    suspend fun createNewSchedule(
        @Body body: CreateNewScheduleRequest
    ): Response<ResponseWrapper<ScheduleSeq>>

    // 일정 초대 수락
    @POST("schedule/accept")
    suspend fun acceptScheduleRequest(
        @Body body: AcceptScheduleRequestRequest
    ): Response<ResponseWrapper<Unit>>

    // 월별 일정 조회
    @GET("schedule/month")
    suspend fun getMonthlySchedule(
        @Query("yearMonth") yearMonth: String,
        @Query("memberSeq") memberSeq: Int
    ): Response<ResponseWrapper<List<MonthlySchedule>>>

    // 일정 List 조회
    @GET("schedule/list")
    suspend fun getScheduleList(
        @Query("memberSeq") memberSeq: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<ResponseWrapper<List<ScheduleListItem>>>

    // 일정 D-Day 조회 API
    @GET("schedule/dday")
    suspend fun getScheduleDDay(
        @Query("memberSeq") memberSeq: Int
    ): Response<ResponseWrapper<List<ScheduleDDay>>>

    // 해당 날짜 일정 조회
    @GET("schedule/date")
    suspend fun getDailySchedule(
        @Query("date") date: String,
        @Query("memberSeq") memberSeq: Int
    ): Response<ResponseWrapper<List<DailyScheduleInfo>>>

    @HTTP(method = "DELETE", path = "schedule/refuse", hasBody = true)
    suspend fun refuseScheduleInvitation(
        @Body body: RefuseScheduleInvitationRequest
    ): Response<ResponseWrapper<Unit>>

    // 일정 삭제(일정 초대자인 경우)
    @HTTP(method = "DELETE", path = "schedule/invited", hasBody = true)
    suspend fun deleteScheduleByInvitor(
        @Body body: DeleteScheduleRequest
    ): Response<ResponseWrapper<Unit>>

    // 일정 삭제(일정 생성자인 경우)
    @HTTP(method = "DELETE", path = "schedule/creator", hasBody = true)
    suspend fun deleteScheduleByCreator(
        @Body body: DeleteScheduleRequest
    ): Response<ResponseWrapper<Unit>>

}