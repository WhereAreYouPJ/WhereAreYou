package com.onmyway.data.calendar

import com.onmyway.domain.entity.schedule.DailyScheduleInfo
import com.onmyway.domain.entity.schedule.MonthlySchedule
import com.onmyway.globalvalue.type.VisualType
import java.time.LocalDate

data class CalendarScreenUIState(
    val selectedYear: Int = 0,
    val selectedMonth: Int = 0,
    val selectedDate: Int = 0,
    val selectedDayOfWeek: Int = 0,
    val selectedMonthCalendarInfoMap: Map<String, MutableList<Pair<MonthlySchedule?, VisualType>>> = emptyMap(),
    val dateList: List<LocalDate> = emptyList(),
    val selectedDateDailyScheduleInfoList: List<DailyScheduleInfo> = emptyList()
)