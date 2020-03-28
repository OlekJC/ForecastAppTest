package com.example.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastapp.data.repository.ForecastRepository
import com.example.forecastapp.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    val weather by lazyDeferred { forecastRepository.getCurrentWeather() }

}
