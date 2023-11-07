package com.whereareyou.repository

import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeResponse
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailResponse
import com.whereareyou.domain.entity.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyou.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyou.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyou.domain.entity.apimessage.signup.SignUpResponse
import com.whereareyou.util.NetworkResultHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpRepositoryImpl(
    private val dataSource: RemoteDataSource
) : SignUpRepository, NetworkResultHandler {

    /**
     * 아이디 중복 검사
     * implements [SignUpRepository.checkIdDuplicate]
     */
    override suspend fun checkIdDuplicate(
        id: String
    ): NetworkResult<CheckIdDuplicateResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.checkIdDuplicate(id)
            handleResult(response) { it }
        }
    }

    /**
     * 이메일 중복 검사
     * implements [SignUpRepository.checkEmailDuplicate]
     */
    override suspend fun checkEmailDuplicate(
        email: String
    ): NetworkResult<CheckEmailDuplicateResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.checkEmailDuplicate(email)
            handleResult(response) { it }
        }
    }

    /**
     * 이메일 인증
     * implements [SignUpRepository.authenticateEmail]
     */
    override suspend fun authenticateEmail(
        body: AuthenticateEmailRequest
    ): NetworkResult<AuthenticateEmailResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.authenticateEmail(body)
            handleResult(response) { it }
        }
    }

    /**
     * 이메일 인증 코드 입력
     * implements [SignUpRepository.authenticateEmailCode]
     */
    override suspend fun authenticateEmailCode(
        body: AuthenticateEmailCodeRequest
    ): NetworkResult<AuthenticateEmailCodeResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.authenticateEmailCode(body)
            handleResult(response) { it }
        }
    }

    /**
     * 회원가입 성공
     * implements [SignUpRepository.signUp]
     */
    override suspend fun signUp(
        body: SignUpRequest
    ): NetworkResult<SignUpResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.signUp(body)
            handleResult(response) { it }
        }
    }
}