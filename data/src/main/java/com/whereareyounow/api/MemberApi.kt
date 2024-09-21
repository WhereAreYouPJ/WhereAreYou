package com.whereareyounow.api

import com.whereareyounow.domain.entity.member.DetailUserInfo
import com.whereareyounow.domain.request.member.SendEmailCodeRequest
import com.whereareyounow.domain.request.member.SignOutRequest
import com.whereareyounow.domain.request.member.UpdateProfileImageRequest
import com.whereareyounow.domain.request.member.VerifyEmailCodeRequest
import com.whereareyounow.domain.request.member.ResetPasswordRequest
import com.whereareyounow.domain.request.member.SignInRequest
import com.whereareyounow.domain.request.member.VerifyPasswordResetCodeRequest
import com.whereareyounow.domain.request.member.SignUpRequest
import com.whereareyounow.domain.entity.member.Email
import com.whereareyounow.domain.entity.member.SignInData
import com.whereareyounow.domain.entity.member.UserInfo
import com.whereareyounow.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface MemberApi {

    // 프로필 사진 변경
    @PUT("member/modify/profileImage")
    suspend fun updateProfileImage(
        @Body body: UpdateProfileImageRequest
    ): Response<ResponseWrapper<String>>

    // 회원가입
    @POST("member")
    suspend fun signUp(
        @Body body: SignUpRequest
    ): Response<ResponseWrapper<String>>

    // 비밀번호 재설정
    @POST("member/resetPassword")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ): Response<ResponseWrapper<String>>

    // 로그아웃
    @POST("member/logout")
    suspend fun signOut(
        @Body body: SignOutRequest
    ): Response<ResponseWrapper<String>>

    // 로그인
    @POST("member/login")
    suspend fun signIn(
        @Body body: SignInRequest
    ): Response<ResponseWrapper<SignInData>>

    // 인증코드 검증
    @POST("member/email/verify")
    suspend fun verifyEmailCode(
        @Body body: VerifyEmailCodeRequest
    ): Response<ResponseWrapper<String>>

    // 비밀번호 재설정 인증코드 검증
    @POST("member/email/verifyPassword")
    suspend fun verifyPasswordResetCode(
        @Body body: VerifyPasswordResetCodeRequest
    ): Response<ResponseWrapper<String>>

    // 이메일로 코드 전송
    @POST("member/email/send")
    suspend fun sendEmailCode(
        @Body body: SendEmailCodeRequest
    ): Response<ResponseWrapper<String>>

    // memberCode로 유저 정보 획득
    @GET("member/search")
    suspend fun getUserInfoByMemberCode(
        @Query("memberCode") memberCode: String
    ): Response<ResponseWrapper<UserInfo>>

    // memberSeq로 유저 정보 획득
    @GET("member/details")
    suspend fun getUserInfoByMemberSeq(
        @Query("memberSeq") memberSeq: Int
    ): Response<ResponseWrapper<DetailUserInfo>>

    // 이메일 중복 체크
    @GET("member/checkEmail")
    suspend fun checkEmailDuplicate(
        @Query("email") email: String
    ): Response<ResponseWrapper<Email>>
}