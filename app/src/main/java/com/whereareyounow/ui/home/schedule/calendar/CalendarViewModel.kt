package com.whereareyounow.ui.home.schedule.calendar

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.Schedule
import com.whereareyounow.domain.entity.schedule.BriefSchedule
import com.whereareyounow.domain.usecase.schedule.GetDailyBriefScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.CalendarUtil
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
    // 선택된 월의 달력(년/월/일/일정 개수)정보 리스트
    private val _currentMonthCalendarInfoList = mutableStateListOf<Schedule>()
    val currentMonthCalendarInfoList: List<Schedule> = _currentMonthCalendarInfoList
    // 선택된 날짜의 일별 일정 간략 정보 리스트
    private val _currentDateBriefScheduleInfoList = mutableStateListOf<BriefSchedule>()
    val currentDateBriefScheduleInfoList: List<BriefSchedule> = _currentDateBriefScheduleInfoList

    // 이번달 달력의 정보를 먼저 가져온 후 이번달 달력의 일정 수를 가져온다.
    fun updateCurrentMonthCalendarInfo() {
        // 현재 달의 달력 정보를 가져온다. [2023/10/1/0, 2023/10/2/0, 2023/10/3/0,...]
        val calendarArrList = CalendarUtil.getCalendarInfo(_year.value, _month.value)

        // 빈 달력을 먼저 보여주기 위해 일단 리스트를 초기화한다.
        _currentMonthCalendarInfoList.clear()
        _currentMonthCalendarInfoList.addAll(calendarArrList)


        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            var currYear: Int
            var currMonth = -1
            for (i in calendarArrList.indices) {
                if (currMonth != calendarArrList[i].month) {
                    // 월이 바뀔 경우 다음 월의 일정 정보를 가져온다.
                    currYear = calendarArrList[i].year
                    currMonth = calendarArrList[i].month
                    when (val getMonthlyScheduleResponse = getMonthlyScheduleUseCase(accessToken, memberId, currYear, currMonth)) {
                        is NetworkResult.Success -> {
                            Log.e("success", "${getMonthlyScheduleResponse.code}, ${getMonthlyScheduleResponse.data}")
                            getMonthlyScheduleResponse.data?.let { data ->
                                // 가져온 스케줄과 달력을 비교해 연/월/일이 같으면 일정 개수 정보를 추가한다.
                                for (schedule in data.schedules) {
                                    for (calendarInfo in calendarArrList) {
                                        if (calendarInfo.year == _year.value &&
                                            calendarInfo.month == currMonth &&
                                            calendarInfo.date == schedule.date
                                        ) {
                                            calendarInfo.scheduleCount = schedule.scheduleCount
                                            break
                                        }
                                    }
                                }
                            } ?: Log.e("success-error", "body is null")
                        }
                        is NetworkResult.Error -> { Log.e("error", "${getMonthlyScheduleResponse.code}, ${getMonthlyScheduleResponse.errorData}") }
                        is NetworkResult.Exception -> { Log.e("exception", "${getMonthlyScheduleResponse.e}") }
                    }
                }
            }
            withContext(Dispatchers.Main) {
                _currentMonthCalendarInfoList.clear()
                _currentMonthCalendarInfoList.addAll(calendarArrList)
            }
        }
    }

    fun updateYear(year: Int) {
        _year.update { year }
    }

    fun updateMonth(month: Int) {
        if (month != _month.value) {
            _month.update { month }
            updateCurrentMonthCalendarInfo()
        }
    }

    fun updateDate(date: Int) {
        if (date != _date.value) {
            _date.update { date }
            updateCurrentDateBriefScheduleInfo()
        }
    }

    private fun updateCurrentDateBriefScheduleInfo() {
        _dayOfWeek.update { CalendarUtil.getDayOfWeek(_year.value, _month.value, _date.value) }
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            when (val getDailyBriefScheduleResponse = getDailyBriefScheduleUseCase(accessToken, memberId, _year.value, _month.value, _date.value)) {
                is NetworkResult.Success -> {
                    Log.e("success", "${getDailyBriefScheduleResponse.code}, ${getDailyBriefScheduleResponse.data}")
                    getDailyBriefScheduleResponse.data?.let { data ->
                        withContext(Dispatchers.Main) {
                            _currentDateBriefScheduleInfoList.clear()
                            _currentDateBriefScheduleInfoList.addAll(data.schedules)
                        }
                    } ?: Log.e("success-error", "body is null")
                }
                is NetworkResult.Error -> { Log.e("error", "${getDailyBriefScheduleResponse.code}, ${getDailyBriefScheduleResponse.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "${getDailyBriefScheduleResponse.e}") }
            }
        }
    }

    // 연도 선택/월 선택/일 선택 달력으로 변경한다.
    fun updateCalendarState(state: CalendarState) {
        _calendarState.update { state }
    }

    // 처음 화면에 보여지는 날짜에 대한 달력 정보를 모두 설정한다.
    private fun initCalendarInfo() {
        val todayInfo = CalendarUtil.getTodayInfo()
        _year.update { todayInfo[0] }
        _month.update { todayInfo[1] }
        _date.update { todayInfo[2] }
        _dayOfWeek.update { todayInfo[3] }
        updateCurrentMonthCalendarInfo()
        updateDate(todayInfo[2])
    }

    enum class CalendarState {
        YEAR, MONTH, DATE
    }

    init {
        initCalendarInfo()
    }
}