package com.example.forecastapp.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "current_weather")
data class CurrentWeatherResponse(
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecast>,
    @Embedded(prefix = "headline_")
    @SerializedName("Headline")
    val headline: Headline
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}