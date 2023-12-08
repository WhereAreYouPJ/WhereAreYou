package com.whereareyounow.repository

import com.whereareyounow.datasource.RemoteDataSource
import com.whereareyounow.domain.repository.SignUpRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailCodeResponse
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailResponse
import com.whereareyounow.domain.entity.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyounow.domain.entity.apimessage.signup.SignUpResponse
import com.whereareyounow.util.NetworkResultHandler

class SignUpRepositoryImpl(
    private val dataSource: RemoteDataSource
) : SignUpRepository, NetworkResultHandler {

    /**
     * 아이디 중복 검사
     * implements [SignUpRepository.checkIdDuplicate]
     */
    override suspend fun checkIdDuplicate(
        userId: String
    ): NetworkResult<CheckIdDuplicateResponse> {
        return handleResult { dataSource.checkIdDuplicate(userId) }
    }

    /**
     * 이메일 중복 검사
     * implements [SignUpRepository.checkEmailDuplicate]
     */
    override suspend fun checkEmailDuplicate(
        email: String
    ): NetworkResult<CheckEmailDuplicateResponse> {
        return handleResult { dataSource.checkEmailDuplicate(email) }
    }

    /**
     * 이메일 인증
     * implements [SignUpRepository.authenticateEmail]
     */
    override suspend fun authenticateEmail(
        body: AuthenticateEmailRequest
    ): NetworkResult<AuthenticateEmailResponse> {
        return handleResult { dataSource.authenticateEmail(body) }
    }

    /**
     * 이메일 인증 코드 입력
     * implements [SignUpRepository.authenticateEmailCode]
     */
    override suspend fun authenticateEmailCode(
        body: AuthenticateEmailCodeRequest
    ): NetworkResult<AuthenticateEmailCodeResponse> {
        return handleResult { dataSource.authenticateEmailCode(body) }
    }

    /**
     * 회원가입 성공
     * implements [SignUpRepository.signUp]
     */
    override suspend fun signUp(
        body: SignUpRequest
    ): NetworkResult<SignUpResponse> {
        return handleResult { dataSource.signUp(body) }
    }
}