package com.whereareyou.repository

import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.domain.entity.signin.SignInData
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.apimessage.signin.SignInRequest
import com.whereareyou.util.NetworkResultHandler

class SignInRepositoryImpl(
    private val dataSource: RemoteDataSource
) : SignInRepository, NetworkResultHandler {

    override suspend fun signIn(
        userId: String,
        password: String
    ): NetworkResult<SignInData> {
        val request = SignInRequest(userId, password)
        val response = dataSource.signIn(request)
        return handleResult(response) { body ->
            SignInData(body.message, body.accessToken, body.refreshToken, body.memberId)
        }
    }
}