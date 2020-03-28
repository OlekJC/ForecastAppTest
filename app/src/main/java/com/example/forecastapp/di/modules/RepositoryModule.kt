package com.example.forecastapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.forecastapp.data.db.CurrentWeatherDAO
import com.example.forecastapp.data.db.ForecastDatabase
import com.example.forecastapp.data.network.WeatherNetworkDataSource
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.data.repository.ForecastRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(private val context: Context)
{
    @Singleton
    @Provides
    fun provideForecastRepository(
        currentWeatherDAO: CurrentWeatherDAO,
        weatherNetworkDataSource: WeatherNetworkDataSource
    ): ForecastRepository {
        return ForecastRepositoryImpl(currentWeatherDAO, weatherNetworkDataSource)
    }

    @Singleton
    @Provides
    fun provideForecastDatabase(): ForecastDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ForecastDatabase::class.java, "forecast.db"
        )
            .build()
    }
    @Provides
    fun provideCurrentWeatherDAO(forecastDatabase: ForecastDatabase): CurrentWeatherDAO {
        return forecastDatabase.currentWeatherDao()
    }
}