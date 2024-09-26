package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.GetScheduleListRequest
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
