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
    private val _currentMonthDateInfo = mutableStateListOf<Schedule>()
    val currentMonthDateInfo: List<Schedule> = _currentMonthDateInfo

    // 일별 일정 간략 정보
    private val _currentDateBriefSchedule = mutableStateListOf<BriefSchedule>()
    val currentDateBriefSchedule: List<BriefSchedule> = _currentDateBriefSchedule

    // 이번달 달력의 정보를 먼저 가져온 후 이번달 달력의 일정 수를 가져온다
    private fun updateCurrentMonthDateInfo() {
        // 현재 달의 달력 정보를 가져온다. [2023/10/1, 2023/10/2, 2023/10/3,...]
        viewModelScope.launch(Dispatchers.IO) {
            val calendarArrList = CalendarUtil.getCalendarInfo(_year.value, _month.value)
            var monthlySchedule = emptyList<ScheduleCountByDay>()
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            var currYear: Int
            var currMonth = -1
            var getMonthlyScheduleResult: NetworkResult<GetMonthlyScheduleResponse>
            for (i in calendarArrList.indices) {
                if (currMonth != calendarArrList[i].month) {
                    currYear = calendarArrList[i].year
                    currMonth = calendarArrList[i].month

                    getMonthlyScheduleResult = getMonthlyScheduleUseCase(
                        accessToken, memberId, currYear, currMonth
                    )
                    when (getMonthlyScheduleResult) {
                        is NetworkResult.Success -> {
                            if (getMonthlyScheduleResult.data != null) {
                                monthlySchedule = getMonthlyScheduleResult.data!!.schedules
                            }
                        }
                        is NetworkResult.Error -> { Log.e("error", "${getMonthlyScheduleResult.code}, ${getMonthlyScheduleResult.errorData}") }
                        is NetworkResult.Exception -> { Log.e("exception", "exception") }
                    }
                }
                calendarArrList[i].scheduleCount = monthlySchedule[calendarArrList[i].date - 1].scheduleCount
            }
            _currentMonthDateInfo.clear()
            _currentMonthDateInfo.addAll(calendarArrList)
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
        viewModelScope.launch {
            _currentDateBriefSchedule.clear()
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val getDailyBriefScheduleResult = getDailyBriefScheduleUseCase(
                accessToken, memberId, _year.value, _month.value, _date.value
            )
            when (getDailyBriefScheduleResult) {
                is NetworkResult.Success -> {
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