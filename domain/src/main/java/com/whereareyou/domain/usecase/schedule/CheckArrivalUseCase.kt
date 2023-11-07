package com.whereareyou.domain.usecase.schedule

import com.whereareyou.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.util.NetworkResult

class CheckArrivalUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        body: CheckArrivalRequest
    ): NetworkResult<Boolean> {
        return repository.checkArrival(token, body)
    }
}