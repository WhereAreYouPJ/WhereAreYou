package com.whereareyounow.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.whereareyounow.domain.repository.DataStoreRepository
import com.whereareyounow.globalvalue.KEY_ACCESS_TOKEN
import com.whereareyounow.globalvalue.KEY_MEMBER_CODE
import com.whereareyounow.globalvalue.KEY_MEMBER_SEQ
import com.whereareyounow.globalvalue.KEY_REFRESH_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    // DataStore에서 Access Token 가져오기
    override fun getAccessToken(): String? {
        return getData(KEY_ACCESS_TOKEN)
    }

    // DataStore에서 Refresh Token 가져오기
    override fun getRefreshToken(): String? {
        return getData(KEY_REFRESH_TOKEN)
    }

    // DataStore에 Access Token 저장하기
    override fun saveAccessToken(accessToken: String) {
        saveData(KEY_ACCESS_TOKEN, accessToken)
    }

    // DataStore에 Refresh Token 저장하기
    override fun saveRefreshToken(refreshToken: String) {
        saveData(KEY_REFRESH_TOKEN, refreshToken)
    }

    // memberSeq 저장
    override fun saveMemberSeq(memberSeq: String) {
        saveData(KEY_MEMBER_SEQ, memberSeq)
    }

    // memberSeq 획득
    override fun getMemberSeq(): String? {
        return getData(KEY_MEMBER_SEQ)
    }

    // memberCode 저장
    override fun saveMemberCode(memberCode: String) {
        saveData(KEY_MEMBER_CODE, memberCode)
    }

    // memberCode 획득
    override fun getMemberCode(): String? {
        return getData(KEY_MEMBER_CODE)
    }

    override suspend fun clearAll() {
        dataStore.edit {
            it.clear()
        }
    }

    fun getData(key: String): String? {
        val prefKey = stringPreferencesKey(key)
        return runBlocking(Dispatchers.IO) {
            dataStore.data.map { it[prefKey] }.first()
        }
    }

    fun saveData(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        return runBlocking(Dispatchers.IO) {
            dataStore.edit { it[prefKey] = value }
        }
    }
}