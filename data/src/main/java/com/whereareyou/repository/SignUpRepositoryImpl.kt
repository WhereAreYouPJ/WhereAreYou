package com.whereareyou.repository

import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyou.apimessage.signup.AuthenticateEmailRequest
import com.whereareyou.apimessage.signup.SignUpRequest
import com.whereareyou.util.NetworkResultHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpRepositoryImpl(
    private val dataSource: RemoteDataSource
) : SignUpRepository, NetworkResultHandler {

    override suspend fun checkIdDuplicate(
        id: String
    ): NetworkResult<String> {
        val response = dataSource.checkIdDuplicate(id)
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                body.userId
            }
        }
    }

    override suspend fun checkEmailDuplicate(
        email: String
    ): NetworkResult<String> {
        val response = dataSource.checkEmailDuplicate(email)
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                body.email
            }
        }
    }

    override suspend fun authenticateEmail(
        email: String
    ): NetworkResult<String> {
        val request = AuthenticateEmailRequest(email)
        val response = dataSource.authenticateEmail(request)
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                body.message
            }
        }
    }

    override suspend fun authenticateEmailCode(
        email: String,
        code: Int
    ): NetworkResult<String> {
        val request = AuthenticateEmailCodeRequest(email, code)
        val response = dataSource.authenticateEmailCode(request)
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                body.message
            }
        }
    }

    override suspend fun signUp(
        userName: String,
        userId: String,
        password: String,
        email: String
    ): NetworkResult<String> {
        val request = SignUpRequest(userName, userId, password, email)
        val response = dataSource.signUp(request)
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                body.message
            }
        }
    }
}