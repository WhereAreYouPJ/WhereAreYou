package com.whereareyou.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.util.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: Application,
    private val getMemberIdUseCase: GetMemberIdUseCase,
) : AndroidViewModel(application) {

    private val _checkingState = MutableStateFlow(CheckingState.NETWORK)
    val checkingState: StateFlow<CheckingState> = _checkingState
    private val _isNetworkConnectionErrorDialogShowing = MutableStateFlow(false)
    val isNetworkConnectionErrorDialogShowing: StateFlow<Boolean> = _isNetworkConnectionErrorDialogShowing
    private val _screenState = MutableStateFlow(ScreenState.SPLASH)
    val screenState: StateFlow<ScreenState> = _screenState

    fun updateCheckingState(state: CheckingState) {
        _checkingState.update { state }
    }

    fun updateIsNetworkConnectionErrorDialogShowing(isShowing: Boolean) {
        _isNetworkConnectionErrorDialogShowing.update { isShowing }
    }

    fun updateScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    fun checkNetworkState(): Boolean {
        return NetworkManager.checkNetworkState()
    }

    fun checkIsSignedIn(): Boolean {
        var isSignedIn = false
        runBlocking {
            delay(2000)
            if (getMemberIdUseCase().first().isNotEmpty()) {
                isSignedIn = false
            }
        }
        return isSignedIn
    }

    enum class CheckingState {
        NETWORK, LOCATION_PERMISSION, SIGN_IN
    }

    enum class ScreenState {
        SPLASH, PERMISSION
    }
}