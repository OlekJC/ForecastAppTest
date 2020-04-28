package com.example.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastapp.data.db.CurrentWeatherDAO
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import com.example.forecastapp.data.network.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {
    private var isMetric : Boolean = false

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {
            persistFetchedCurrentWeather(it)
        }
    }

    override suspend fun getCurrentWeather(isMetric : Boolean): LiveData<CurrentWeatherResponse> {
        this.isMetric = isMetric
        return withContext(IO) {
            initWeatherData()
            return@withContext currentWeatherDAO.getWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(IO) {
            currentWeatherDAO.upsert(fetchedWeather)
        }
    }

    private suspend fun initWeatherData() {
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchCurrentWeather()
        }
    }

    private suspend fun fetchCurrentWeather() {
        //todo check route of ismMtric and language parameters
        val isMetricString = if(isMetric) "true" else "false"
        weatherNetworkDataSource.fetchCurrentWeather(Locale.getDefault().language,isMetricString)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}