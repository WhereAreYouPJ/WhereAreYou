package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.DeleteScheduleRequest
import kotlinx.coroutines.flow.flow

class DeleteScheduleByCreatorUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: DeleteScheduleRequest
    ) = flow {
        val response = repository.deleteScheduleByCreator(data)
        emit(response)
    }
}