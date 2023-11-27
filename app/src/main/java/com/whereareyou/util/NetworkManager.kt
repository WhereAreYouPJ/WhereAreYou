package com.whereareyou.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.whereareyou.WhereAreYouApplication

object NetworkManager {

    fun checkNetworkState(): Boolean {
        val connectivityManager: ConnectivityManager =
            WhereAreYouApplication.applicationContext().getSystemService(ConnectivityManager::class.java)

        val network: Network = connectivityManager.activeNetwork ?: return false
        val actNetwork: NetworkCapabilities =
            connectivityManager.getNetworkCapabilities(network)?: return false

        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }
}