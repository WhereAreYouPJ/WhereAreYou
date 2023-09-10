package com.whereareyou.ui.home.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _calendarState = MutableStateFlow(CalendarState.DATE)
    val calendarState: StateFlow<CalendarState> = _calendarState

    fun updateCalendarState(state: CalendarState) {
        _calendarState.update { state }
    }

    enum class CalendarState {
        YEAR, MONTH, DATE
    }
}