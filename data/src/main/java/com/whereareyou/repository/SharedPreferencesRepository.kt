package com.whereareyou.repository

import com.whereareyou.datasource.SharedPreferencesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPreferencesRepository(
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) {
    // 내 아이디 저장
    suspend fun updateMyMemberId(myMemberId: String) {
        withContext(Dispatchers.IO) {
            sharedPreferencesDataSource.putString("myMemberId", myMemberId)
        }
    }

    // 내 아이디 가져오기
    suspend fun getMyMemberId(): String {
        return withContext(Dispatchers.IO) {
            sharedPreferencesDataSource.getString("myMemberId")
        }
    }
}