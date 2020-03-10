package com.example.forecastapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import com.example.forecastapp.data.db.entity.TODAY_INDEX

@Dao
interface CurrentWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherResponse)

    @Query("SELECT * FROM current_weather where id = $TODAY_INDEX")
    fun getWeather() : LiveData<CurrentWeatherResponse>
}