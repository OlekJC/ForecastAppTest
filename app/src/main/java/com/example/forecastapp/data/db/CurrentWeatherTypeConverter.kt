package com.example.forecastapp.data.db.entity

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

class CurrentWeatherTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun dailyForecastListToString(list : List<DailyForecast>) : String {
        
    }

}