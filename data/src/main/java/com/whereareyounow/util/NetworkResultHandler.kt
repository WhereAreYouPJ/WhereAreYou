package com.whereareyounow.util

import com.google.gson.GsonBuilder
import com.whereareyounow.domain.entity.ErrorBody
import com.whereareyounow.domain.util.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import java.text.SimpleDateFormat

class EmptyBody : Any()

interface NetworkResultHandler {
    suspend fun <T:Any> handleResult(
        responseFunction: suspend () -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = responseFunction()
            if (response.isSuccessful) {
                if (response.body() == null) {
                    NetworkResult.Success(response.code(), null)
                } else {
                    NetworkResult.Success(response.code(), response.body())
                }
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