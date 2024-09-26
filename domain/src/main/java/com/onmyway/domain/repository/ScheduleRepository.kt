package com.onmyway.domain.repository

import com.onmyway.domain.entity.schedule.DailyScheduleInfo
import com.onmyway.domain.entity.schedule.DetailScheduleInfo
import com.onmyway.domain.entity.schedule.MonthlySchedule
import com.onmyway.domain.entity.schedule.ScheduleDDay
import com.onmyway.domain.entity.schedule.ScheduleListItem
import com.onmyway.domain.entity.schedule.ScheduleSeq
import com.onmyway.domain.request.schedule.AcceptScheduleRequestRequest
import com.onmyway.domain.request.schedule.CreateNewScheduleRequest
import com.onmyway.domain.request.schedule.DeleteScheduleRequest
import com.onmyway.domain.request.schedule.GetDailyScheduleRequest
import com.onmyway.domain.request.schedule.GetDetailScheduleRequest
import com.onmyway.domain.request.schedule.GetMonthlyScheduleRequest
import com.onmyway.domain.request.schedule.GetScheduleDDayRequest
import com.onmyway.domain.request.schedule.GetScheduleListRequest
import com.onmyway.domain.request.schedule.ModifyScheduleInfoRequest
import com.onmyway.domain.request.schedule.RefuseScheduleInvitationRequest
import com.onmyway.domain.util.NetworkResult

interface ScheduleRepository {

    suspend fun getDetailSchedule(
        data: GetDetailScheduleRequest
    ): NetworkResult<DetailScheduleInfo>

    suspend fun modifyScheduleInfo(
        data: ModifyScheduleInfoRequest
    ): NetworkResult<ScheduleSeq>

    suspend fun createNewSchedule(
        data: CreateNewScheduleRequest
    ): NetworkResult<ScheduleSeq>

    suspend fun acceptScheduleRequest(
        data: AcceptScheduleRequestRequest
    ): NetworkResult<Unit>

    suspend fun getMonthlySchedule(
        data: GetMonthlyScheduleRequest
    ): NetworkResult<List<MonthlySchedule>>

    suspend fun getScheduleList(
        data: GetScheduleListRequest
    ): NetworkResult<List<ScheduleListItem>>

    suspend fun getScheduleDDay(
        data: GetScheduleDDayRequest
    ): NetworkResult<List<ScheduleDDay>>

    suspend fun getDailySchedule(
        data: GetDailyScheduleRequest
    ): NetworkResult<List<DailyScheduleInfo>>

    suspend fun refuseScheduleInvitation(
        data: RefuseScheduleInvitationRequest
    ): NetworkResult<Unit>

    suspend fun deleteScheduleByInvitor(
        data: DeleteScheduleRequest
    ): NetworkResult<Unit>

    suspend fun deleteScheduleByCreator(
        data: DeleteScheduleRequest
    ): NetworkResult<Unit>
}