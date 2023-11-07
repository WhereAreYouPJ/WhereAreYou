package com.whereareyou.api

import com.whereareyou.domain.entity.apimessage.signin.DeleteMemberRequest
import com.whereareyou.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyou.domain.entity.apimessage.signin.FindIdResponse
import com.whereareyou.domain.entity.apimessage.signin.GetMemberDetailsResponse
import com.whereareyou.domain.entity.apimessage.signin.ModifyMyInfoRequest
import com.whereareyou.domain.entity.apimessage.signin.ReissueTokenRequest
import com.whereareyou.domain.entity.apimessage.signin.ReissueTokenResponse
import com.whereareyou.domain.entity.apimessage.signin.ResetPasswordRequest
import com.whereareyou.domain.entity.apimessage.signin.SignInRequest
import com.whereareyou.domain.entity.apimessage.signin.SignInResponse
import com.whereareyou.domain.entity.apimessage.signin.VerifyPasswordResetCodeRequest
import com.whereareyou.domain.entity.apimessage.signin.VerifyPasswordResetCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SignInApi {

    // 로그인
    @POST("member/login")
    suspend fun signIn(
        @Body body: SignInRequest
    ): Response<SignInResponse>

    // 토큰 재발급
    @POST("member/tokenReissue")
    suspend fun reissueToken(
        @Body body: ReissueTokenRequest
    ): Response<ReissueTokenResponse>

    // 아이디 찾기
    @POST("member/findId")
    suspend fun findId(
        @Body body: FindIdRequest
    ): Response<FindIdResponse>

    // 비밀번호 재설정 코드 입력
    @POST("member/email/verifyPassword")
    suspend fun verifyPasswordResetCode(
        @Body body: VerifyPasswordResetCodeRequest
    ): Response<VerifyPasswordResetCodeResponse>

    // 비밀번호 재설정
    @POST("member/resetPassword")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ): Response<Nothing>

    // 회원 상세 정보
    @GET("member/details")
    suspend fun getMemberDetails(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String
    ): Response<GetMemberDetailsResponse>

    // 마이페이지 수정
    @POST("member/myPage/modify")
    suspend fun modifyMyInfo(
        @Header("Authorization") token: String,
        @Body body: ModifyMyInfoRequest
    ): Response<Nothing>

    // 회원정보 삭제
    @POST("member/deleteMember")
    suspend fun deleteMember(
        @Header("Authorization") token: String,
        @Body body: DeleteMemberRequest
    ): Response<Nothing>
}