package com.whereareyounow.data.calendar

import com.whereareyounow.domain.entity.schedule.BriefSchedule

data class CalendarScreenUIState(
    val selectedYear: Int = 0,
    val selectedMonth: Int = 0,
    val selectedDate: Int = 0,
    val selectedDayOfWeek: Int = 0,
    val selectedMonthCalendarInfoList: List<Schedule> = emptyList(),
    val selectedDateBriefScheduleInfoList: List<BriefSchedule> = emptyList()
)