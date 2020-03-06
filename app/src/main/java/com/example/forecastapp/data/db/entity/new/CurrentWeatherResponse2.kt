package com.example.forecastapp.data.db.entity.new


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse2(
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecast>,
    @SerializedName("Headline")
    val headline: Headline
)