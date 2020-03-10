package com.example.forecastapp.data.db

import androidx.room.TypeConverter
import com.example.forecastapp.data.db.entity.DailyForecast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CurrentWeatherTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun dailyForecastListToString(list : List<DailyForecast>) : String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToDailyForecastList(serialized : String) : List<DailyForecast> {
        val typeOfT:Type  = object: TypeToken<Collection<DailyForecast>>(){}.type
        return gson.fromJson(serialized,typeOfT)
    }
}