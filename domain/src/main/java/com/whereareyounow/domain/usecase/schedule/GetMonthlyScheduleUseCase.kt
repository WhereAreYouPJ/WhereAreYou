package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.GetMonthlyScheduleRequest
import kotlinx.coroutines.flow.flow

class GetMonthlyScheduleUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: GetMonthlyScheduleRequest
    ) = flow {
        val response = repository.getMonthlySchedule(data)
        emit(data)
    }
}