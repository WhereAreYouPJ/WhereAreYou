package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

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