package com.example.forecastapp.di.modules

import com.example.forecastapp.data.provider.UnitProvider
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.ui.weather.current.CurrentWeatherViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun provideViewModelFactory(forecastRepository: ForecastRepository,unitProvider: UnitProvider): CurrentWeatherViewModelFactory {
        return CurrentWeatherViewModelFactory(forecastRepository,unitProvider)
    }
}