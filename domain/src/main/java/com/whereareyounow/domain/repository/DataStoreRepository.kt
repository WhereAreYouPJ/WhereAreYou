package com.whereareyounow.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    // Access Token 가져오기
    fun getAccessToken(): String?

    // Refresh Token 가져오기
    fun getRefreshToken(): String?

    // Access Token 저장하기
    fun saveAccessToken(accessToken: String)

    // Refresh Token 저장하기
    fun saveRefreshToken(refreshToken: String)

    // memberSeq 저장
    fun saveMemberSeq(memberSeq: String)

    // memberSeq 획득
    fun getMemberSeq(): String?

    // memberCode 저장
    fun saveMemberCode(memberCode: String)

    // memberCode 획득
    fun getMemberCode(): String?

    suspend fun clearAll()
}