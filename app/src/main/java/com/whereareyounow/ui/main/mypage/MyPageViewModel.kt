package com.whereareyounow.ui.main.mypage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.myinfo.MyInfoScreenUIState
import com.whereareyounow.domain.request.feed.BookmarkFeedRequest
import com.whereareyounow.domain.request.feed.GetBookmarkedFeedRequest
import com.whereareyounow.domain.request.member.GetUserInfoByMemberSeqRequest
import com.whereareyounow.domain.request.member.SendEmailCodeRequest
import com.whereareyounow.domain.request.member.VerifyEmailCodeRequest
import com.whereareyounow.domain.usecase.datastore.ClearAuthDataStoreUseCase
import com.whereareyounow.domain.usecase.feed.BookmarkFeedUseCase
import com.whereareyounow.domain.usecase.feed.GetBookmarkedFeedUseCase
import com.whereareyounow.domain.usecase.location.DeleteFavoriteLocationUsecase
import com.whereareyounow.domain.usecase.location.GetFavoriteLocationUseCase
import com.whereareyounow.domain.usecase.member.GetUserInfoByMemberSeqUseCase
import com.whereareyounow.domain.usecase.member.SendEmailCodeUseCase
import com.whereareyounow.domain.usecase.member.VerifyEmailCodeUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.ui.main.mypage.mapper.toModel
import com.whereareyounow.ui.main.mypage.model.FeedBookMarkResponseModel
import com.whereareyounow.ui.main.mypage.model.LocationFavoriteInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val application: Application,
    private val getUserInfoByMemberSeqUseCase: GetUserInfoByMemberSeqUseCase,
    private val verifyEmailCodeUseCase: VerifyEmailCodeUseCase,
    private val sendEmailCodeUseCase: SendEmailCodeUseCase,
    private val getFaboriteLocationUseCase: GetFavoriteLocationUseCase,
    private val deleteFavoriteLocacionUseCase: DeleteFavoriteLocationUsecase,
    private val getFeedBookMarkUseCase: GetBookmarkedFeedUseCase,
    private val addFeedBookMarkUseCase: BookmarkFeedUseCase,
    private val clearAuthDataStoreUseCase: ClearAuthDataStoreUseCase,
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MyInfoScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun getMyInfo() {
        val requestData = GetUserInfoByMemberSeqRequest(
            memberSeq = AuthData.memberSeq
        )
        getUserInfoByMemberSeqUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                name = data.userName,
                                email = data.email
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .onEach {  }
    }


    private val _locationSeq = MutableStateFlow<Int?>(0)
    val locationSeq: StateFlow<Int?> = _locationSeq
    private val _location = MutableStateFlow<String?>("")
    val location: StateFlow<String?> = _location
    private val _streetName = MutableStateFlow<String?>("")
    val streetName: StateFlow<String?> = _streetName
    private val _locationFavoriteList =
        MutableStateFlow<List<LocationFavoriteInfoModel?>>(emptyList())

    val locationFaboriteList: StateFlow<List<LocationFavoriteInfoModel?>> = _locationFavoriteList
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _feedBookMarkState = MutableStateFlow<FeedBookMarkResponseModel?>(null)
    val feedBookMarkState: StateFlow<FeedBookMarkResponseModel?> = _feedBookMarkState


    init {
        getMyInfo(AuthData.memberSeq)
        // 테스트 후 제거
        getLocationFavorite(
            memberSeq = AuthData.memberSeq
        )
        getFeedBookMark(
            memberSeq = AuthData.memberSeq
        )
        addBookMarkFeed(0, 0)
//        getLocationFaborite()
    }

    data class Announcement(
        val string: String
    )

    private val _announcementList = MutableStateFlow<List<Announcement>>(emptyList())
    val announcementList: StateFlow<List<Announcement>> = _announcementList.asStateFlow()

    private fun getFeedBookMark(memberSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getFeedBookMarkUseCase(
                GetBookmarkedFeedRequest(
                    memberSeq = 1,
                    page = 0,
                    size = 10
                )
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _feedBookMarkState.value = it.toModel()
                    }
                }.onError { code, message ->
                    Log.d("Sflsjfleifnslf", message.toString())

                }.onException {
                    Log.d("Sflsjfleifnslf", it.message ?: "Unknown exception")

                }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "getFeedBookMarkUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(this)
        }

    }

    private fun addBookMarkFeed(
        feedSeq: Int,
        memberSeq: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addFeedBookMarkUseCase(
                BookmarkFeedRequest(
                    feedSeq = feedSeq,
                    memberSeq = memberSeq
                )
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    Log.d("Sflsjfleifnslfsfsfsfsu", code.toString())
                    Log.d("Sflsjfleifnslfsfsfsfsu", message.toString())
                    Log.d("Sflsjfleifnslfsfsfsfsu", data.toString())

                }.onError { code, message ->
                    Log.d("Sflsjfleifnslfsfsfsfer", message!!)
                    Log.d("Sflsjfleifnslfsfsfsfer", code.toString())

                }.onException {
                    Log.d("Sflsjfleifnslfsfsfsfex", it.message ?: "Unknown exception")

                }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "getFeedBookMarkUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(this)
        }
    }


    fun signOut(
        moveToSignInMethodSelectionScreen: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
//            saveRefreshTokenUseCase("")
//            saveAccessTokenUseCase("")
//            saveMemberIdUseCase("")
            clearAuthDataStoreUseCase()
            withContext(Dispatchers.Main) { moveToSignInMethodSelectionScreen() }
        }
    }

    fun getMyInfo(memberSeq: Int) {

        viewModelScope.launch(Dispatchers.IO) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val response = getMemberDetailsUseCase(accessToken, memberId)
//            LogUtil.printNetworkLog("memberId = $memberId", response, "내 정보 가져오기")
            getUserInfoByMemberSeqUseCase(GetUserInfoByMemberSeqRequest(memberSeq = memberSeq)).collect {
                when (it) {

                    is NetworkResult.Success -> {
                        val userInfo = it.data?.toModel()
//                        userInfo?.let {
//                            _name.value = it.userName
//                            _email.value = it.email
//                            _profileImageUri.value = it.profileImage
//                        }
                    }
                    is NetworkResult.Error -> {

                    }

                    is NetworkResult.Exception -> {

                    }
                }
            }
        }
    }

    fun emailRequest(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendEmailCodeUseCase(
                SendEmailCodeRequest(
                    email = email
                )
            ).collect { response ->
                when (response) {

                    is NetworkResult.Success -> {


                    }

                    is NetworkResult.Error -> {


                    }

                    is NetworkResult.Exception -> {


                    }
                }
            }

        }

    }

    fun emailVerify(email: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {

            verifyEmailCodeUseCase(
                VerifyEmailCodeRequest(
                    email = email,
                    code = code
                )
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
//                        _isVerifyed.value = data
                    }
                }.onError { code, message ->

                }.onException { }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "VerifyEmailCodeUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    fun withdrawAccount(
        moveToStartScreen: () -> Unit,
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val request = com.whereareyounow.domain.request.signin.DeleteMemberRequest(memberId)
//            val response = deleteMemberUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "회원 탈퇴하기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                        moveToStartScreen()
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

    fun deleteCalendar() {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val request =
//                com.whereareyounow.domain.request.schedule.ResetCalendarRequest(memberId)
//            val response = resetCalendarUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "캘린더 삭제")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "캘린더가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
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


    private fun getLocationFavorite(memberSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getFaboriteLocationUseCase(
                memberSeq = memberSeq
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    Log.d("getLocationFaborite", code.toString())
                    Log.d("getLocationFaborite", message.toString())
                    Log.d("getLocationFaborite", data.toString())

                    val updatedList = data?.map { it.toModel() } ?: emptyList()
                    _locationFavoriteList.value = updatedList.toList()

                    _isLoading.value = false

                }.onError { code, message ->
                    Log.d("getLocationFaborite", code.toString())
                    Log.d("getLocationFaborite", message.toString())

                }.onException { }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "getFaboriteLocationUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(this)

        }

    }

    fun deleteFavoriteLocation(
        memberSeq: Int,
        locationSeqs: List<Int>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteLocacionUseCase(
                memberSeq = memberSeq,
                locationSeqs = locationSeqs
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    Log.d("getLocationFaborite", code.toString())
                    Log.d("getLocationFaborite", message.toString())
                    Log.d("getLocationFaborite", data.toString())

                }.onError { code, message ->
                    Log.d("getLocationFaborite", code.toString())
                    Log.d("getLocationFaborite", message.toString())

                }.onException { }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "getFaboriteLocationUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(this)
        }
    }

    init {
        getMyInfo()
    }
}


