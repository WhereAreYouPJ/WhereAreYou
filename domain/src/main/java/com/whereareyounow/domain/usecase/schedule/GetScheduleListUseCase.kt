package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.GetScheduleListRequest
import kotlinx.coroutines.flow.flow

data class GetScheduleListUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: GetScheduleListRequest
    ) = flow {
        val response = repository.getScheduleList(data)
        emit(response)
    }
}
