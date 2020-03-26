package com.example.forecastapp.data.network

import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AccuWeatherApiService {
    @GET("daily/1day/274663")
    suspend fun getCurrentWeather(
        @Query("language") language: String = "pl",
        @Query("metric") metric: String = "true"
    ): CurrentWeatherResponse
}