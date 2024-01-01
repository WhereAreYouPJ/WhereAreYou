package com.whereareyounow.ui.home.mypage

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.entity.apimessage.signin.DeleteMemberRequest
import com.whereareyounow.domain.usecase.signin.DeleteMemberUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.ModifyMyInfoUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val application: Application,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveMemberIdUseCase: SaveMemberIdUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase
) : AndroidViewModel(application) {

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _profileImageUri = MutableStateFlow<String?>("")
    val profileImageUri: StateFlow<String?> = _profileImageUri

    fun signOut(
        moveToStartScreen: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            saveRefreshTokenUseCase("")
            saveAccessTokenUseCase("")
            saveMemberIdUseCase("")
            withContext(Dispatchers.Main) { moveToStartScreen() }
        }
    }

    fun getMyInfo() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val response = getMemberDetailsUseCase(accessToken, memberId)
            LogUtil.printNetworkLog(response, "내 정보 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _name.update { data.userName }
                        _email.update { data.email }
                        _profileImageUri.update { data.profileImage }
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
        }
    }

    fun withdrawAccount(
        moveToStartScreen: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = DeleteMemberRequest(memberId)
            val response = deleteMemberUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "회원 탈퇴하기")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        moveToStartScreen()
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
        }
    }

    fun deleteCalendar() {
        viewModelScope.launch(Dispatchers.Default) {

        }
    }
}