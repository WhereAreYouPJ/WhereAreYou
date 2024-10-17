package com.whereareyounow.ui.main.schedule.detailschedule

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.R
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.detailschedule.DetailScheduleMapScreenSideEffect
import com.whereareyounow.data.detailschedule.DetailScheduleMapScreenUIState
import com.whereareyounow.domain.entity.UserLocation
import com.whereareyounow.domain.request.location.GetUserLocationRequest
import com.whereareyounow.domain.request.location.SendUserLocationRequest
import com.whereareyounow.domain.request.member.GetUserInfoByMemberSeqRequest
import com.whereareyounow.domain.request.schedule.GetDetailScheduleRequest
import com.whereareyounow.domain.usecase.location.GetUserLocationUseCase
import com.whereareyounow.domain.usecase.location.SendUserLocationUseCase
import com.whereareyounow.domain.usecase.member.GetUserInfoByMemberSeqUseCase
import com.whereareyounow.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.util.LocationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class DetailScheduleMapViewModel @Inject constructor(
    private val application: Application,
    private val getDetailScheduleUseCase: GetDetailScheduleUseCase,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val sendUserLocationUseCase: SendUserLocationUseCase,
    private val getUserInfoByMemberSeqUseCase: GetUserInfoByMemberSeqUseCase,
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
                                memberLocationList = data.memberInfos.map { UserLocation(
                                    memberSeq = it.memberSeq
                                ) }
                            )
                        }
                        getUsersLocation()
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    private fun getUsersLocation() {
        _uiState.value.memberLocationList.forEachIndexed { idx, userLocation ->
            val requestData1 = GetUserInfoByMemberSeqRequest(userLocation.memberSeq)
            getUserInfoByMemberSeqUseCase(requestData1)
                .onEach { networkResult ->
                    networkResult.onSuccess { code, message, data ->
                        data?.let {
                            _uiState.update {
                                it.copy(
                                    memberLocationList = it.memberLocationList.map {
                                        val connection = URL(data.profileImage).openConnection().apply {
                                            doInput = true
                                            connect()
                                        }
                                        val input = connection.getInputStream()
                                        val bitmap = BitmapFactory.decodeStream(input)

                                        if (it.memberSeq == userLocation.memberSeq) {
                                            userLocation.copy(
                                                userName = data.userName,
                                                profileImage = bitmap.getCircledBitmap(application.applicationContext, (application.resources.displayMetrics.density * 40).toInt())
                                            )
                                        } else {
                                            it
                                        }
                                    }
                                )
                            }
                        }
                    }.onError { code, message ->

                    }.onException {  }
                }
                .catch {  }
                .launchIn(viewModelScope)


            val requestData2 = GetUserLocationRequest(userLocation.memberSeq)
            getUserLocationUseCase(requestData2)
                .onEach { networkResult ->
                    networkResult.onSuccess { code, message, data ->
                        data?.let {
                            _uiState.update {
                                it.copy(
                                    memberLocationList = it.memberLocationList.map {
                                        if (it.memberSeq == userLocation.memberSeq) {
                                            userLocation.copy(
                                                x = data.x,
                                                y = data.y
                                            )
                                        } else {
                                            it
                                        }
                                    }
                                )
                            }
                        }
                    }.onError { code, message ->

                    }.onException {  }
                }
                .catch {  }
                .launchIn(viewModelScope)
        }
    }

    fun sendUserLocation() {
        locationUtil.getCurrentLocation {
            val requestData = SendUserLocationRequest(
                AuthData.memberSeq,
                it.latitude,
                it.longitude
            )
            sendUserLocationUseCase(requestData)
                .onEach { networkResult ->
                    networkResult.onSuccess { code, message, data ->
                        getUsersLocation()
                    }.onError { code, message ->

                    }.onException {  }
                }
                .catch {  }
                .launchIn(viewModelScope)

            LogUtil.e("locationDiff", "${sqrt((it.latitude - _uiState.value.x).pow(2.0) + (it.longitude - _uiState.value.y).pow(2.0))}")
            if (sqrt((it.latitude - _uiState.value.x).pow(2.0) + (it.longitude - _uiState.value.y).pow(2.0)) < 0.00335) {
                checkArrival()
            }
        }
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
}

fun Bitmap.getCircledBitmap(context: Context, size: Int): Bitmap {
    val croppedBitmap =
        if (this.width == this.height) {
            this
        } else if (this.width > this.height) {
            val horizontalCenter = this.width / 2
            val verticalCenter = this.height / 2
            Bitmap.createBitmap(this, horizontalCenter - verticalCenter, 0, verticalCenter * 2, verticalCenter * 2)
        } else {
            val horizontalCenter = this.width / 2
            val verticalCenter = this.height / 2
            Bitmap.createBitmap(this, 0, verticalCenter - horizontalCenter, horizontalCenter * 2, horizontalCenter * 2)
        }

    val locationBackground = ContextCompat.getDrawable(context, R.drawable.location_background)!!
    val backgroundOutput = Bitmap.createBitmap(size, ((size.toFloat() / locationBackground.intrinsicWidth) * locationBackground.intrinsicHeight).toInt(), Bitmap.Config.ARGB_8888)
    val rect = Rect(0, 0, size, ((size.toFloat() / locationBackground.intrinsicWidth) * locationBackground.intrinsicHeight).toInt())
    val mainCanvas = Canvas(backgroundOutput)
    locationBackground.setBounds(0, 0, mainCanvas.width, mainCanvas.height)
    locationBackground.draw(mainCanvas)

    val resizedBitmap = Bitmap.createScaledBitmap(croppedBitmap, size, size, false)
    val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(size / 2f, size / 2f, (size - 10) / 2f, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(resizedBitmap, rect, rect, paint)
    mainCanvas.drawBitmap(output, rect, rect, null)

    return backgroundOutput
}