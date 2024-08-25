package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.AcceptScheduleRequestRequest
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