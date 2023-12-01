package com.whereareyou.ui.home.mypage

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.ModifyMyInfoUseCase
import com.whereareyou.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyou.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyou.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val application: Application,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveMemberIdUseCase: SaveMemberIdUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val modifyMyInfoUseCase: ModifyMyInfoUseCase

) : AndroidViewModel(application) {

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    fun signOut(
        moveToStartScreen: () -> Unit,
    ) {
        viewModelScope.launch {
            saveRefreshTokenUseCase("")
            saveAccessTokenUseCase("")
            saveMemberIdUseCase("")
            moveToStartScreen()
        }
    }

    @SuppressLint("Recycle", "Range")
    fun updateProfileImage(uri: Uri) {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val cursor = application.contentResolver.query(uri, null, null, null, null)
            cursor?.moveToNext()
            val path = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
            val file = path?.let { File(it) }
            _imageUri.update {
                path
            }
            Log.e("File", "${file?.absolutePath}")
//            val file = File.createTempFile(
//                prefix = "${System.currentTimeMillis()}",
//                suffix = ".jpg",
//                directory = application.cacheDir
//            )
            file?.let {
                when (val updateProfileImageResponse = modifyMyInfoUseCase(accessToken, it, "user1")) {
                    is NetworkResult.Success -> {
                        Log.e("success", "${updateProfileImageResponse.code}, ${updateProfileImageResponse.data}")
                    }

                    is NetworkResult.Error -> {
                        Log.e("error", "${updateProfileImageResponse.code}, ${updateProfileImageResponse.errorData}")
                    }

                    is NetworkResult.Exception -> { Log.e("exception", "${updateProfileImageResponse.e}") }
                }
            }
        }
    }
}