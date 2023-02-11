package com.siddroid.currencyconverter.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InternetConnectivity @Inject constructor(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    fun isNetworkConnected(): Boolean {
            val n: Network? = connectivityManager.activeNetwork
            if (n != null) {
                val nc: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(n)
                return nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
                        || nc?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
            }
        return false
    }
}