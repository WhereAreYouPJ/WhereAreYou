package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.CreateNewScheduleRequest
import kotlinx.coroutines.flow.flow

class CreateNewScheduleUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: CreateNewScheduleRequest
    ) = flow {
        val response = repository.createNewSchedule(data)
        emit(response)
    }
}