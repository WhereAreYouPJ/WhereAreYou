package com.whereareyounow.domain.util

import android.net.Network
import android.util.Log

object LogUtil {
    fun printNetworkLog(response: NetworkResult<Any>, name: String) {
        when (response) {
            is NetworkResult.Success -> {
                Log.e("LogUtil", "$name Success\ncode: ${response.code}\ndata: ${response.data}")
            }
            is NetworkResult.Error -> {
                Log.e("LogUtil", "$name Error\ncode: ${response.code}\ndata: ${response.errorData}")
            }
            is NetworkResult.Exception -> {
                Log.e("LogUtil", "$name Exception\n${response.e}")
            }
        }
    }
}