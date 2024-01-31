package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleDetailsRequest
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

class ModifyScheduleDetailsUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        body: ModifyScheduleDetailsRequest
    ): NetworkResult<Unit> {
        return repository.modifyScheduleDetails(token, body)
    }
}