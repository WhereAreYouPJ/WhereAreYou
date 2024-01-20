package com.whereareyounow.ui.home.schedule.calendar

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.Schedule
import com.whereareyounow.domain.entity.schedule.BriefSchedule
import com.whereareyounow.domain.usecase.schedule.GetDailyBriefScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.LogUtil
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
    private val application: Application,
    private val getMonthlyScheduleUseCase: GetMonthlyScheduleUseCase,
    private val getDailyBriefScheduleUseCase: GetDailyBriefScheduleUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase
) : AndroidViewModel(application) {

    private val _selectedYear = MutableStateFlow(0)
    val selectedYear: StateFlow<Int> = _selectedYear
    private val _selectedMonth = MutableStateFlow(0)
    val selectedMonth: StateFlow<Int> = _selectedMonth
    private val _selectedDate = MutableStateFlow(0)
    val selectedDate: StateFlow<Int> = _selectedDate
    private val _dayOfWeek = MutableStateFlow(0)
    val dayOfWeek: StateFlow<Int> = _dayOfWeek
    private val _calendarState = MutableStateFlow(CalendarState.DATE)
    val calendarState: StateFlow<CalendarState> = _calendarState
    private val _currentMonthCalendarInfoList = mutableStateListOf<Schedule>()
    val currentMonthCalendarInfoList: List<Schedule> = _currentMonthCalendarInfoList
    private val _currentDateBriefScheduleInfoList = mutableStateListOf<BriefSchedule>()
    val currentDateBriefScheduleInfoList: List<BriefSchedule> = _currentDateBriefScheduleInfoList

    // 이번달 달력의 정보를 먼저 가져온 후 이번달 달력의 일정 수를 가져온다.
    fun updateCurrentMonthCalendarInfo() {
        // 현재 달의 달력 정보를 가져온다. [2023/10/1/0, 2023/10/2/0, 2023/10/3/0,...]
        val calendarArrList = CalendarUtil.getCalendarInfo(_selectedYear.value, _selectedMonth.value)

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
                    val response = getMonthlyScheduleUseCase(accessToken, memberId, currYear, currMonth)
                    LogUtil.printNetworkLog(response, "월별 일정 정보")
                    when (response) {
                        is NetworkResult.Success -> {
                            response.data?.let { data ->
                                // 가져온 스케줄과 달력을 비교해 연/월/일이 같으면 일정 개수 정보를 추가한다.
                                for (schedule in data.schedules) {
                                    for (calendarInfo in calendarArrList) {
                                        if (calendarInfo.year == _selectedYear.value &&
                                            calendarInfo.month == currMonth &&
                                            calendarInfo.date == schedule.date
                                        ) {
                                            calendarInfo.scheduleCount = schedule.scheduleCount
                                            break
                                        }
                                    }
                                }
                            }
                        }
                        is NetworkResult.Error -> {

                        }
                        is NetworkResult.Exception -> {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            withContext(Dispatchers.Main) {
                Log.e("", "${calendarArrList}")
                _currentMonthCalendarInfoList.clear()
                _currentMonthCalendarInfoList.addAll(calendarArrList)
            }
        }
    }

    fun updateYear(year: Int) {
        _selectedYear.update { year }
    }

    fun updateMonth(month: Int) {
        if (month != _selectedMonth.value) {
            _selectedMonth.update { month }
            updateCurrentMonthCalendarInfo()
        }
    }

    fun updateDate(date: Int) {
        if (date != _selectedDate.value) {
            _selectedDate.update { date }
            updateCurrentDateBriefScheduleInfo()
            updateCurrentMonthCalendarInfo()
        }
    }

    fun updateCurrentDateBriefScheduleInfo() {
        _dayOfWeek.update { CalendarUtil.getDayOfWeek(_selectedYear.value, _selectedMonth.value, _selectedDate.value) }
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val response = getDailyBriefScheduleUseCase(accessToken, memberId, _selectedYear.value, _selectedMonth.value, _selectedDate.value)
            LogUtil.printNetworkLog(response, "일별 간략정보 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        withContext(Dispatchers.Main) {
                            _currentDateBriefScheduleInfoList.clear()
                            _currentDateBriefScheduleInfoList.addAll(data.schedules)
                        }
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
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
        _selectedYear.update { todayInfo[0] }
        _selectedMonth.update { todayInfo[1] }
        _selectedDate.update { todayInfo[2] }
        _dayOfWeek.update { todayInfo[3] }
    }

    enum class CalendarState {
        YEAR, MONTH, DATE
    }

    init {
        initCalendarInfo()
    }
}