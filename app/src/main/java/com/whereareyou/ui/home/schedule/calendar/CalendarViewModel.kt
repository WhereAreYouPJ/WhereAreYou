package com.whereareyou.ui.home.schedule.calendar

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.data.Schedule
import com.whereareyou.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyou.domain.entity.schedule.BriefSchedule
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.domain.entity.schedule.ScheduleCountByDay
import com.whereareyou.domain.usecase.schedule.GetDailyBriefScheduleUseCase
import com.whereareyou.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.util.CalendarUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    application: Application,
    private val getMonthlyScheduleUseCase: GetMonthlyScheduleUseCase,
    private val getDailyBriefScheduleUseCase: GetDailyBriefScheduleUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase
) : AndroidViewModel(application) {

    // 현재 선택된 년도
    private val _year = MutableStateFlow(0)
    val year: StateFlow<Int> = _year
    // 현재 선택된 월
    private val _month = MutableStateFlow(0)
    val month: StateFlow<Int> = _month
    // 현재 선택된 일
    private val _date = MutableStateFlow(0)
    val date: StateFlow<Int> = _date
    // 현재 선택된 요일
    private val _dayOfWeek = MutableStateFlow(0)
    val dayOfWeek: StateFlow<Int> = _dayOfWeek
    // 보여지고 있는 달력 상태 (년도 or 월 or 일)
    private val _calendarState = MutableStateFlow(CalendarState.DATE)
    val calendarState: StateFlow<CalendarState> = _calendarState
    // 선택된 월의 (년/월/일/일정 개수)정보를 담는 리스트
    private val _currentMonthDateInfo = mutableStateListOf<Schedule>()
    val currentMonthDateInfo: List<Schedule> = _currentMonthDateInfo
    // 일별 일정 간략 정보 리스트
    private val _currentDateBriefSchedule = mutableStateListOf<BriefSchedule>()
    val currentDateBriefSchedule: List<BriefSchedule> = _currentDateBriefSchedule

    // 이번달 달력의 정보를 먼저 가져온 후 이번달 달력의 일정 수를 가져온다.
    private fun updateCurrentMonthDateInfo() {
        // 현재 달의 달력 정보를 가져온다. [2023/10/1, 2023/10/2, 2023/10/3,...]
        val calendarArrList = CalendarUtil.getCalendarInfo(_year.value, _month.value)
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            var currYear: Int
            var currMonth = -1
            for (i in calendarArrList.indices) {
                if (currMonth != calendarArrList[i].month) {
                    // 월이 바뀔 경우 다음 월의 일정 정보를 가져온다.
                    currYear = calendarArrList[i].year
                    currMonth = calendarArrList[i].month
                    when (val networkResponse = getMonthlyScheduleUseCase(accessToken, memberId, currYear, currMonth)) {
                        is NetworkResult.Success -> {
                            networkResponse.data?.let { data ->
                                for (schedule in data.schedules) {
                                    for (calendarInfo in calendarArrList) {
                                        if (calendarInfo.year == _year.value &&
                                            calendarInfo.month == currMonth &&
                                            calendarInfo.date == schedule.date
                                        ) {
                                            calendarInfo.scheduleCount = schedule.scheduleCount
                                        }
                                    }
                                }
                            }
                        }
                        is NetworkResult.Error -> { Log.e("error", "${networkResponse.code}, ${networkResponse.errorData}") }
                        is NetworkResult.Exception -> { Log.e("exception", "exception") }
                    }
                }
            }
            withContext(Dispatchers.Main) {
                _currentMonthDateInfo.clear()
                _currentMonthDateInfo.addAll(calendarArrList)
            }
            Log.e("MonthlySchedule", calendarArrList.toString())
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
        _dayOfWeek.update { CalendarUtil.getDayOfWeek(_year.value, _month.value, _date.value) }
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val getDailyBriefScheduleResult = getDailyBriefScheduleUseCase(
                accessToken, memberId, _year.value, _month.value, _date.value
            )
            when (getDailyBriefScheduleResult) {
                is NetworkResult.Success -> {
                    _currentDateBriefSchedule.clear()
                    _currentDateBriefSchedule.addAll(getDailyBriefScheduleResult.data!!.schedules)
                }
                is NetworkResult.Error -> { Log.e("error", "${getDailyBriefScheduleResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
        }
    }

    fun updateCalendarState(state: CalendarState) {
        _calendarState.update { state }
    }

    // 처음 화면에 보여지는 날짜
    private fun initCalendarInfo() {
        val todayInfo = CalendarUtil.getTodayInfo()
        _year.update { todayInfo[0] }
        _month.update { todayInfo[1] }
        _date.update { todayInfo[2] }
        _dayOfWeek.update { todayInfo[3] }
        updateCurrentMonthDateInfo()
        updateDate(todayInfo[2])
    }

    enum class CalendarState {
        YEAR, MONTH, DATE
    }

    init {
        initCalendarInfo()
    }
}