package com.example.forecastapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

class ConnectivityMonitor(context: Context) {

    private val appContext = context.applicationContext
    private val networkCallback = object : ConnectivityManager.NetworkCallback(){
        override fun onLost(network: Network) {
            super.onLost(network)
            connectionAvailability = false

        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            connectionAvailability = true
        }
    }

    companion object {

        var connectionAvailability : Boolean = false
    }

    init{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun isOnline() = connectionAvailability
}