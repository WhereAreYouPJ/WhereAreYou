package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

class DeleteScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        body: DeleteScheduleRequest
    ): NetworkResult<Unit> {
        return repository.deleteSchedule(token, body)
    }
}