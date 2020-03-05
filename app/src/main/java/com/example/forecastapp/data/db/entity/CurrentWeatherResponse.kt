package com.example.forecastapp.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.forecastapp.data.db.entity.DailyForecast
import com.example.forecastapp.data.db.entity.Headline
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherResponse(
    @Embedded(prefix = "forecast_")
    val dailyForecasts: DailyForecast
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = CURRENT_WEATHER_ID
}