package com.example.forecastapp.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapp.R
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import com.example.forecastapp.data.db.entity.TODAY_INDEX
import com.example.forecastapp.di.components.DaggerAppComponent
import com.example.forecastapp.di.modules.NetworkModule
import com.example.forecastapp.di.modules.ViewModelModule
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CurrentWeatherViewModelFactory
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule(requireContext()))
            .viewModelModule(ViewModelModule(requireContext()))
            .build()
            .inject(this)

        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() {
        CoroutineScope(IO).launch {
            val currentWeather = viewModel.weather.await()
            withContext(Main) {
                currentWeather.observe(viewLifecycleOwner, Observer {
                    if (it == null) return@Observer
                    textView.text = getWeatherText(it)
                })
            }
        }
    }

    private fun getWeatherText(response: CurrentWeatherResponse): String {
        val tMax = response.dailyForecasts[TODAY_INDEX].temperature.maximum.value
        val phrase = response.dailyForecasts[TODAY_INDEX].day.iconPhrase
        return "Temperatura w Warszawie wynosi $tMax C\n $phrase"
    }
}
