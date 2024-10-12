package com.whereareyounow.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.home.HomeScreenData
import com.whereareyounow.domain.request.home.GetHomeImageListRequest
import com.whereareyounow.domain.request.schedule.GetDailyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetScheduleDDayRequest
import com.whereareyounow.domain.usecase.home.GetHomeImageListUseCase
import com.whereareyounow.domain.usecase.schedule.GetDailyScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetScheduleDDayUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeImageListUseCase: GetHomeImageListUseCase,
    private val getDailyScheduleUseCase: GetDailyScheduleUseCase,
    private val getScheduleDDayUseCase: GetScheduleDDayUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenData())
    val uiState = _uiState.asStateFlow()

    fun getBannerImageList() {
        val requestData = GetHomeImageListRequest(
            memberSeq = AuthData.memberSeq
        )
        getHomeImageListUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                imageList = data.map { it.imageUrl }
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun getDailyScheduleInfo() {
        val requestData = GetDailyScheduleRequest(
            date = LocalDate.now().toString(),
            memberSeq = AuthData.memberSeq
        )
        getDailyScheduleUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                dailyScheduleList = data
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun getScheduleDday() {
        val requestData = GetScheduleDDayRequest(
            memberSeq = AuthData.memberSeq
        )
        getScheduleDDayUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                dDayScheduleList = data
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    init {
        getBannerImageList()
        getDailyScheduleInfo()
        getScheduleDday()
    }
}