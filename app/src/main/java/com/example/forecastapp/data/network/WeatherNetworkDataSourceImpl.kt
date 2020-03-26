package com.example.forecastapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import com.example.forecastapp.internal.NoConnectivityException
import javax.inject.Singleton

@Singleton
class WeatherNetworkDataSourceImpl(
    private val apiService: AccuWeatherApiService
) : WeatherNetworkDataSource {
    private val TAG = this.javaClass.name

    private val _downloadedCurentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurentWeather //To change initializer of created properties use File | Settings | File Templates.

    override suspend fun fetchCurrentWeather(language: String, metric: String) {
        try{
            val fetchedCurrentWeather = apiService
                .getCurrentWeather()
            _downloadedCurentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e(TAG,"No internet connection",e)
        }
    }
}