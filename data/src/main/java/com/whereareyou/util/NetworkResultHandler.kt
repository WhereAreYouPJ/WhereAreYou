package com.whereareyou.util

import com.google.gson.GsonBuilder
import com.whereareyou.domain.entity.ErrorBody
import com.whereareyou.domain.util.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import java.text.SimpleDateFormat


interface NetworkResultHandler {
    suspend fun <T : Any, K : Any> handleResult(
        response: Response<T>,
        processResponse: (T?) -> K
    ): NetworkResult<K> {
        return try {
            if (response.isSuccessful) {
                NetworkResult.Success(response.code(), processResponse(response.body()))
            } else {
                val gson = GsonBuilder().create()
                val errorBody = gson.fromJson(response.errorBody()?.string(), ErrorBody::class.java)
                NetworkResult.Error(response.code(), errorBody)
            }
        } catch (e: HttpException) {
            val currentTime = System.currentTimeMillis()
            val format = SimpleDateFormat("yyyy-MM-dd-hh-mm-ss")
            val date = format.format(currentTime)
            NetworkResult.Error(e.code(), ErrorBody(date, e.message(), ""))
        } catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}