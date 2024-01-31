package com.whereareyounow.api

import com.whereareyounow.domain.entity.apimessage.signin.DeleteMemberRequest
import com.whereareyounow.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyounow.domain.entity.apimessage.signin.FindIdResponse
import com.whereareyounow.domain.entity.apimessage.signin.GetMemberIdByUserIdResponse
import com.whereareyounow.domain.entity.apimessage.signin.GetMemberDetailsResponse
import com.whereareyounow.domain.entity.apimessage.signin.ReissueTokenRequest
import com.whereareyounow.domain.entity.apimessage.signin.ReissueTokenResponse
import com.whereareyounow.domain.entity.apimessage.signin.ResetPasswordRequest
import com.whereareyounow.domain.entity.apimessage.signin.SignInRequest
import com.whereareyounow.domain.entity.apimessage.signin.SignInResponse
import com.whereareyounow.domain.entity.apimessage.signin.VerifyPasswordResetCodeRequest
import com.whereareyounow.domain.entity.apimessage.signin.VerifyPasswordResetCodeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
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
    ): Response<Unit>

    // 회원 상세 정보(memberId)
    @GET("member/details")
    suspend fun getMemberDetails(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String
    ): Response<GetMemberDetailsResponse>

    // 회원 상세 정보(userId)
    @GET("member/info")
    suspend fun getMemberIdByUserId(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): Response<GetMemberIdByUserIdResponse>

    // 마이페이지 수정
    @Multipart
    @POST("member/myPage/modify")
    suspend fun modifyMyInfo(
        @Header("Authorization") token: String,
        @PartMap partMap: HashMap<String, RequestBody>,
//        @Part("memberId") memberId: RequestBody,
        @Part photo: MultipartBody.Part?,
//        @Part("newId") userId: RequestBody
    ): Response<Unit>

    // 회원정보 삭제
    @HTTP(method = "DELETE", path = "member/deleteMember", hasBody = true)
    suspend fun deleteMember(
        @Header("Authorization") token: String,
        @Body body: DeleteMemberRequest
    ): Response<Unit>
}