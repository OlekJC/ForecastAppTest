package com.example.forecastapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.forecastapp.data.db.CurrentWeatherDAO
import com.example.forecastapp.data.db.ForecastDatabase
import com.example.forecastapp.data.network.AccuWeatherApiService
import com.example.forecastapp.data.network.WeatherNetworkDataSource
import com.example.forecastapp.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.data.repository.ForecastRepositoryImpl
import com.example.forecastapp.ui.weather.current.CurrentWeatherViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule(private val context: Context) {

    @Provides
    fun provideViewModelFactory(forecastRepository: ForecastRepository): CurrentWeatherViewModelFactory {
        return CurrentWeatherViewModelFactory(forecastRepository)
    }

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