package com.whereareyounow.ui.main.schedule.detailschedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.detailschedule.DetailScheduleMapScreenSideEffect
import com.whereareyounow.data.detailschedule.DetailScheduleMapScreenUIState
import com.whereareyounow.domain.request.schedule.GetDetailScheduleRequest
import com.whereareyounow.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.util.LocationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailScheduleMapViewModel @Inject constructor(
    private val application: Application,
    private val getDetailScheduleUseCase: GetDetailScheduleUseCase,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val getUserLocationUseCase: GetUserLocationUseCase,
//    private val sendUserLocationUseCase: SendUserLocationUseCase,
//    private val checkArrivalUseCase: CheckArrivalUseCase,
    private val locationUtil: LocationUtil,
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(DetailScheduleMapScreenUIState())
    val uiState = _uiState.asStateFlow()
    val detailScheduleMapScreenSideEffect = MutableSharedFlow<DetailScheduleMapScreenSideEffect>()

    fun updateScheduleInfo(
        scheduleSeq: Int
    ) {
        val requestData = GetDetailScheduleRequest(
            scheduleSeq = scheduleSeq,
            memberSeq = AuthData.memberSeq
        )
        getDetailScheduleUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                x = data.x,
                                y = data.y,
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
        getUsersLocation()
    }

    private fun getUsersLocation() {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            // 유저의 위치를 가져온다.
//            val request = com.whereareyounow.domain.request.location.GetUserLocationRequest(
//                _detailScheduleMapScreenUIState.value.memberInfosList.map { it.memberId },
//                scheduleId
//            )
//            val response = getUserLocationUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "유저 위치 가져오기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    response.data?.let { data ->
//                        _detailScheduleMapScreenUIState.update { state ->
//                            val prevList = state.copy().memberInfosList.toMutableList()
//                            state.copy(
//                                memberInfosList = prevList.map { info ->
//                                    if (data.any { it.memberId == info.memberId })
//                                        info.copy(
//                                            latitude = data.filter { it.memberId == info.memberId }[0].latitude,
//                                            longitude = data.filter { it.memberId == info.memberId }[0].longitude
//                                        )
//                                    else info
//                                }
//                            )
//                        }
//                    }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> { detailScheduleMapScreenSideEffect.emit(DetailScheduleMapScreenSideEffect.Toast("오류가 발생했습니다.")) }
//            }
//        }
    }

    fun sendUserLocation() {
//        locationUtil.getCurrentLocation {
//            val request = com.whereareyounow.domain.request.location.SendUserLocationRequest(
//                memberId,
//                it.latitude,
//                it.longitude
//            )
//            val response = sendUserLocationUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "유저 위치 전송하기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    getUsersLocation()
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> { detailScheduleMapScreenSideEffect.emit(DetailScheduleMapScreenSideEffect.Toast("오류가 발생했습니다.")) }
//            }
//            Log.e("locationDiff", "${sqrt((it.latitude - _detailScheduleMapScreenUIState.value.destinationLatitude).pow(2.0) + (it.longitude - _detailScheduleMapScreenUIState.value.destinationLongitude).pow(2.0))}")
//            if (sqrt((it.latitude - _detailScheduleMapScreenUIState.value.destinationLatitude).pow(2.0) + (it.longitude - _detailScheduleMapScreenUIState.value.destinationLongitude).pow(2.0)) < 0.00335) {
//                checkArrival()
//            }
//        }
    }

    fun stopUpdateLocation() {
        locationUtil.stopUpdateLocation()
    }

    private suspend fun checkArrival() {
//        val accessToken = getAccessTokenUseCase().first()
//        val memberId = getMemberIdUseCase().first()
//        val request =
//            com.whereareyounow.domain.request.schedule.CheckArrivalRequest(memberId, scheduleId)
//        val response = checkArrivalUseCase(accessToken, request)
//        LogUtil.printNetworkLog(request, response, "도착 여부")
//        when (response) {
//            is NetworkResult.Success -> {
//
//            }
//            is NetworkResult.Error -> {  }
//            is NetworkResult.Exception -> { detailScheduleMapScreenSideEffect.emit(DetailScheduleMapScreenSideEffect.Toast("오류가 발생했습니다.")) }
//        }
    }

    init {
        locationUtil.getCurrentLocation {
//            val request = com.whereareyounow.domain.request.location.SendUserLocationRequest(
//                memberId,
//                it.latitude,
//                it.longitude
//            )
        }
    }
}