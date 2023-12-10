package com.whereareyounow.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val application: Application
) : AndroidViewModel(application) {

    private val _inputUserName = MutableStateFlow("")
    val inputUserName: StateFlow<String> = _inputUserName
    private val _inputUserId = MutableStateFlow("")
    val inputUserId: StateFlow<String> = _inputUserId
    private val _inputPassword = MutableStateFlow("")
    val inputPassword: StateFlow<String> = _inputPassword
    private val _inputPasswordForChecking = MutableStateFlow("")
    val inputPasswordForChecking: StateFlow<String> = _inputPasswordForChecking
    private val _inputEmail = MutableStateFlow("")
    val inputEmail: StateFlow<String> = _inputEmail

    fun updateInputUserName(userName: String) {
        _inputUserName.update { userName }
    }

    fun updateInputUserId(userId: String) {
        _inputUserId.update { userId }
    }

    fun updateInputPassword(password: String) {
        _inputPassword.update { password }
    }

    fun updateInputPasswordForChecking(password: String) {
        _inputPasswordForChecking.update { password }
    }

    fun updateInputEmail(email: String) {
        _inputEmail.update { email }
    }
}