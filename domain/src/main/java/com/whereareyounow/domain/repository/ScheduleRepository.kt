package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.schedule.DailyScheduleInfo
import com.whereareyounow.domain.entity.schedule.DetailScheduleInfo
import com.whereareyounow.domain.entity.schedule.InvitedSchedule
import com.whereareyounow.domain.entity.schedule.MonthlySchedule
import com.whereareyounow.domain.entity.schedule.ScheduleDDay
import com.whereareyounow.domain.entity.schedule.ScheduleListData
import com.whereareyounow.domain.entity.schedule.ScheduleSeq
import com.whereareyounow.domain.request.schedule.AcceptScheduleInvitationRequest
import com.whereareyounow.domain.request.schedule.CreateNewScheduleRequest
import com.whereareyounow.domain.request.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDailyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDetailScheduleRequest
import com.whereareyounow.domain.request.schedule.GetInvitedScheduleRequest
import com.whereareyounow.domain.request.schedule.GetMonthlyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetScheduleDDayRequest
import com.whereareyounow.domain.request.schedule.GetScheduleListRequest
import com.whereareyounow.domain.request.schedule.ModifyScheduleInfoRequest
import com.whereareyounow.domain.request.schedule.RefuseScheduleInvitationRequest
import com.whereareyounow.domain.util.NetworkResult

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
        data: AcceptScheduleInvitationRequest
    ): NetworkResult<String>

    suspend fun getMonthlySchedule(
        data: GetMonthlyScheduleRequest
    ): NetworkResult<List<MonthlySchedule>>

    suspend fun getScheduleList(
        data: GetScheduleListRequest
    ): NetworkResult<ScheduleListData>

    suspend fun getInvitedSchedule(
        data: GetInvitedScheduleRequest
    ): NetworkResult<List<InvitedSchedule>>

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
    ): NetworkResult<String>

    suspend fun deleteScheduleByCreator(
        data: DeleteScheduleRequest
    ): NetworkResult<String>
}