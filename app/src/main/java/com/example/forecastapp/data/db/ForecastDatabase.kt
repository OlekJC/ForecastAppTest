package com.example.forecastapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse

@Database(entities = [CurrentWeatherResponse::class],version = 1)
@TypeConverters(CurrentWeatherTypeConverter::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao() : CurrentWeatherDAO

    companion object{

        @Volatile private var INSTANCE : ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE
            ?:
                synchronized(LOCK){
                    INSTANCE
                        ?: Room.databaseBuilder(context.applicationContext,
                            ForecastDatabase::class.java,"forecast.db")
                        .build()
                }


    }
}