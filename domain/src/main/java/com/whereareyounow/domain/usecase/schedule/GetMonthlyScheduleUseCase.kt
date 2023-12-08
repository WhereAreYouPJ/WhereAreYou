package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.domain.repository.ScheduleRepository

class GetMonthlyScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String,
        year: Int,
        month: Int
    ): NetworkResult<GetMonthlyScheduleResponse> {
        return repository.getMonthlySchedule(token, memberId, year, month)
    }
}