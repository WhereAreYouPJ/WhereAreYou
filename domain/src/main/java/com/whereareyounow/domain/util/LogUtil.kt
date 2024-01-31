package com.whereareyounow.domain.util

import android.util.Log
import com.whereareyounow.domain.BuildConfig

object LogUtil {
    fun printNetworkLog(response: NetworkResult<Any>, name: String) {
        when (response) {
            is NetworkResult.Success -> {
                printLog("NetworkResultLog", "$name Success\ncode: ${response.code}\ndata: ${response.data}\n-")
            }
            is NetworkResult.Error -> {
                printLog("NetworkResultLog", "$name Error\ncode: ${response.code}\ndata: ${response.errorData}\n-")
            }
            is NetworkResult.Exception -> {
                printLog("NetworkResultLog", "$name Exception\n${response.e}\n-")
            }
        }
    }

    fun printLog(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.e(tag, message) else Unit
    }
}