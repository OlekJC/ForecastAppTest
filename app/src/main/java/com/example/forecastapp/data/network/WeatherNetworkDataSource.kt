package com.example.forecastapp.data.network

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        language:String,
        metric: String
    )
}