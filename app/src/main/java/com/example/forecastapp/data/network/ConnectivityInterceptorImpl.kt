package com.example.forecastapp.data.network

import android.content.Context
import android.util.Log
import com.example.forecastapp.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {
    private val TAG = this.javaClass.name
    private val appContext = context.applicationContext
    private val connectivityMonitor = ConnectivityMonitor(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityMonitor.isOnline()) {
            Log.e(TAG, "Internet is not available on this device")
            throw NoConnectivityException()
        }
        Log.d(TAG, "Internet is available")
        return chain.proceed(chain.request())
    }


}