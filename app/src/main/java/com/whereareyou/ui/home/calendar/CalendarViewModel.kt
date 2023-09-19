package com.whereareyou.ui.home.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.whereareyou.util.CalendarUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _year = MutableStateFlow(0)
    val year: StateFlow<Int> = _year
    private val _month = MutableStateFlow(0)
    val month: StateFlow<Int> = _month
    private val _date = MutableStateFlow(0)
    val date: StateFlow<Int> = _date

    private val _calendarState = MutableStateFlow(CalendarState.DATE)
    val calendarState: StateFlow<CalendarState> = _calendarState

    private val _currentMonthDateInfo = MutableStateFlow<List<Int>>(emptyList())
    val currentMonthDateInfo = _currentMonthDateInfo.asStateFlow()



    private fun updateCurrentMonthDateInfo() {
        _currentMonthDateInfo.update {
            CalendarUtil.getCalendarInfo(_year.value, _month.value).toList()
        }
    }

    fun updateYear(year: Int) {
        _year.update { year }
        updateCurrentMonthDateInfo()
    }

    fun updateMonth(month: Int) {
        _month.update { month }
        updateCurrentMonthDateInfo()
    }

    fun updateDate(date: Int) {
        _date.update { date }
    }

    fun updateCalendarState(state: CalendarState) {
        _calendarState.update { state }
    }

    private fun initCalendarInfo() {
        val todayInfo = CalendarUtil.getTodayInfo()
        _year.update { todayInfo[0] }
        _month.update { todayInfo[1] }
        _date.update { todayInfo[2] }
    }

    enum class CalendarState {
        YEAR, MONTH, DATE
    }

    init {
        initCalendarInfo()
        updateCurrentMonthDateInfo()
    }
}