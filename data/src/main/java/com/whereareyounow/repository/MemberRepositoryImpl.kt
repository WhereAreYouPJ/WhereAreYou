package com.whereareyounow.repository

import com.whereareyounow.api.MemberApi
import com.whereareyounow.domain.entity.member.DetailUserInfo
import com.whereareyounow.domain.entity.member.Email
import com.whereareyounow.domain.entity.member.SignInData
import com.whereareyounow.domain.entity.member.UserInfo
import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.CheckEmailDuplicateRequest
import com.whereareyounow.domain.request.member.GetUserInfoByMemberCodeRequest
import com.whereareyounow.domain.request.member.GetUserInfoByMemberSeqRequest
import com.whereareyounow.domain.request.member.LinkAccountRequest
import com.whereareyounow.domain.request.member.ResetPasswordRequest
import com.whereareyounow.domain.request.member.SendEmailCodeRequest
import com.whereareyounow.domain.request.member.SignInRequest
import com.whereareyounow.domain.request.member.SignOutRequest
import com.whereareyounow.domain.request.member.SignUpRequest
import com.whereareyounow.domain.request.member.SnsSignUpRequest
import com.whereareyounow.domain.request.member.UpdateProfileImageRequest
import com.whereareyounow.domain.request.member.UpdateUserNameRequest
import com.whereareyounow.domain.request.member.VerifyEmailCodeRequest
import com.whereareyounow.domain.request.member.VerifyPasswordResetCodeRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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
        val memberSeq = data.memberSeq.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("images", "images", data.images.asRequestBody("image/png".toMediaTypeOrNull()))
        return handleResult { memberApi.updateProfileImage(
            memberSeq = memberSeq,
            image = image
        ) }
    }

    override suspend fun signUp(
        data: SignUpRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.signUp(body = data) }
    }

    override suspend fun snsSignUp(
        data: SnsSignUpRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.snsSignUp(body = data) }
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

    override suspend fun linkAccount(
        data: LinkAccountRequest
    ): NetworkResult<String> {
        return handleResult { memberApi.linkAccount(body = data) }
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