package com.whereareyounow.api

import com.whereareyounow.domain.request.member.UpdateUserInfoRequest
import com.whereareyounow.domain.response.member.UpdateUserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MemberApi {

    // 사용자 정보 수정
    @PUT("v1/member/modify")
    suspend fun updateUserInfo(
        @Body body: UpdateUserInfoRequest
    ): Response<UpdateUserInfoResponse>

    // 회원가입
    @POST("v1/member")
    suspend fun signUp(

    )

    // 비밀번호 초기화
    @POST("v1/member/resetPassword")
    suspend fun resetPassword(

    )

    // 로그아웃
    @POST("v1/member/logout")
    suspend fun signOut(

    )

    // 로그인
    @POST("v1/member/login")
    suspend fun signIn(

    )

    // 아이디 찾기
    @POST("v1/member/findId")
    suspend fun findId(

    )

    // 회원가입 시 이메일로 전송된 코드 인증
    @POST("v1/member/email/verify")
    suspend fun verifySignUpEmailCode(

    )

    // 비밀번호 찾기 시 이메일로 전송된 코드 인증
    @POST("v1/member/email/verifyPassword")
    suspend fun verifyFindPasswordEmailCode(

    )

    // 이메일로 코드 전송
    @POST("v1/member/email/send")
    suspend fun sendEmailCode(

    )

    // UserId로 유저 정보 획득
    @GET("v1/member/info")
    suspend fun getUserInfoByUserId(

    )

    // memberSeq로 유저 정보 획득
    @GET("v1/member/details")
    suspend fun getUserInfoByMemberSeq(

    )

    // 아이디 중복 체크
    @GET("v1/member/checkId")
    suspend fun checkUserId(

    )

    // 이메일 중복 체크
    @GET("v1/member/checkEmail")
    suspend fun checkEmail(

    )
}