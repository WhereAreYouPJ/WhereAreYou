package com.whereareyou.api

import com.whereareyou.apimessage.signin.SignInRequest
import com.whereareyou.apimessage.signin.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInApi {

    @POST("member/login")
    suspend fun signIn(
        @Body body: SignInRequest
    ): Response<SignInResponse>
}