package com.example.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.forecastapp.R
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import com.example.forecastapp.data.network.AccuWeatherApiService
import com.example.forecastapp.data.db.entity.TODAY_INDEX
import com.example.forecastapp.data.network.ConnectivityInterceptorImpl
import com.example.forecastapp.data.network.WeatherNetworkDataSource
import com.example.forecastapp.data.network.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

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

        val apiService =
            AccuWeatherApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner, Observer {
            textView.text = getWeatherText(it)
        })


        CoroutineScope(Main).launch{
            weatherNetworkDataSource.fetchCurrentWeather("pl","true")
            //withContext(Main){

                //val weatherResponse = apiService.getCurrentWeather()
                /*//val tMax = (currentWeather.temperature.maximum.value-32f)* (5f/9f)
                Log.d("OMG","Response = $weatherResponse")
                val weather = weatherResponse.dailyForecasts[TODAY_INDEX]
                Log.d("OMG","Weather = $weather")
                if(weather != null){
                    val tMax = weather.temperature.maximum.value
                    textView.text = "Temperatura w Warszawie wynosi $tMax C\n" +
                            "${weather.day.iconPhrase}"
                }*/
           //}
        }
    }

    fun getWeatherText(response : CurrentWeatherResponse) : String{
        val tMax = response.dailyForecasts[TODAY_INDEX].temperature.maximum.value
        val phrase = response.dailyForecasts[TODAY_INDEX].day.iconPhrase
        return "Temperatura w Warszawie wynosi $tMax C\n $phrase"
    }
}
