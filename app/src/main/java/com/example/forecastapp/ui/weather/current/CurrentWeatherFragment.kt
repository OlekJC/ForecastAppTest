package com.example.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.forecastapp.R
import com.example.forecastapp.data.AccuWeatherApiService
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

const val TODAY_INDEX = 0

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel

        val apiService = AccuWeatherApiService()

        CoroutineScope(IO).launch{
            val weatherResponse = apiService.getCurrentWeather()
            withContext(Main){
                //val tMax = (currentWeather.temperature.maximum.value-32f)* (5f/9f)
                Log.d("OMG","Response = $weatherResponse")
                val weather = weatherResponse.dailyForecasts[TODAY_INDEX]
                Log.d("OMG","Weather = $weather")
                if(weather != null){
                    val tMax = weather.temperature.maximum.value
                    textView.text = "Temperatura w Warszawie wynosi $tMax C\n" +
                            "${weather.day.iconPhrase}"
                }
            }
        }
    }
}
