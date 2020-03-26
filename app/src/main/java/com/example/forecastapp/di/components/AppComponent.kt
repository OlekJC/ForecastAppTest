package com.example.forecastapp.di.components

import com.example.forecastapp.di.modules.NetworkModule
import com.example.forecastapp.ui.weather.current.CurrentWeatherFragment
import dagger.Component

@Component(modules = [NetworkModule::class])
public interface AppComponent {

    fun inject(currentWeatherFragment: CurrentWeatherFragment)

}