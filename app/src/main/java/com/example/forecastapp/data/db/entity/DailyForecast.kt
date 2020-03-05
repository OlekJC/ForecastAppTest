package com.example.forecastapp.data.db.entity


import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class DailyForecast(
    @SerializedName("Date")
    val date: String,
    @Embedded(prefix = "day_")
    val day: Day,
    @SerializedName("EpochDate")
    val epochDate: Int,
    @SerializedName("Link")
    val link: String,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @Embedded(prefix = "night_")
    val night: Night,
    @Embedded(prefix = "temp_")
    val temperature: Temperature
)