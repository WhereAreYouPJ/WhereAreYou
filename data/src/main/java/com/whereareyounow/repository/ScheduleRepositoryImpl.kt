package com.whereareyounow.repository

import com.whereareyounow.api.ScheduleApi
import com.whereareyounow.domain.entity.schedule.DailyScheduleInfo
import com.whereareyounow.domain.entity.schedule.DetailScheduleInfo
import com.whereareyounow.domain.entity.schedule.MonthlySchedule
import com.whereareyounow.domain.entity.schedule.ScheduleDDay
import com.whereareyounow.domain.entity.schedule.ScheduleListData
import com.whereareyounow.domain.entity.schedule.ScheduleListItem
import com.whereareyounow.domain.entity.schedule.ScheduleSeq
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.AcceptScheduleRequestRequest
import com.whereareyounow.domain.request.schedule.CreateNewScheduleRequest
import com.whereareyounow.domain.request.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDailyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDetailScheduleRequest
import com.whereareyounow.domain.request.schedule.GetMonthlyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetScheduleDDayRequest
import com.whereareyounow.domain.request.schedule.GetScheduleListRequest
import com.whereareyounow.domain.request.schedule.ModifyScheduleInfoRequest
import com.whereareyounow.domain.request.schedule.RefuseScheduleInvitationRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class ScheduleRepositoryImpl(
    private val scheduleApi: ScheduleApi
) : ScheduleRepository, NetworkResultHandler {

    override suspend fun getDetailSchedule(
        data: GetDetailScheduleRequest
    ): NetworkResult<DetailScheduleInfo> {
        return handleResult { scheduleApi.getDetailSchedule(
            scheduleSeq = data.scheduleSeq,
            memberSeq = data.memberSeq
        ) }
    }

    override suspend fun modifyScheduleInfo(
        data: ModifyScheduleInfoRequest
    ): NetworkResult<ScheduleSeq> {
        return handleResult { scheduleApi.modifyScheduleInfo(body = data) }
    }

    override suspend fun createNewSchedule(
        data: CreateNewScheduleRequest
    ): NetworkResult<ScheduleSeq> {
        return handleResult { scheduleApi.createNewSchedule(body = data) }
    }

    override suspend fun acceptScheduleRequest(
        data: AcceptScheduleRequestRequest
    ): NetworkResult<Unit> {
        return handleResult { scheduleApi.acceptScheduleRequest(body = data) }
    }

    override suspend fun getMonthlySchedule(
        data: GetMonthlyScheduleRequest
    ): NetworkResult<List<MonthlySchedule>> {
        return handleResult { scheduleApi.getMonthlySchedule(
            yearMonth = data.yearMonth,
            memberSeq = data.memberSeq
        ) }
    }

    override suspend fun getScheduleList(
        data: GetScheduleListRequest
    ): NetworkResult<ScheduleListData> {
        return handleResult { scheduleApi.getScheduleList(
            memberSeq = data.memberSeq,
            page = data.page,
            size = data.size
        ) }
    }

    override suspend fun getScheduleDDay(
        data: GetScheduleDDayRequest
    ): NetworkResult<List<ScheduleDDay>> {
        return handleResult { scheduleApi.getScheduleDDay(
            memberSeq = data.memberSeq
        ) }
    }

    override suspend fun getDailySchedule(
        data: GetDailyScheduleRequest
    ): NetworkResult<List<DailyScheduleInfo>> {
        return handleResult { scheduleApi.getDailySchedule(
            date = data.date,
            memberSeq = data.memberSeq
        ) }
    }

    override suspend fun refuseScheduleInvitation(
        data: RefuseScheduleInvitationRequest
    ): NetworkResult<Unit> {
        return handleResult { scheduleApi.refuseScheduleInvitation(body = data) }
    }

    override suspend fun deleteScheduleByInvitor(
        data: DeleteScheduleRequest
    ): NetworkResult<Unit> {
        return handleResult { scheduleApi.deleteScheduleByInvitor(body = data) }
    }

    override suspend fun deleteScheduleByCreator(
        data: DeleteScheduleRequest
    ): NetworkResult<Unit> {
        return handleResult { scheduleApi.deleteScheduleByCreator(body = data) }
    }
}