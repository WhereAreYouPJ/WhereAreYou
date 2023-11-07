package com.whereareyou.domain.usecase.schedule

import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.util.NetworkResult

class AddNewScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        body: AddNewScheduleRequest
    ): NetworkResult<AddNewScheduleResponse> {
        return repository.addNewSchedule(token, body)
    }
}