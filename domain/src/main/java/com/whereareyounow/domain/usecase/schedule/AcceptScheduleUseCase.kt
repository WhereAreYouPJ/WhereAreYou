package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

class AcceptScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        body: AcceptScheduleRequest
    ): NetworkResult<Boolean> {
        return repository.acceptSchedule(token, body)
    }
}