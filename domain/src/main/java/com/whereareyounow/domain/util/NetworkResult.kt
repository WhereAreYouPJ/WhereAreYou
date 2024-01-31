package com.whereareyounow.domain.util

import com.whereareyounow.domain.entity.ErrorBody

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val code: Int, val data: T?) : NetworkResult<T>()
    data class Error(val code: Int, val errorData: ErrorBody?) : NetworkResult<Nothing>()
    data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}