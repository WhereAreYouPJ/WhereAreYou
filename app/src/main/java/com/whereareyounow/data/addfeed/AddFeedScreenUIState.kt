package com.whereareyounow.data.addfeed

import com.whereareyounow.domain.entity.schedule.ScheduleListItem
import java.io.File

data class AddFeedScreenUIState(
    val scheduleListMap: Map<String, List<ScheduleListItem>> = mapOf(),
    val selectedSchedule: ScheduleListItem? = null,
    val title: String = "",
    val content: String = "",
    val imageUris: List<String> = listOf()
)