package com.whereareyou.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.domain.entity.signin.SignInData
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.apimessage.signin.SignInRequest
import com.whereareyou.util.NetworkResultHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SignInRepositoryImpl(
    private val dataSource: RemoteDataSource,
    private val preferencesDataStore: DataStore<Preferences>
) : SignInRepository, NetworkResultHandler {

    override suspend fun signIn(
        userId: String,
        password: String
    ): NetworkResult<SignInData> {
        val request = SignInRequest(userId, password)
        val response = dataSource.signIn(request)
        return handleResult(response) { body ->
            SignInData(body.accessToken, body.refreshToken, body.memberId)
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        val key = stringPreferencesKey("accessToken")
        preferencesDataStore.edit {
            it[key] = accessToken
        }
    }

    override suspend fun getAccessToken(): Flow<String> {
        val key = stringPreferencesKey("accessToken")
        return preferencesDataStore.data.map {
            it[key] ?: ""
        }
    }
}