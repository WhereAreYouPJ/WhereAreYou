package com.onmyway.repository

import com.onmyway.api.MemberApi
import com.onmyway.domain.entity.member.DetailUserInfo
import com.onmyway.domain.entity.member.Email
import com.onmyway.domain.entity.member.SignInData
import com.onmyway.domain.entity.member.UserInfo
import com.onmyway.domain.repository.MemberRepository
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
import com.onmyway.util.NetworkResultHandler

class MemberRepositoryImpl(
    private val memberApi: MemberApi
) : MemberRepository, NetworkResultHandler {
    override suspend fun updateUserName(
        data: UpdateUserNameRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.updateUserName(body = data) }
    }

    override suspend fun updateProfileImage(
        data: UpdateProfileImageRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.updateProfileImage(body = data) }
    }

    override suspend fun signUp(
        data: SignUpRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.signUp(body = data) }
    }

    override suspend fun resetPassword(
        data: ResetPasswordRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.resetPassword(body = data) }
    }

    override suspend fun signOut(
        data: SignOutRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.signOut(body = data) }
    }

    override suspend fun signIn(
        data: SignInRequest
    ): NetworkResult<SignInData> {
        return handleResult { memberApi.signIn(body = data) }
    }

    override suspend fun verifyEmailCode(
        data: VerifyEmailCodeRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.verifyEmailCode(body = data) }
    }

    override suspend fun verifyPasswordResetCode(
        data: VerifyPasswordResetCodeRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.verifyPasswordResetCode(body = data) }
    }

    override suspend fun sendEmailCode(
        data: SendEmailCodeRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.sendEmailCode(body = data) }
    }

    override suspend fun getUserInfoByMemberCode(
        data: GetUserInfoByMemberCodeRequest
    ): NetworkResult<UserInfo> {
        return handleResult { memberApi.getUserInfoByMemberCode(
            memberCode = data.memberCode
        ) }
    }

    override suspend fun getUserInfoByMemberSeq(
        data: GetUserInfoByMemberSeqRequest
    ): NetworkResult<DetailUserInfo> {
        return handleResult { memberApi.getUserInfoByMemberSeq(
            memberSeq = data.memberSeq
        ) }
    }

    override suspend fun checkEmailDuplicate(
        data: CheckEmailDuplicateRequest
    ): NetworkResult<Email> {
        return handleResult { memberApi.checkEmailDuplicate(
            email = data.email
        ) }
    }
}