package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.signup.ResponseMessage
import com.whereareyou.domain.util.NetworkResult

interface SignUpRepository {

    suspend fun checkIdDuplicate(
        id: String
    ): NetworkResult<String>

    suspend fun checkEmailDuplicate(
        email: String
    ): NetworkResult<String>

    suspend fun authenticateEmail(
        email: String,
    ): NetworkResult<String>

    suspend fun authenticateEmailCode(
        email: String,
        code: Int
    ): NetworkResult<String>

    suspend fun signUp(
        userName: String,
        userId: String,
        password: String,
        email: String
    ): NetworkResult<String>
}