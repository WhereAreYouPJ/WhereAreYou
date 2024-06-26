package com.whereareyounow.api

import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.entity.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpApi {

    // 아이디 중복 검사
    @GET("member/checkId")
    suspend fun checkIdDuplicate(
        @Query("userId") id: String
    ): Response<CheckIdDuplicateResponse>

    // 이메일 중복 검사
    @GET("member/checkEmail")
    suspend fun checkEmailDuplicate(
        @Query("email") email: String
    ): Response<CheckEmailDuplicateResponse>

    // 이메일 인증 코드 전송
    @POST("member/email/send")
    suspend fun authenticateEmail(
        @Body body: AuthenticateEmailRequest
    ): Response<Unit>

    // 이메일 인증 코드 입력
    @POST("member/email/verify")
    suspend fun authenticateEmailCode(
        @Body body: AuthenticateEmailCodeRequest
    ): Response<Unit>

    // 회원가입 성공
    @POST("member/join")
    suspend fun signUp(
        @Body body: SignUpRequest
    ): Response<Unit>
}