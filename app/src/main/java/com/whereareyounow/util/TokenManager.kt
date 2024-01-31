package com.whereareyounow.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.whereareyounow.domain.entity.apimessage.signin.ReissueTokenRequest
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetRefreshTokenUseCase
import com.whereareyounow.domain.usecase.signin.ReissueTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyounow.domain.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val context: Context
) {
    val goToLoginScreen: () -> Unit = {}

//    suspend fun getAccessToken(): String {
//        return withContext(Dispatchers.IO) {
//            try {
//                val accessToken = getAccessTokenUseCase().first()
//                if (accessToken != "") { accessToken }
//                else { throw Exception() }
//            } catch (e: Exception) {
//                reissueToken()
//                getAccessTokenUseCase().first()
//            }
//        }
//    }

//    suspend fun reissueToken() {
//            val refreshToken = getRefreshTokenUseCase().first()
//            if (refreshToken != "") { refreshToken }
//        val request = ReissueTokenRequest(refreshToken)
//        when (val response = reissueTokenUseCase(request)) {
//            is NetworkResult.Success -> {
//                Log.e("Success", "reissueTokenUseCase\n${response.code}\n${response.data}")
//                response.data?.let { data ->
//                    saveAccessTokenUseCase(data.accessToken)
//                    saveRefreshTokenUseCase(data.refreshToken)
//                } ?: withContext(Dispatchers.Main) { Toast.makeText(context, "null data", Toast.LENGTH_SHORT).show() }
//            }
//            is NetworkResult.Error -> {
//                Log.e("Error", "reissueTokenUseCase\n${response.code}\n${response.errorData}")
//                withContext(Dispatchers.Main) {
//                    when (response.code) {
//                        401 -> { Toast.makeText(context, "만료된 토큰입니다.", Toast.LENGTH_SHORT).show() }
//                        404 -> { Toast.makeText(context, "존재하지 않는 토큰입니다.", Toast.LENGTH_SHORT).show() }
//                        410 -> { Toast.makeText(context, "이미 사용된 토큰입니다.", Toast.LENGTH_SHORT).show() }
//                        else -> { Toast.makeText(context, "알 수 없는 네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                    }
//                }
//            }
//            is NetworkResult.Exception -> {
//                Log.e("Exception", "reissueTokenUseCase\n${response.e.printStackTrace()}")
//                withContext(Dispatchers.Main) { Toast.makeText(context, "알 수 없는 예외가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//            }
//        }
//    }
    suspend fun getAccessToken(): String {
        return withContext(Dispatchers.IO) { getAccessTokenUseCase().first() }
    }

    suspend fun reissueToken(
        moveToSignInScreen: () -> Unit
    ) {
        val refreshToken = getRefreshTokenUseCase().first()
        val request = ReissueTokenRequest(refreshToken)
        when (val response = reissueTokenUseCase(request)) {
            is NetworkResult.Success -> {
                Log.e("Success", "reissueTokenUseCase\n${response.code}\n${response.data}")
                response.data?.let { data ->
                    saveAccessTokenUseCase(data.accessToken)
                    saveRefreshTokenUseCase(data.refreshToken)
                } ?: withContext(Dispatchers.Main) { Toast.makeText(context, "null data", Toast.LENGTH_SHORT).show() }
            }
            is NetworkResult.Error -> {
                Log.e("Error", "reissueTokenUseCase\n${response.code}\n${response.errorData}")
                withContext(Dispatchers.Main) {
                    when (response.code) {
                        401 -> {
                            Toast.makeText(context, "만료된 토큰입니다.", Toast.LENGTH_SHORT).show()
                            moveToSignInScreen()
                        }
                        404 -> {
                            Toast.makeText(context, "존재하지 않는 토큰입니다.", Toast.LENGTH_SHORT).show()
                            moveToSignInScreen()
                        }
                        410 -> {
                            Toast.makeText(context, "이미 사용된 토큰입니다.", Toast.LENGTH_SHORT).show()
                            moveToSignInScreen()
                        }
                        else -> { Toast.makeText(context, "알 수 없는 네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
                    }
                }
            }
            is NetworkResult.Exception -> {
            }
        }
    }
}