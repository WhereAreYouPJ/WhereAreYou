package com.whereareyou.domain.repository

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
import com.whereareyou.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyou.domain.entity.signin.SignInData
import com.whereareyou.domain.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    // 로그인
    suspend fun signIn(
        body: SignInRequest
    ): NetworkResult<SignInResponse>

    // 액세스 토큰 저장
    suspend fun saveAccessToken(
        accessToken: String
    )

    // 액세스 토큰 가져오기
    suspend fun getAccessToken(): Flow<String>

    // memberId 저장
    suspend fun saveMemberId(
        id: String
    )

    // memberId 가져오기
    suspend fun getMemberId(): Flow<String>

    // 토큰 재발급
    suspend fun reissueToken(
        body: ReissueTokenRequest
    ): NetworkResult<ReissueTokenResponse>

    // 아이디 찾기
    suspend fun findId(
        body: FindIdRequest
    ): NetworkResult<FindIdResponse>

    // 비밀번호 재설정 코드 입력
    suspend fun verifyPasswordResetCode(
        body: VerifyPasswordResetCodeRequest
    ): NetworkResult<VerifyPasswordResetCodeResponse>

    // 비밀번호 재설정
    suspend fun resetPassword(
        body: ResetPasswordRequest
    ): NetworkResult<Unit>

    // 회원 상세 정보
    suspend fun getMemberDetails(
        token: String,
        memberId: String
    ): NetworkResult<GetMemberDetailsResponse>

    // 마이페이지 수정
    suspend fun modifyMyInfo(
        token: String,
        body: ModifyMyInfoRequest
    ): NetworkResult<Unit>

    // 회원정보 삭제
    suspend fun deleteMember(
        token: String,
        body: DeleteMemberRequest
    ): NetworkResult<Unit>
}