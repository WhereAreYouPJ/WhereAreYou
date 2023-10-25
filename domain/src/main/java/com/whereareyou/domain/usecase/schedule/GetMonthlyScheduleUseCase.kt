package com.whereareyou.domain.usecase.schedule

import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.domain.entity.schedule.ScheduleCountByDay
import com.whereareyou.domain.repository.ScheduleRepository

class GetMonthlyScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String,
        year: Int,
        month: Int
    ): NetworkResult<List<ScheduleCountByDay>> {
        return repository.getMonthlySchedule(token, memberId, year, month)
    }
}