package com.whereareyou.ui.home.main.detailschedule

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.BuildConfig
import com.whereareyou.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyou.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScheduleViewModel @Inject constructor(
    application: Application,
    private val getDetailScheduleUseCase: GetDetailScheduleUseCase
) : AndroidViewModel(application) {


    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title
    private val _start = MutableStateFlow("")
    val start: StateFlow<String> = _start
    private val _end = MutableStateFlow("")
    val end: StateFlow<String> = _end
    private val _memo = MutableStateFlow("")
    val memo: StateFlow<String> = _memo

    fun updateScheduleId(id: String?) {
        if (id == null) {
            return
        } else {
            getDetailSchedule(id)
        }
    }

    fun getDetailSchedule(id: String) {
        viewModelScope.launch {
            val getDetailScheduleResult = getDetailScheduleUseCase(
                BuildConfig.ACCESS_TOKEN,
                BuildConfig.MEMBER_ID,
                id
            )
            when (getDetailScheduleResult) {
                is NetworkResult.Success -> {
                    Log.e("success", "${getDetailScheduleResult.data}")
                    val detailSchedule = getDetailScheduleResult.data
                    _title.update { detailSchedule.title }
                    _start.update { detailSchedule.start }
                    _end.update { detailSchedule.end }
                    _memo.update { detailSchedule.memo }
                }
                is NetworkResult.Error -> { Log.e("error", "${getDetailScheduleResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
        }
    }
}