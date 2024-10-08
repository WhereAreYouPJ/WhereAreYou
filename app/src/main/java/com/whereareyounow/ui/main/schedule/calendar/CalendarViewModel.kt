package com.whereareyounow.ui.main.schedule.calendar

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.calendar.CalendarScreenSideEffect
import com.whereareyounow.data.calendar.CalendarScreenUIState
import com.whereareyounow.domain.entity.schedule.DailyScheduleInfo
import com.whereareyounow.domain.entity.schedule.MonthlySchedule
import com.whereareyounow.domain.request.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDailyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetMonthlyScheduleRequest
import com.whereareyounow.domain.usecase.schedule.DeleteScheduleByCreatorUseCase
import com.whereareyounow.domain.usecase.schedule.GetDailyScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetMonthlyScheduleUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.globalvalue.type.VisualType
import com.whereareyounow.util.calendar.compareDate
import com.whereareyounow.util.calendar.getCalendarInfo
import com.whereareyounow.util.calendar.getTodayInfo
import com.whereareyounow.util.calendar.parseLocalDate
import com.whereareyounow.util.calendar.parseLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val application: Application,
    private val getMonthlyScheduleUseCase: GetMonthlyScheduleUseCase,
    private val getDailyScheduleUseCase: GetDailyScheduleUseCase,
    private val deleteScheduleByCreatorUseCase: DeleteScheduleByCreatorUseCase,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(CalendarScreenUIState())
    val uiState = _uiState.asStateFlow()
    val calendarScreenSideEffectFlow = MutableSharedFlow<CalendarScreenSideEffect>()

    fun updateYear(year: Int) {
        _uiState.update {
            it.copy(selectedYear = year)
        }
    }

    fun updateMonth(year: Int, month: Int) {
        if (_uiState.value.selectedYear != year || _uiState.value.selectedMonth != month) {
            _uiState.update {
                it.copy(selectedMonth = month)
            }
            updateCurrentMonthCalendarInfo()
            getDailySchedule()
        }
    }

    fun updateDate(year: Int, month: Int, date: Int) {
        if (_uiState.value.selectedYear != year || _uiState.value.selectedMonth != month || _uiState.value.selectedDate != date) {
            _uiState.update {
                it.copy(selectedDate = date)
            }
            getDailySchedule()
        }
    }

    // 이번달 달력의 정보를 먼저 가져온 후 이번달 달력의 일정 수를 가져온다.
    @SuppressLint("DefaultLocale")
    fun updateCurrentMonthCalendarInfo() {
        // 현재 달의 달력 정보를 가져온다. [2023/10/1/0, 2023/10/2/0, 2023/10/3/0,...]
        val dateList = getCalendarInfo(_uiState.value.selectedYear, _uiState.value.selectedMonth)

        // 빈 달력을 먼저 보여주기 위해 먼저 리스트를 초기화한다.
        _uiState.update { it ->
            it.copy(
                selectedMonthCalendarInfoMap = dateList.associateBy(keySelector = { it.toString() }, valueTransform = { mutableListOf(null to VisualType.One, null to VisualType.One, null to VisualType.One, null to VisualType.One) }),
                dateList = dateList
            )
        }
        LogUtil.e("", "${_uiState.value.selectedMonthCalendarInfoMap}")
        LogUtil.e("", "${_uiState.value.dateList}")
        var allScheduleList = mutableListOf<MonthlySchedule>()
        val monthList = listOf(dateList[15].minusMonths(1), dateList[15], dateList[15].plusMonths(1))
        viewModelScope.launch {
            monthList.forEach {
                val requestData = GetMonthlyScheduleRequest(
                    yearMonth = "${it.year}-${String.format("%02d", it.monthValue)}",
                    memberSeq = AuthData.memberSeq
                )
                getMonthlyScheduleUseCase(requestData)
                    .onEach { networkResult ->
                        networkResult.onSuccess { code, message, data ->
                            data?.let {
                                allScheduleList.addAll(data)
                            }
                        }.onError { code, message ->

                        }.onException {  }
                    }
                    .catch { LogUtil.e("flow error", "getMonthlyScheduleUseCase\n${it.message}") }
                    .launchIn(viewModelScope)
                    .join()
            }
            allScheduleList = allScheduleList.sortedWith(
                compareBy(
                    { compareDate(parseLocalDateTime(it.startTime), parseLocalDateTime(it.endTime)) },
                    { !it.allDay },
                    { parseLocalDateTime(it.startTime) }
                )
            ).distinctBy { it.scheduleSeq }.toMutableList()
            LogUtil.e("allScheduleList", "${allScheduleList}")


            // 1번 기준: 이틀 이상 지속되는 일정 우선 2번 기준: 시작 일자가 빠른 일정 우선
//        val allScheduleList = CalendarMockData.monthlyScheduleList.sortedWith(
//            compareBy(
//                { compareDate(parseLocalDateTime(it.startTime), parseLocalDateTime(it.endTime)) },
//                { parseLocalDateTime(it.startTime) }
//            )
//        )
            LogUtil.e("allScheduleList", "${allScheduleList}")

            val scheduleMap: MutableMap<String, MutableList<Pair<MonthlySchedule?, VisualType>>> = mutableMapOf()
            for (localDate in dateList) {
                scheduleMap[localDate.toString()] = MutableList(10) { null to VisualType.One }
            }
            for (schedule in allScheduleList) {
                val originStartDate = parseLocalDate(schedule.startTime)
                var startDate = parseLocalDate(schedule.startTime)
                val endDate = parseLocalDate(schedule.endTime)

                var flag = false
                while (!scheduleMap.containsKey(startDate.toString())) {
                    startDate = startDate.plusDays(1)
                    if (startDate.isAfter(endDate)) {
                        flag = false
                        break
                    }
                }
                if (flag) continue

                var startIdx = 0
                if (!scheduleMap.containsKey(startDate.toString())) continue
                for (idx in scheduleMap[startDate.toString()]!!.indices) {
                    if (scheduleMap[startDate.toString()]!![idx].first == null) {
                        startIdx = idx
                        break
                    }
                }
                while (!startDate.isAfter(endDate)) {
                    if (!scheduleMap.containsKey(startDate.toString())) break
                    scheduleMap[startDate.toString()]!![startIdx] = scheduleMap[startDate.toString()]!![startIdx].copy(first = schedule)
                    if (!compareDate(originStartDate, endDate)) {
                        scheduleMap[startDate.toString()]!![startIdx] = scheduleMap[startDate.toString()]!![startIdx].copy(
                            second = if (compareDate(originStartDate, startDate)) VisualType.Start
                            else if (compareDate(endDate, startDate)) VisualType.End
                            else VisualType.Mid
                        )
                    }
                    startDate = startDate.plusDays(1)
                }
            }
//            LogUtil.e("scheduleMap", "${scheduleMap.toString().replace(", 2024", ",\n2024")}")
            _uiState.update {
                it.copy(
                    selectedMonthCalendarInfoMap = scheduleMap.toMap(),
                    dateList = dateList
                )
            }
            LogUtil.e("updatedMonthCalendarInfo", _uiState.value.selectedMonthCalendarInfoMap.toString())
        }
    }

    private fun getDailySchedule() {
        val requestData = GetDailyScheduleRequest(
            date = "${_uiState.value.selectedYear}-${String.format("%02d",_uiState.value.selectedMonth)}-${String.format("%02d", _uiState.value.selectedDate)}",
            AuthData.memberSeq
        )
        var allScheduleList = mutableListOf<DailyScheduleInfo>()
        getDailyScheduleUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            allScheduleList = data.sortedWith(
                                compareBy(
                                    { compareDate(parseLocalDateTime(it.startTime), parseLocalDateTime(it.endTime)) },
                                    { !it.allDay },
                                    { parseLocalDateTime(it.startTime) }
                                )
                            ).toMutableList()
                            it.copy(
                                selectedDateDailyScheduleInfoList = allScheduleList
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun deleteSchedule(scheduleSeq: Int) {
        val requestData = DeleteScheduleRequest(
            scheduleSeq = scheduleSeq,
            memberSeq = AuthData.memberSeq
        )
        deleteScheduleByCreatorUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    getDailySchedule()
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    init {
        _uiState.update {
            val today = LocalDate.now()
            it.copy(
                selectedYear = today.year,
                selectedMonth = today.monthValue,
                selectedDate = today.dayOfMonth
            )
        }
        getDailySchedule()
    }
}

