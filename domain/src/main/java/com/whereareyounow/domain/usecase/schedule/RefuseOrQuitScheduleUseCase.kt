package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.RefuseOrQuitScheduleRequest
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

class RefuseOrQuitScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        body: RefuseOrQuitScheduleRequest
    ): NetworkResult<Unit> {
        return repository.refuseOrQuitSchedule(token, body)
    }
}