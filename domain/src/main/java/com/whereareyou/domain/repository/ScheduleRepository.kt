package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyou.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.EndScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.ModifyScheduleRequest
import com.whereareyou.domain.entity.schedule.BriefSchedule
import com.whereareyou.domain.entity.schedule.DetailSchedule
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.domain.entity.schedule.ScheduleCountByDay

interface ScheduleRepository {

    // 월별 일정 정보
    suspend fun getMonthlySchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int
    ): NetworkResult<GetMonthlyScheduleResponse>

    // 일별 간략 정보
    suspend fun getDailyBriefSchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): NetworkResult<GetDailyBriefScheduleResponse>

    // 일별 일정 상세 정보
    suspend fun getDetailSchedule(
        token: String,
        memberId: String,
        scheduleId: String
    ): NetworkResult<GetDetailScheduleResponse>

    // 일정 추가
    suspend fun addNewSchedule(
        token: String,
        body: AddNewScheduleRequest
    ): NetworkResult<AddNewScheduleResponse>

    // 일정 수정
    suspend fun modifySchedule(
        token: String,
        body: ModifyScheduleRequest
    ): NetworkResult<Nothing>

    // 일정 삭제
    suspend fun deleteSchedule(
        token: String,
        body: DeleteScheduleRequest
    ): NetworkResult<Nothing>

    // 일정 수락
    suspend fun acceptSchedule(
        token: String,
        body: AcceptScheduleRequest
    ): NetworkResult<Boolean>

    // 일정 종료
    suspend fun endSchedule(
        token: String,
        body: EndScheduleRequest
    ): NetworkResult<Boolean>

    // 도착 여부
    suspend fun checkArrival(
        token: String,
        body: CheckArrivalRequest
    ): NetworkResult<Boolean>
}