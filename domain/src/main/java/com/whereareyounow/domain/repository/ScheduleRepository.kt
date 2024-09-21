package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.schedule.DailyScheduleInfo
import com.whereareyounow.domain.entity.schedule.DetailScheduleInfo
import com.whereareyounow.domain.entity.schedule.MonthlySchedule
import com.whereareyounow.domain.entity.schedule.ScheduleDDay
import com.whereareyounow.domain.entity.schedule.ScheduleListItem
import com.whereareyounow.domain.entity.schedule.ScheduleSeq
import com.whereareyounow.domain.request.schedule.AcceptScheduleRequestRequest
import com.whereareyounow.domain.request.schedule.CreateNewScheduleRequest
import com.whereareyounow.domain.request.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDailyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDetailScheduleRequest
import com.whereareyounow.domain.request.schedule.GetMonthlyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetScheduleDDayRequest
import com.whereareyounow.domain.request.schedule.GetScheduleListRequest
import com.whereareyounow.domain.request.schedule.ModifyScheduleInfoRequest
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
    ): NetworkResult<DailyScheduleInfo>

    suspend fun deleteScheduleByInvitor(
        data: DeleteScheduleRequest
    ): NetworkResult<Unit>

    suspend fun deleteScheduleByCreator(
        data: DeleteScheduleRequest
    ): NetworkResult<Unit>
}