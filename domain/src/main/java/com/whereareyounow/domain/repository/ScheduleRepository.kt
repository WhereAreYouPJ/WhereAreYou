package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyounow.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetScheduleInvitationResponse
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleDetailsRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyounow.domain.entity.apimessage.schedule.RefuseOrQuitScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ResetCalendarRequest
import com.whereareyounow.domain.util.NetworkResult

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

    // 일정 내용 수정
    suspend fun modifyScheduleDetails(
        token: String,
        body: ModifyScheduleDetailsRequest
    ): NetworkResult<Unit>

    // 일정 멤버 수정
    suspend fun modifyScheduleMember(
        token: String,
        body: ModifyScheduleMemberRequest
    ): NetworkResult<Unit>

    // 일정 삭제
    suspend fun deleteSchedule(
        token: String,
        body: DeleteScheduleRequest
    ): NetworkResult<Unit>

    // 일정 수락
    suspend fun acceptSchedule(
        token: String,
        body: AcceptScheduleRequest
    ): NetworkResult<Boolean>

    // 일정 종료
    suspend fun refuseOrQuitSchedule(
        token: String,
        body: RefuseOrQuitScheduleRequest
    ): NetworkResult<Unit>

    // 도착 여부
    suspend fun checkArrival(
        token: String,
        body: CheckArrivalRequest
    ): NetworkResult<Boolean>

    // 일정 초대 목록
    suspend fun getScheduleInvitation(
        token: String,
        memberId: String
    ): NetworkResult<GetScheduleInvitationResponse>

    // 캘린더 삭제
    suspend fun resetCalendar(
        token: String,
        body: ResetCalendarRequest
    ): NetworkResult<Boolean>
}