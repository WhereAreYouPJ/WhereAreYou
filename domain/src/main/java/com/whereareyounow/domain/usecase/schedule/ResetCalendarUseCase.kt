package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.ResetCalendarRequest
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

class ResetCalendarUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        body: ResetCalendarRequest
    ): NetworkResult<Boolean> {
        return repository.resetCalendar(token, body)
    }
}