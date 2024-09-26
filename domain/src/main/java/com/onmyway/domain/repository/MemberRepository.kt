package com.onmyway.domain.repository

import com.onmyway.domain.entity.member.DetailUserInfo
import com.onmyway.domain.entity.member.Email
import com.onmyway.domain.entity.member.SignInData
import com.onmyway.domain.entity.member.UserInfo
import com.onmyway.domain.request.member.CheckEmailDuplicateRequest
import com.onmyway.domain.request.member.GetUserInfoByMemberCodeRequest
import com.onmyway.domain.request.member.GetUserInfoByMemberSeqRequest
import com.onmyway.domain.request.member.ResetPasswordRequest
import com.onmyway.domain.request.member.SendEmailCodeRequest
import com.onmyway.domain.request.member.SignInRequest
import com.onmyway.domain.request.member.SignOutRequest
import com.onmyway.domain.request.member.SignUpRequest
import com.onmyway.domain.request.member.UpdateProfileImageRequest
import com.onmyway.domain.request.member.UpdateUserNameRequest
import com.onmyway.domain.request.member.VerifyEmailCodeRequest
import com.onmyway.domain.request.member.VerifyPasswordResetCodeRequest
import com.onmyway.domain.util.NetworkResult

interface MemberRepository {

    // 유저명 변경
    suspend fun updateUserName(
        data: UpdateUserNameRequest
    ): NetworkResult<String>

    // 프로필 사진 변경
    suspend fun updateProfileImage(
        data: UpdateProfileImageRequest
    ): NetworkResult<String>

    // 회원가입
    suspend fun signUp(
        data: SignUpRequest
    ): NetworkResult<String>

    // 비밀번호 재설정
    suspend fun resetPassword(
        data: ResetPasswordRequest
    ): NetworkResult<String>

    // 로그아웃
    suspend fun signOut(
        data: SignOutRequest
    ): NetworkResult<String>

    // 로그인
    suspend fun signIn(
        data: SignInRequest
    ): NetworkResult<SignInData>

    // 인증코드 검증
    suspend fun verifyEmailCode(
        data: VerifyEmailCodeRequest
    ): NetworkResult<String>

    // 비밀번호 재설정 인증코드 검증
    suspend fun verifyPasswordResetCode(
        data: VerifyPasswordResetCodeRequest
    ): NetworkResult<String>

    // 이메일로 코드 전송
    suspend fun sendEmailCode(
        data: SendEmailCodeRequest
    ): NetworkResult<String>

    // memberCode로 유저 정보 획득
    suspend fun getUserInfoByMemberCode(
        data: GetUserInfoByMemberCodeRequest
    ): NetworkResult<UserInfo>

    // memberSeq로 유저 정보 획득
    suspend fun getUserInfoByMemberSeq(
        data: GetUserInfoByMemberSeqRequest
    ): NetworkResult<DetailUserInfo>

    // 이메일 중복 체크
    suspend fun checkEmailDuplicate(
        data: CheckEmailDuplicateRequest
    ): NetworkResult<Email>
}