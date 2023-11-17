package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeResponse
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailResponse
import com.whereareyou.domain.entity.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyou.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyou.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyou.domain.entity.apimessage.signup.SignUpResponse
import com.whereareyou.domain.entity.signup.ResponseMessage
import com.whereareyou.domain.util.NetworkResult

interface SignUpRepository {

    // 아이디 중복 검사
    suspend fun checkIdDuplicate(
        userId: String
    ): NetworkResult<CheckIdDuplicateResponse>

    // 이메일 중복 검사
    suspend fun checkEmailDuplicate(
        email: String
    ): NetworkResult<CheckEmailDuplicateResponse>

    // 이메일 인증
    suspend fun authenticateEmail(
        body: AuthenticateEmailRequest
    ): NetworkResult<AuthenticateEmailResponse>

    // 이메일 인증 코드 입력
    suspend fun authenticateEmailCode(
        body: AuthenticateEmailCodeRequest
    ): NetworkResult<AuthenticateEmailCodeResponse>

    // 회원가입 성공
    suspend fun signUp(
        body: SignUpRequest
    ): NetworkResult<SignUpResponse>
}