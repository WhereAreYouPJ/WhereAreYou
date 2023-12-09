package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.entity.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyounow.domain.util.NetworkResult

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
    ): NetworkResult<Unit>

    // 이메일 인증 코드 입력
    suspend fun authenticateEmailCode(
        body: AuthenticateEmailCodeRequest
    ): NetworkResult<Unit>

    // 회원가입 성공
    suspend fun signUp(
        body: SignUpRequest
    ): NetworkResult<Unit>
}