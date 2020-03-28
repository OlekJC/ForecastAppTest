package com.example.forecastapp.di.components

import com.example.forecastapp.di.modules.NetworkModule
import com.example.forecastapp.di.modules.ViewModelModule
import com.example.forecastapp.ui.weather.current.CurrentWeatherFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
public interface AppComponent {
    fun inject(currentWeatherFragment: CurrentWeatherFragment)
}