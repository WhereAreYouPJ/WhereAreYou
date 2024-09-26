package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.GetMonthlyScheduleRequest
import kotlinx.coroutines.flow.flow

class GetMonthlyScheduleUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: GetMonthlyScheduleRequest
    ) = flow {
        val response = repository.getMonthlySchedule(data)
        emit(response)
    }
}