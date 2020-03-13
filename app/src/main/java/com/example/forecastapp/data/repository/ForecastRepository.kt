package com.example.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse

interface ForecastRepository {
    suspend fun getCurrentWeather() : LiveData<CurrentWeatherResponse>
}