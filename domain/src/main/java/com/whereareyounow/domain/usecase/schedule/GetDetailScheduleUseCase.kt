package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.GetDetailScheduleRequest
import kotlinx.coroutines.flow.flow

class GetDetailScheduleUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: GetDetailScheduleRequest
    ) = flow {
        val response = repository.getDetailSchedule(data)
        emit(response)
    }
}