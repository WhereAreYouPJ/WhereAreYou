package com.whereareyou.domain.usecase.schedule

import com.whereareyou.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.util.NetworkResult

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