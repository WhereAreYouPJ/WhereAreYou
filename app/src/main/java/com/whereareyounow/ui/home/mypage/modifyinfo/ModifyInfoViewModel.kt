package com.whereareyounow.ui.home.mypage.modifyinfo

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.ModifyMyInfoUseCase
import com.whereareyounow.domain.usecase.signup.CheckIdDuplicateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.ui.signup.UserIdState
import com.whereareyounow.ui.signup.UserNameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.File

@HiltViewModel
class ModifyInfoViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val checkIdDuplicateUseCase: CheckIdDuplicateUseCase,
    private val modifyMyInfoUseCase: ModifyMyInfoUseCase,
) : AndroidViewModel(application) {

    private val nameCondition = Regex("^[\\uAC00-\\uD7A3a-zA-Z]{4,10}\$")
    private val idCondition = Regex("^[a-z][a-z0-9]{3,9}\$")

    private var originalName = ""
    private var originalId = ""

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    private val _inputNameState = MutableStateFlow(UserNameState.EMPTY)
    val inputNameState: StateFlow<UserNameState> = _inputNameState
    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id
    private val _inputIdState = MutableStateFlow(UserIdState.EMPTY)
    val inputIdState: StateFlow<UserIdState> = _inputIdState
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _profileImageUri = MutableStateFlow<String>("")
    val profileImageUri: StateFlow<String> = _profileImageUri

    fun updateName(name: String) {
        _name.update { name }
        _inputNameState.update {
            if (name == "") {
                UserNameState.EMPTY
            } else {
                if (name.matches(nameCondition)) UserNameState.SATISFIED else UserNameState.UNSATISFIED
            }
        }
    }

    fun updateId(id: String) {
        _id.update { id }
        _inputIdState.update {
            if (id == "") {
                UserIdState.EMPTY
            } else {
                if (id.matches(idCondition)) UserIdState.SATISFIED else UserIdState.UNSATISFIED
            }
        }
    }

    fun updateProfileImageUri(uri: Uri) {
        _profileImageUri.update { uri.toString() }
    }

    private fun getUserInfo() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val response = getMemberDetailsUseCase(accessToken, memberId)
            LogUtil.printNetworkLog(response, "내 정보 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        originalName = data.userName
                        originalId = data.userId
                        _name.update { data.userName }
                        _id.update { data.userId }
                        _email.update { data.email }
                        _profileImageUri.update { data.profileImage ?: "" }
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
        }
    }


    fun checkIdDuplicate() {
        viewModelScope.launch {
            if (_inputIdState.value == UserIdState.SATISFIED) {
                val response = checkIdDuplicateUseCase(_id.value)
                LogUtil.printNetworkLog(response, "아이디 중복 체크")
                when (response) {
                    is NetworkResult.Success -> {
                        _inputIdState.update { UserIdState.UNIQUE }
                    }
                    is NetworkResult.Error -> {
                        _inputIdState.update { UserIdState.DUPLICATED }
                    }
                    is NetworkResult.Exception -> {

                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "아이디를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("Range")
    fun saveModifiedInfo(
        moveToBackScreen: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            var imageFile: File? = null
            if (_profileImageUri.value.contains("content")) {
                val cursor = application.contentResolver.query(_profileImageUri.value.toUri(), null, null, null, null)
                cursor?.moveToNext()
                val path = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
                imageFile = path?.let { File(it) }
            }
            if (_id.value == originalId && imageFile == null) {
                withContext(Dispatchers.Main) {
                    moveToBackScreen()
                }
            } else {
                val response: NetworkResult<Unit> = if (_id.value == originalId && imageFile != null) {
                    modifyMyInfoUseCase(accessToken, memberId, imageFile, "")
                } else if (_id.value != originalId && imageFile == null) {
                    modifyMyInfoUseCase(accessToken, memberId, null, _id.value)
                } else {
                    modifyMyInfoUseCase(accessToken, memberId, imageFile, _id.value)
                }
                LogUtil.printNetworkLog(response, "내 정보 수정하기")
                when (response) {
                    is NetworkResult.Success -> {
                        withContext(Dispatchers.Main) {
                            moveToBackScreen()
                        }
                    }
                    is NetworkResult.Error -> {  }
                    is NetworkResult.Exception -> {  }
                }
            }
        }
    }

    init {
        getUserInfo()
    }
}