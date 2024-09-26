package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.AcceptScheduleRequestRequest
import kotlinx.coroutines.flow.flow

class AcceptScheduleRequestUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: AcceptScheduleRequestRequest
    ) = flow {
        val response = repository.acceptScheduleRequest(data)
        emit(response)
    }
}