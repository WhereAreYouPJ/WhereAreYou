package com.whereareyou.domain.usecase.schedule

import com.whereareyou.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyou.domain.entity.schedule.BriefSchedule
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.util.NetworkResult

class GetDailyBriefScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): NetworkResult<GetDailyBriefScheduleResponse> {
        return repository.getDailyBriefSchedule(token, memberId, year, month, date)
    }
}