package com.whereareyounow.ui.main.schedule.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.whereareyounow.data.calendar.CalendarScreenSideEffect
import com.whereareyounow.data.calendar.CalendarScreenUIState
import com.whereareyounow.util.getDayOfWeek
import com.whereareyounow.util.getTodayInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val application: Application,
//    private val getMonthlyScheduleUseCase: GetMonthlyScheduleUseCase,
//    private val getDailyBriefScheduleUseCase: GetDailyBriefScheduleUseCase,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase
) : AndroidViewModel(application) {

    private val _calendarScreenUIState = MutableStateFlow(CalendarScreenUIState())
    val calendarScreenUIState = _calendarScreenUIState.asStateFlow()
    val calendarScreenSideEffectFlow = MutableSharedFlow<CalendarScreenSideEffect>()

    fun updateYear(year: Int) {
        _calendarScreenUIState.update {
            it.copy(selectedYear = year)
        }
    }

    fun updateMonth(month: Int) {
        _calendarScreenUIState.update {
            it.copy(selectedMonth = month)
        }
    }

    fun updateDate(date: Int) {
        _calendarScreenUIState.update {
            it.copy(selectedDate = date)
        }
        updateCurrentDateBriefScheduleInfo()
    }

    // 이번달 달력의 정보를 먼저 가져온 후 이번달 달력의 일정 수를 가져온다.
    fun updateCurrentMonthCalendarInfo() {
//        // 현재 달의 달력 정보를 가져온다. [2023/10/1/0, 2023/10/2/0, 2023/10/3/0,...]
//        val calendarList = getCalendarInfo(_calendarScreenUIState.value.selectedYear, _calendarScreenUIState.value.selectedMonth).toMutableList()
//
//        // 빈 달력을 먼저 보여주기 위해 먼저 리스트를 초기화한다.
//        _calendarScreenUIState.update {
//            it.copy(selectedMonthCalendarInfoList = calendarList.toList())
//        }
//
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            var currYear: Int
//            var currMonth = -1
//            for (i in calendarList.indices) {
//                if (currMonth != calendarList[i].month) {
//                    // 월이 바뀔 경우 다음 월의 일정 정보를 가져온다.
//                    currYear = calendarList[i].year
//                    currMonth = calendarList[i].month
//                    val response = getMonthlyScheduleUseCase(accessToken, memberId, currYear, currMonth)
//                    LogUtil.printNetworkLog("memberId = $memberId\ncurrYear = $currYear\ncurrMonth = $currMonth", response, "월별 일정 정보")
//                    when (response) {
//                        is NetworkResult.Success -> {
//                            response.data?.let { data ->
//                                // 가져온 스케줄과 달력을 비교해 연/월/일이 같으면 일정 개수 정보를 추가한다.
//                                for (idx in calendarList.indices) {
//                                    if (calendarList[idx].year == _calendarScreenUIState.value.selectedYear && calendarList[idx].month == currMonth) {
//                                        if (calendarList[idx].scheduleCount != data.schedules[calendarList[idx].date - 1].scheduleCount) {
//                                            calendarList[idx] = calendarList[idx].copy(scheduleCount = data.schedules[calendarList[idx].date - 1].scheduleCount)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        is NetworkResult.Error -> {
//
//                        }
//                        is NetworkResult.Exception -> { calendarScreenSideEffectFlow.emit(CalendarScreenSideEffect.Toast("오류가 발생했습니다.")) }
//                    }
//                }
//            }
//            _calendarScreenUIState.update {
//                it.copy(selectedMonthCalendarInfoList = calendarList.toList())
//            }
//        }
    }

    fun updateCurrentDateBriefScheduleInfo() {
        _calendarScreenUIState.update {
            it.copy(
                selectedDayOfWeek = getDayOfWeek(
                    _calendarScreenUIState.value.selectedYear,
                    _calendarScreenUIState.value.selectedMonth,
                    _calendarScreenUIState.value.selectedDate
                )
            )
        }
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val response = getDailyBriefScheduleUseCase(
//                accessToken,
//                memberId,
//                _calendarScreenUIState.value.selectedYear,
//                _calendarScreenUIState.value.selectedMonth,
//                _calendarScreenUIState.value.selectedDate
//            )
//            LogUtil.printNetworkLog("memberId = $memberId\nselectedYear = ${_calendarScreenUIState.value.selectedYear}\nselectedMonth = ${_calendarScreenUIState.value.selectedMonth}\nselectedDate = ${_calendarScreenUIState.value.selectedDate}", response, "일별 간략정보 가져오기")
//            when (response) {
//                is NetworkResult.Success -> {  }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> { calendarScreenSideEffectFlow.emit(CalendarScreenSideEffect.Toast("오류가 발생했습니다.")) }
//            }
//        }
    }

    // 처음 화면에 보여지는 날짜에 대한 달력 정보를 모두 설정한다.
    private fun initCalendarInfo() {
        val todayInfo = getTodayInfo()
        _calendarScreenUIState.update {
            it.copy(
                selectedYear = todayInfo[0],
                selectedMonth = todayInfo[1],
                selectedDate = todayInfo[2],
                selectedDayOfWeek = todayInfo[3]
            )
        }
    }

    init {
        initCalendarInfo()
    }
}