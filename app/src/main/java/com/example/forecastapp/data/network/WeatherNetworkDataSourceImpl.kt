package com.example.forecastapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import com.example.forecastapp.internal.NoConnectivityException
import java.util.*
import javax.inject.Singleton

@Singleton
class WeatherNetworkDataSourceImpl(
    private val apiService: AccuWeatherApiService
) : WeatherNetworkDataSource {
    private val TAG = this.javaClass.name

    private val _downloadedCurentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurentWeather

    override suspend fun fetchCurrentWeather(language: String, metric: String) {
        try{
            val fetchedCurrentWeather = apiService
                .getCurrentWeather(language,metric)
            _downloadedCurentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e(TAG,"No internet connection",e)
        }
        catch(e: Exception){
            Log.e(TAG,"Exception : ${e.message}")
        }
    }
}