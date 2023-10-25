package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.signin.SignInData
import com.whereareyou.domain.util.NetworkResult

interface SignInRepository {

    suspend fun signIn(
        userId: String,
        password: String
    ): NetworkResult<SignInData>
}