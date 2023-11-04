package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.signin.SignInData
import com.whereareyou.domain.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    suspend fun signIn(
        userId: String,
        password: String
    ): NetworkResult<SignInData>

    suspend fun saveAccessToken(
        accessToken: String
    )

    suspend fun getAccessToken(): Flow<String>

    suspend fun saveMemberId(
        id: String
    )

    suspend fun getMemberId(): Flow<String>
}