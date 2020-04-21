package com.example.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastapp.data.provider.UnitProvider
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.internal.UnitSystem
import com.example.forecastapp.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private var unitSystem = unitProvider.getUnitSystem()

    fun isMetric() : Boolean{
        return unitSystem == UnitSystem.METRIC
    }

    val weather by lazyDeferred { forecastRepository.getCurrentWeather(isMetric()) }
}