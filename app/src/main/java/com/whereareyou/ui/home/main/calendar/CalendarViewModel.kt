package com.whereareyou.ui.home.main.calendar

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.BuildConfig
import com.whereareyou.domain.entity.schedule.BriefSchedule
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.domain.entity.schedule.ScheduleCountByDay
import com.whereareyou.domain.usecase.schedule.GetDailyBriefScheduleUseCase
import com.whereareyou.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.util.CalendarUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    application: Application,
    private val getMonthlyScheduleUseCase: GetMonthlyScheduleUseCase,
    private val getDailyBriefScheduleUseCase: GetDailyBriefScheduleUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase
) : AndroidViewModel(application) {

    private val _year = MutableStateFlow(0)
    val year: StateFlow<Int> = _year
    private val _month = MutableStateFlow(0)
    val month: StateFlow<Int> = _month
    private val _date = MutableStateFlow(0)
    val date: StateFlow<Int> = _date
    private val _calendarState = MutableStateFlow(CalendarState.DATE)
    val calendarState: StateFlow<CalendarState> = _calendarState

    // 2023/11/11/3: 년/월/일/일정 개수
    private val _currentMonthDateInfo = MutableStateFlow<List<String>>(emptyList())
    val currentMonthDateInfo: StateFlow<List<String>> = _currentMonthDateInfo

    // 일별 일정 간략 정보
    private val _currentDateBriefSchedule = MutableStateFlow<List<BriefSchedule>>(emptyList())
    val currentDateBriefSchedule: StateFlow<List<BriefSchedule>> = _currentDateBriefSchedule

    // 이번달 달력의 정보를 먼저 가져온 후 이번달 달력의 일정 수를 가져온다
    private fun updateCurrentMonthDateInfo() {
        // 현재 달의 달력 정보를 가져온다. [2023/10/1, 2023/10/2, 2023/10/3,...]
        val calendarArrList = CalendarUtil.getCalendarInfo(_year.value, _month.value)
        var monthlySchedule = emptyList<ScheduleCountByDay>()
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            var currYear = calendarArrList[0].split("/")[0].toInt()
            var currMonth = calendarArrList[0].split("/")[1].toInt()
            var getMonthlyScheduleResult = getMonthlyScheduleUseCase(
                accessToken, memberId, currYear, currMonth
            )
            for (i in 0 until calendarArrList.size) {
                if (currMonth != calendarArrList[i].split("/")[1].toInt()) {
                    currYear = calendarArrList[i].split("/")[0].toInt()
                    currMonth = calendarArrList[i].split("/")[1].toInt()

                    getMonthlyScheduleResult = getMonthlyScheduleUseCase(
                        accessToken, memberId, currYear, currMonth
                    )
                }
                when (getMonthlyScheduleResult) {
                    is NetworkResult.Success -> {
                        monthlySchedule = getMonthlyScheduleResult.data.schedules

                        calendarArrList[i] = calendarArrList[i] + "/" + monthlySchedule[calendarArrList[i].split("/")[2].toInt() - 1].scheduleCount
                    }
                    is NetworkResult.Error -> { Log.e("error", "${getMonthlyScheduleResult.code}, ${getMonthlyScheduleResult.errorData}") }
                    is NetworkResult.Exception -> { Log.e("exception", "exception") }
                }
            }
            _currentMonthDateInfo.update {
                calendarArrList.toList()
            }
            Log.e("MonthlySchedule", calendarArrList.toString())
        }
        _currentMonthDateInfo.update {
            calendarArrList.toList()
        }
    }

    fun updateYear(year: Int) {
        _year.update { year }
        updateCurrentMonthDateInfo()
    }

    fun updateMonth(month: Int) {
        if (month != _month.value) {
            _month.update { month }
            updateCurrentMonthDateInfo()
        }
    }

    fun updateDate(date: Int) {
        _date.update { date }
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val getDailyBriefScheduleResult = getDailyBriefScheduleUseCase(
                accessToken, memberId, _year.value, _month.value + 1, _date.value
            )
            when (getDailyBriefScheduleResult) {
                is NetworkResult.Success -> {
                    _currentDateBriefSchedule.update {
                        getDailyBriefScheduleResult.data.schedules
                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${getDailyBriefScheduleResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
        }
    }

    fun updateCalendarState(state: CalendarState) {
        _calendarState.update { state }
    }

    private fun initCalendarInfo() {
        val todayInfo = CalendarUtil.getTodayInfo()
        _year.update { todayInfo[0] }
        _month.update { todayInfo[1] - 1 }
        _date.update { todayInfo[2] }
        Log.e("todayInfo", todayInfo.toString())
    }

    enum class CalendarState {
        YEAR, MONTH, DATE
    }

    init {
        initCalendarInfo()
        updateCurrentMonthDateInfo()
    }
}