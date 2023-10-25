package com.whereareyou.datasource

import com.whereareyou.api.ScheduleApi
import com.whereareyou.api.SignInApi
import com.whereareyou.api.SignUpApi
import com.whereareyou.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyou.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyou.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyou.apimessage.schedule.SendNewScheduleRequest
import com.whereareyou.apimessage.schedule.SendNewScheduleResponse
import com.whereareyou.apimessage.signin.SignInRequest
import com.whereareyou.apimessage.signin.SignInResponse
import com.whereareyou.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyou.apimessage.signup.AuthenticateEmailCodeResponse
import com.whereareyou.apimessage.signup.AuthenticateEmailRequest
import com.whereareyou.apimessage.signup.AuthenticateEmailResponse
import com.whereareyou.apimessage.signup.CheckEmailDuplicateResponse
import com.whereareyou.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyou.apimessage.signup.SignUpRequest
import com.whereareyou.apimessage.signup.SignUpResponse
import retrofit2.Response

class RemoteDataSource(
    private val scheduleApi: ScheduleApi,
    private val signUpApi: SignUpApi,
    private val signInApi: SignInApi
) {
    // 일정 관련
    suspend fun getMonthlySchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int
    ): Response<GetMonthlyScheduleResponse> {
        return scheduleApi.getMonthlySchedule(token, memberId, year, month)
    }

    suspend fun getDailyBriefSchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): Response<GetDailyBriefScheduleResponse> {
        return scheduleApi.getDailyBriefSchedule(token, memberId, year, month, date
            )
    }

    suspend fun getDetailSchedule(
        token: String,
        memberId: String,
        scheduleId: String
    ): Response<GetDetailScheduleResponse> {
        return scheduleApi.getDetailSchedule(token, memberId, scheduleId)
    }

    suspend fun sendNewSchedule(
        token: String,
        body: SendNewScheduleRequest
    ): Response<SendNewScheduleResponse> {
        return scheduleApi.addNewSchedule(token, body)
    }

    // 회원가입 관련
    suspend fun checkIdDuplicate(
        id: String
    ): Response<CheckIdDuplicateResponse> {
        return signUpApi.checkIdDuplicate(id)
    }

    suspend fun checkEmailDuplicate(
        email: String
    ): Response<CheckEmailDuplicateResponse> {
        return signUpApi.checkEmailDuplicate(email)
    }

    suspend fun authenticateEmail(
        body: AuthenticateEmailRequest
    ): Response<AuthenticateEmailResponse> {
        return signUpApi.authenticateEmail(body)
    }

    suspend fun authenticateEmailCode(
        body: AuthenticateEmailCodeRequest
    ): Response<AuthenticateEmailCodeResponse> {
        return signUpApi.authenticateEmailCode(body)
    }

    suspend fun signUp(
        body: SignUpRequest
    ): Response<SignUpResponse> {
        return signUpApi.signUp(body)
    }

    // 로그인 관련
    suspend fun signIn(
        body: SignInRequest
    ): Response<SignInResponse> {
        return signInApi.signIn(body)
    }

    // 친구 관련
}