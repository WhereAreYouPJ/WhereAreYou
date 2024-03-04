package com.whereareyounow.ui.home.schedule.detailschedule

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.detailschedule.DetailScheduleMapScreenSideEffect
import com.whereareyounow.data.detailschedule.DetailScheduleMapScreenUIState
import com.whereareyounow.data.detailschedule.MemberInfo
import com.whereareyounow.domain.entity.apimessage.location.GetUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyounow.domain.usecase.location.GetUserLocationUseCase
import com.whereareyounow.domain.usecase.location.SendUserLocationUseCase
import com.whereareyounow.domain.usecase.schedule.CheckArrivalUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.LocationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class DetailScheduleMapViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val sendUserLocationUseCase: SendUserLocationUseCase,
    private val checkArrivalUseCase: CheckArrivalUseCase,
    private val locationUtil: LocationUtil,
) : AndroidViewModel(application) {

    private lateinit var scheduleId: String
    private val _detailScheduleMapScreenUIState = MutableStateFlow(DetailScheduleMapScreenUIState())
    val detailScheduleMapScreenUIState = _detailScheduleMapScreenUIState.asStateFlow()
    val detailScheduleMapScreenSideEffect = MutableSharedFlow<DetailScheduleMapScreenSideEffect>()

    fun updateScheduleInfo(
        scheduleId: String,
        destinationLatitude: Double,
        destinationLongitude: Double,
        memberInfosList: List<MemberInfo>
    ) {
        this.scheduleId = scheduleId
        _detailScheduleMapScreenUIState.update { state ->
            state.copy(
                destinationLatitude = destinationLatitude,
                destinationLongitude = destinationLongitude,
                memberInfosList = memberInfosList
            )
        }
        getUsersLocation()
    }

    fun getUsersLocation() {
        viewModelScope.launch(Dispatchers.Default) {
            val memberId = getMemberIdUseCase().first()
            val accessToken = getAccessTokenUseCase().first()
            // 유저의 위치를 가져온다.
            val request = GetUserLocationRequest(_detailScheduleMapScreenUIState.value.memberInfosList.map { it.memberId }, scheduleId)
            val response = getUserLocationUseCase(accessToken, request)
            LogUtil.printNetworkLog(request, response, "유저 위치 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _detailScheduleMapScreenUIState.update { state ->
                            val prevList = state.copy().memberInfosList.toMutableList()
                            state.copy(
                                memberInfosList = prevList.map { info ->
                                    if (data.any { it.memberId == info.memberId })
                                        info.copy(
                                            latitude = data.filter { it.memberId == info.memberId }[0].latitude,
                                            longitude = data.filter { it.memberId == info.memberId }[0].longitude
                                        )
                                    else info
                                }
                            )
                        }
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { detailScheduleMapScreenSideEffect.emit(DetailScheduleMapScreenSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
    }

    fun sendUserLocation() {
        locationUtil.getCurrentLocation {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = SendUserLocationRequest(memberId, it.latitude, it.longitude)
            val response = sendUserLocationUseCase(accessToken, request)
            LogUtil.printNetworkLog(request, response, "유저 위치 전송하기")
            when (response) {
                is NetworkResult.Success -> {
                    getUsersLocation()
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { detailScheduleMapScreenSideEffect.emit(DetailScheduleMapScreenSideEffect.Toast("오류가 발생했습니다.")) }
            }
            Log.e("location", "${sqrt((it.latitude - _detailScheduleMapScreenUIState.value.destinationLatitude).pow(2.0) + (it.longitude - _detailScheduleMapScreenUIState.value.destinationLongitude).pow(2.0))}")
            if (sqrt((it.latitude - _detailScheduleMapScreenUIState.value.destinationLatitude).pow(2.0) + (it.longitude - _detailScheduleMapScreenUIState.value.destinationLongitude).pow(2.0)) < 0.00335) {
                checkArrival()
            }
        }
    }

    private suspend fun checkArrival() {
        val accessToken = getAccessTokenUseCase().first()
        val memberId = getMemberIdUseCase().first()
        val request = CheckArrivalRequest(memberId, scheduleId)
        val response = checkArrivalUseCase(accessToken, request)
        LogUtil.printNetworkLog(request, response, "도착 여부")
        when (response) {
            is NetworkResult.Success -> {

            }
            is NetworkResult.Error -> {  }
            is NetworkResult.Exception -> { detailScheduleMapScreenSideEffect.emit(DetailScheduleMapScreenSideEffect.Toast("오류가 발생했습니다.")) }
        }
    }

}