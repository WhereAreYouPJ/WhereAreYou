package com.whereareyounow.data.home

import com.whereareyounow.domain.entity.schedule.DailyScheduleInfo
import com.whereareyounow.domain.entity.schedule.ScheduleDDay

data class HomeScreenData(
    val imageList: List<String> = emptyList(),
    val dailyScheduleList: List<DailyScheduleInfo> = emptyList(),
    val dDayScheduleList: List<ScheduleDDay> = emptyList()
)
