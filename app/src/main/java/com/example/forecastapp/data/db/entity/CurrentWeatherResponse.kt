package com.example.forecastapp.data.db.entity


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecast>,
    @SerializedName("Headline")
    val headline: Headline
)