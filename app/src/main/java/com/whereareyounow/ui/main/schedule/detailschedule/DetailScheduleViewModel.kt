package com.whereareyounow.ui.main.schedule.detailschedule

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.whereareyounow.R
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class DetailScheduleViewModel @Inject constructor(
    private val application: Application,
//    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
//    private val getDetailScheduleUseCase: GetDetailScheduleUseCase,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val refuseOrQuitScheduleUseCase: RefuseOrQuitScheduleUseCase,
//    private val deleteScheduleUseCase: DeleteScheduleUseCase,
) : AndroidViewModel(application) {

    private val _detailScheduleScreenUIState = MutableStateFlow(DetailScheduleScreenUIState())
    val detailScheduleScreenUIState = _detailScheduleScreenUIState.asStateFlow()

    private var scheduleId = ""
    private var memberIdsList = emptyList<String>()
    private var arrivedMemberIdsList = emptyList<String>()
    var destinationLatitude = 0.0
    var destinationLongitude = 0.0
    private val _isScheduleCreator = MutableStateFlow(false)
    val isScheduleCreator: StateFlow<Boolean> = _isScheduleCreator

    fun updateScheduleId(id: String) {
        scheduleId = id
        getDetailSchedule(id)
    }

    private fun getDetailSchedule(id: String) {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val response = getDetailScheduleUseCase(accessToken, memberId, id)
//            LogUtil.printNetworkLog("memberId = $memberId\nscheduleId = $scheduleId", response, "일정 상세 정보 가져오기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    response.data?.let { data ->
//                        _isScheduleCreator.update { data.creatorId == memberId }
//                        destinationLatitude = data.destinationLatitude
//                        destinationLongitude = data.destinationLongitude
//                        _detailScheduleScreenUIState.update {
//                            // {년, 월, 일}
//                            val appointmentDateList = data.appointmentTime.split("T")[0].split("-").map { num -> num.toInt() }
//                            // {시, 분}
//                            val appointmentTimeList = data.appointmentTime.split("T")[1].split(":").map { num -> num.toInt() }
//                            it.copy(
//                                scheduleName = data.title,
//                                scheduleYear = appointmentDateList[0],
//                                scheduleMonth = appointmentDateList[1],
//                                scheduleDate = appointmentDateList[2],
//                                scheduleDayOfWeek = getDayOfWeekString(appointmentDateList[0], appointmentDateList[1], appointmentDateList[2]),
//                                scheduleHour = appointmentTimeList[0],
//                                scheduleMinute = appointmentTimeList[1],
//                                destinationName = data.place.replace("<b>", "").replace("</b>", ""),
//                                destinationRoadAddress = data.roadName,
//                                memo = data.memo,
//                                isLocationCheckEnabled = getMinuteDiffWithCurrentTime(data.appointmentTime) <= 60
//                            )
//                        }
//                        memberIdsList = data.friendsIdList.toMutableList()
//                        arrivedMemberIdsList = data.arrivedFriendsIdList
//                        getUsersInfo()
//                    }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
    }

    private suspend fun getUsersInfo() {
//        val accessToken = getAccessTokenUseCase().first()
//        val memberInfosList = mutableListOf<MemberInfo>()
//
//        // 유저의 정보를 가져온다.
//        for (id in memberIdsList) {
//            val response = getMemberDetailsUseCase(accessToken, id)
//            LogUtil.printNetworkLog("memberId = $id", response, "유저 정보 가져오기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    response.data?.let { data ->
//                        val memberInfo = MemberInfo(
//                            memberId = id,
//                            name = data.userName,
//                            userId = data.userId,
//                            email = data.email,
//                            profileImage = data.profileImage,
//                            imageBitmap = when (data.profileImage != null) {
//                                true -> {
//                                    val connection = URL(data.profileImage).openConnection().apply {
//                                        doInput = true
//                                        connect()
//                                    }
//                                    val input = connection.getInputStream()
//                                    val bitmap = BitmapFactory.decodeStream(input)
//                                    bitmap.getCircledBitmap(application.applicationContext, (application.resources.displayMetrics.density * 40).toInt())
//                                }
//                                false -> null
//                            },
//                            isArrived = id in arrivedMemberIdsList
//                        )
//                        memberInfosList.add(memberInfo)
//                    }
//                }
//                is NetworkResult.Error -> {}
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
//        _detailScheduleScreenUIState.update {
//            it.copy(
//                memberInfosList = memberInfosList.sortedWith(
//                    compareBy(
//                        { memberInfo -> !memberInfo.isArrived },
//                        { memberInfo -> memberInfo.name }
//                    )
//                )
//            )
//        }
    }

    fun quitSchedule(
        moveToBackScreen: () -> Unit
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val request = com.whereareyounow.domain.request.schedule.RefuseOrQuitScheduleRequest(
//                memberId,
//                scheduleId
//            )
//            val response = refuseOrQuitScheduleUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "일정 나가기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) { moveToBackScreen() }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
    }

    fun deleteSchedule(
        moveToBackScreen: () -> Unit
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val request = com.whereareyounow.domain.request.schedule.DeleteScheduleRequest(
//                memberId,
//                scheduleId
//            )
//            val response = deleteScheduleUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "일정 삭제하기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) { moveToBackScreen() }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
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