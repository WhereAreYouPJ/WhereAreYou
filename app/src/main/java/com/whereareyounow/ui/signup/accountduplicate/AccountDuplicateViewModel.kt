package com.whereareyounow.ui.signup.accountduplicate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.request.member.LinkAccountRequest
import com.whereareyounow.domain.usecase.member.LinkAccountUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AccountDuplicateViewModel @Inject constructor(
    private val linkAccountUseCase: LinkAccountUseCase
) : ViewModel() {
    fun linkAccount(
        userName: String,
        email: String,
        password: String,
        loginType: String,
        fcmToken: String = "a",
        moveToSignUpSuccessScreen: () -> Unit
    ) {
        val requestData = LinkAccountRequest(
            userName = userName,
            email = email,
            password = password,
            loginType = loginType,
            fcmToken = fcmToken
        )
        linkAccountUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    moveToSignUpSuccessScreen()
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }
}