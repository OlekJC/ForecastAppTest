package com.example.forecastapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse

@Dao
interface CurrentWeatherDAO {

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //fun upsert(weatherEntry: CurrentWeatherResponse)

    //@Query("SELECT * FROM current_weather where id = $CURRENT_WEATHER_ID")
    //fun getWeather() : LiveData<CurrentWeatherResponse>
}