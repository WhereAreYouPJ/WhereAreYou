package com.whereareyou.ui.signin

import android.app.Application
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whereareyou.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyou.domain.entity.apimessage.signin.ResetPasswordRequest
import com.whereareyou.domain.entity.apimessage.signin.SignInRequest
import com.whereareyou.domain.entity.apimessage.signin.VerifyPasswordResetCodeRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyou.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyou.domain.usecase.signin.FindIdUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.domain.usecase.signin.ResetPasswordUseCase
import com.whereareyou.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyou.domain.usecase.signin.SignInUseCase
import com.whereareyou.domain.usecase.signin.VerifyPasswordResetCodeUseCase
import com.whereareyou.domain.usecase.signup.AuthenticateEmailCodeUseCase
import com.whereareyou.domain.usecase.signup.AuthenticateEmailUseCase
import com.whereareyou.domain.usecase.signup.CheckEmailDuplicateUseCase
import com.whereareyou.domain.usecase.signup.CheckIdDuplicateUseCase
import com.whereareyou.domain.usecase.signup.SignUpUseCase
import com.whereareyou.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    application: Application,
    private val signInUseCase: SignInUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val saveMemberIdUseCase: SaveMemberIdUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,


    private val signUpUseCase: SignUpUseCase, //회원가입
    private val checkIdDuplicateUseCase: CheckIdDuplicateUseCase,   // 아이디 중복확인
    private val checkEmailDuplicateUseCase: CheckEmailDuplicateUseCase, // 이메일 중복확인
    private val authenticateEmailUseCase: AuthenticateEmailUseCase, // 이메일 인증
    private val authenticateEmailCodeUseCase: AuthenticateEmailCodeUseCase, //이메일 코드확인

    private val findIdUseCase: FindIdUseCase, //아이디 찾기
    private val verifyPasswordResetCodeUseCase: VerifyPasswordResetCodeUseCase, //비밀번호 재설정 인증
    private val resetPasswordUseCase: ResetPasswordUseCase //비밀번호 재 설정





    // checkIdDuplicateUsecase랑 getMemberIdUseCase 무슨차이?
    // 회원가입 성공시 바로 로그인 되도록?(access token 저장 ) -> 아니면 다시 로그인 화면 ?

) : AndroidViewModel(application) {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult
    fun verifyPasswordResetCode(userId: String, email: String, code: String) {
        viewModelScope.launch {
            val body = VerifyPasswordResetCodeRequest(userId, email, code)

            val verifyCodeResult = verifyPasswordResetCodeUseCase(body)
            when (verifyCodeResult) {
                is NetworkResult.Success -> {
                    // 비밀번호 재설정 인증 성공 시 처리
                    Log.d("verify", "Password reset code verified")
                    Log.d("verify",verifyCodeResult.toString())
                    // 이후 비밀번호 재설정 로직 수행
                    // resetPassword(userId, newPassword, newPasswordCheck)
                }
                is NetworkResult.Error -> {
                    // 비밀번호 재설정 인증 실패 시 처리
                    Log.e("verify", "Password reset code verification failed: ${verifyCodeResult.errorData}")
                }
                is NetworkResult.Exception -> {
                    // 예외 처리 로직
                    Log.e("verify", "Password reset code verification exception: ${verifyCodeResult.e.message}")
                }
            }
        }
    }

    fun resetPassword(userId: String, password: String, checkPassword: String) {
        viewModelScope.launch {


            val body = ResetPasswordRequest(userId, password, checkPassword)
            Log.d("resetPassword",body.toString())

           Log.d("resetpassword",resetPasswordUseCase(body).toString())
/*
            when (resetPasswordResult) {
                is NetworkResult.Success -> {
                    // 비밀번호 재설정 성공 시 처리
                    Log.d("reset","ok")
                }
                is NetworkResult.Error -> {
                    // 에러 처리 로직
                    Log.d("reset2","xx")

                }
                is NetworkResult.Exception -> {
                    // 예외 처리 로직
                    Log.d("reset3","xx")

                }
            }*/

        }
    }


    //  로그인 함수
    fun LogIn(user_name: String, user_password: String,onLoginResult: (Boolean) -> Unit // 콜백 함수
    ){
        var isLoginSuccess = false // 초기값 false

        viewModelScope.launch() {
            val body = SignInRequest(user_name, user_password)
            Log.e("SignVIewModel",body.toString())

            val signInResult = signInUseCase(body)
            Log.e("SignVIewModel",signInResult.toString())

            when (signInResult) {
                is NetworkResult.Success -> {
                    Log.e("SignViewModel", "성공")

                    Log.e("SignViewModel", signInResult.data.toString())
                    saveAccessTokenUseCase("Bearer " + signInResult.data!!.accessToken)
                    saveMemberIdUseCase(signInResult.data!!.memberId)
                    isLoginSuccess=true

                }

                is NetworkResult.Error -> {
                    Log.e("SignViewModel", "error")
                    Log.e("SignViewModel", "Error: ${signInResult.code}")
                    Log.e("SignViewModel", "Error: ${signInResult.errorData}")
                    isLoginSuccess=false


                }

                is NetworkResult.Exception -> {
                    Log.e("SignViewModel", "${signInResult.e.message}")
                    isLoginSuccess=false

                }
            }

            val accessToken = getAccessTokenUseCase().first()
            val memberid = getMemberIdUseCase().first()
            Log.e("GlobalViewModel", "$memberid $accessToken")
            onLoginResult(isLoginSuccess)
        }
        Log.e("SignViewModel", isLoginSuccess.toString())

    }
    // 아이디 중복여부 체크 함수
    fun checkIdDuplicated(user_id:String,onidDuplicatedresult: (Boolean) -> Unit ) {

        var isIdDuplicated = false // 초기값 false

        viewModelScope.launch {
            var result = checkIdDuplicateUseCase(user_id)
            when (result) {
                is NetworkResult.Success -> {
                    isIdDuplicated = true
                }

                is NetworkResult.Error -> {
                    when (result.code) {

                        409 -> {
                            isIdDuplicated = false

                        }
                        else -> {
                            isIdDuplicated=true

                        }
                    }
                    Log.e("checkid", "error")
                    Log.e("checkid", "Error: ${result.code}")
                    Log.e("checkid", "Error: ${result.errorData}")
                }

                is NetworkResult.Exception -> {
                    Log.e("checkid", "${result.e.message}")
                    isIdDuplicated = false

                }
            }
            onidDuplicatedresult(isIdDuplicated)
        }
    }


    // 이메일 중복 여부 체크 함수
     fun checkEmailDuplicate() {
        viewModelScope.launch {
            val result = checkEmailDuplicateUseCase("chanhue467@gmail.com")
            when (result) {
                is NetworkResult.Success -> {
                    // 이메일이 중복되지 않았을 때
                    Log.e("checkEmail", result.data!!.message) // 로그찍기
                }

                is NetworkResult.Error -> {
                    Log.e("checkEmail", "error")
                }

                is NetworkResult.Exception -> {
                    Log.e("checkEmail", "${result.e.message}")
                }
            }
        }

    }


    // 이메일 인증요청 함수
     fun checkauthenticateEmail(email: String) {
        viewModelScope.launch {

            val body = AuthenticateEmailRequest(email)

            val result = authenticateEmailUseCase(body)

            when (result) {
                is NetworkResult.Success -> {
                    // 이메일이 성공적으로 인증되었을 때
                    Log.e("authenticateEmail", result.data!!.message) // 로그찍기
                    Log.e("authen","성공")
                }

                is NetworkResult.Error -> {
                    Log.e("authen","실패1")

                    Log.e("authenticateEmail", "error")
                    Log.e("authenticateEmail", "Error: ${result.code}")
                    Log.e("authenticateEmail", "Error: ${result.errorData}")

                }

                is NetworkResult.Exception -> {
                    Log.e("authen","실패2")

                    Log.e("authenticateEmail", "${result.e.message}")
                }
            }
        }
    }



    // 이메일 코드 인증
     fun checkauthenticateEmailCode(email: String, code: Int) {

        viewModelScope.launch {
            val body = AuthenticateEmailCodeRequest(email, code)
            Log.e("checkEmailCode",body.toString())

            val result = authenticateEmailCodeUseCase(body)
            Log.e("checkEmailCode",result.toString())
            when (result) {
                is NetworkResult.Success -> {
                    // 이메일 코드가 올바를 때
                    Log.e("checkEmailCode1", result.data!!.message) // 로그찍기

                }

                is NetworkResult.Error -> {
                    Log.e("checkEmailCode2", "error")
                }

                is NetworkResult.Exception -> {
                    Log.e("checkEmailCode3", "${result.e.message}")
                }
            }
        }
    }



    // 회원가입 함수
    fun signup(userName: String, userId: String, password: String, email: String) {
        viewModelScope.launch {
            // 요청
            val body = SignUpRequest(userName, userId, password, email)
            Log.d("signup",body.toString())
            val result = signUpUseCase(body)
            Log.d("signup",result.toString())

            when (result) {
                is NetworkResult.Success -> {
                    // 회원가입이 성공적으로 완료되었을 때의 로직을 여기에 추가
                    // 예: 회원가입 성공 메시지 표시, 화면 전환 등
                    Log.e("signup", result.data!!.message) // 로그찍기
                    Log.e("signup", "성공") // 로그찍기

                }

                is NetworkResult.Error -> {
                    /*
                    when (result.code) {
                        400 -> {

                        }
                        404 -> {

                        }
                        else -> {

                        }
                    }*/
                    Log.e("signup", "error")
                    Log.e("signup", "실패") // 로그찍기

                }

                is NetworkResult.Exception -> {
                    Log.e("signup", "${result.e.suppressed}")
                    Log.e("signup", "${result.e.message}")
                    Log.e("signup", "${result.e.cause}")

                    Log.e("signup", "실패1") // 로그찍기

                }
            }
        }
    }
    // 아이디 중복여부 체크
    fun checkIsSignedIn(user_id: TextFieldValue): Boolean {
        var isSignedIn = false // 초기값 false
        runBlocking {
            delay(2000)
            if (getMemberIdUseCase().first().isNotEmpty()) {
                isSignedIn = true
            }
        }
        return isSignedIn
    }




}


